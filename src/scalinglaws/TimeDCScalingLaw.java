package scalinglaws;

import ievents.DcOS;
import market.Price;
import tools.Tools;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by author.
 *
 * This class is dedicated to the computation of the "Time of directional change scaling law", Law 10(dc) from
 * the "Patterns in high-frequency FX data: Discovery of 12 empirical scaling laws" by Glattfelder et. al.
 *
 * The results of the computation is a file with two columns: Delta, TimeDC (in milliseconds).
 * Another result is the coefficients E_{T, DC} and C_{T, DC} of the scaling law.
 * * ATTENTION!!! X axis is expressed in the fractional term where 1% is 0.01. Y axis is in seconds.
 *
 * In order to run the code one should:
 *  choose the lowest and highest size of thresholds;
 *  define number of steps between the chosen thresholds;
 *  choose whether to store results to a file or no.
 */

public class TimeDCScalingLaw {

    private double[] arrayDeltas; // to hold all set of deltas used to compute the scaling law
    private DcOS[] dcOses; // DC elements to get the values at given threshold
    private double[] timesDC; // keeps times of all DCs
    private double[] numDCs; // num of all DCs and, correspondingly, all TMs
    private int numSteps; // number of steps for the scaling law
    private double[] scalingLawParam; // array of the following values: [C, E, r^2]

    /**
     * The constructor of the class.
     * @param lowDelta
     * @param higDelta
     * @param numSteps
     */
    public TimeDCScalingLaw(float lowDelta, float higDelta, int numSteps){
        arrayDeltas = Tools.GenerateLogSpace(lowDelta, higDelta, numSteps);
        dcOses = new DcOS[numSteps];
        for (int i = 0; i < numSteps; i++){
            double delta = arrayDeltas[i];
            dcOses[i] = new DcOS(delta, delta, 1, delta, delta, true);
        }
        numDCs = new double[numSteps];
        timesDC = new double[numSteps];
        this.numSteps = numSteps;
    }

    /**
     * The method should be run for EACH tick
     * @param aPrice is the next observed (generated, recorded...) price
     */
    public void run(Price aPrice){
        for (int i = 0; i < numSteps; i++){
            int event = dcOses[i].run(aPrice);
            if (event == 1 || event == -1){
                timesDC[i] += dcOses[i].gettDcIE() - dcOses[i].gettOS();
                numDCs[i] += 1;
            }
        }
    }

    /**
     * Here the method simply finds average value of each overshoot set
     */
    public void finish(){
        for (int i = 0; i < numSteps; i++){
            if (numDCs[i] != 0){
                timesDC[i] /= numDCs[i];
                timesDC[i] /= 1000.0; // to have seconds instead of milliseconds
            }
        }
    }


    /**
     * The function finds the scaling law parameters defined in "Patterns in high-frequency FX data: Discovery of 12
     * empirical scaling laws J.B.", page 13
     * @return the same what the function Tools.computeScalingParams returns
     */
    public double[] computeParams(){
        scalingLawParam = Tools.computeScalingParams(arrayDeltas, timesDC);
        return scalingLawParam;
    }

    /**
     *
     * @param dirName is the name of the output folder.
     */
    public void saveResults(String dirName){
        Tools.CheckDirectory(dirName);
        try {
            String dateString = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date());
            String fileName = "14_timeDCScalingLaw" + "_" + dateString + ".csv";
            PrintWriter writer = new PrintWriter(dirName + "/" + fileName, "UTF-8");
            writer.println("Delta;TimeDC");
            for (int i = 0; i < numSteps; i++){
                writer.println(arrayDeltas[i] + ";" + timesDC[i]);
            }
            writer.close();
            System.out.println("The file is saved as:   " + fileName);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public double[] getNumDCs() {
        return numDCs;
    }

    public double[] getTimesDC() { return timesDC;}

    public double[] getArrayDeltas() {
        return arrayDeltas;
    }
}
