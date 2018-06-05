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
 * This class is dedicated to the computation of the "Overshoot tick count scaling law", Law 11(os) from
 * the "Patterns in high-frequency FX data: Discovery of 12 empirical scaling laws".
 *
 * One of the results of the computation is a file with two columns: Delta, OSticksNum.
 * Another result is the coefficients of the scaling law.
 *
 * In order to run the code one should:
 *  choose the lowest and highest size of thresholds;
 *  define number of points between the chosen thresholds;
 *  choose whether to store results to a file or no.
 */

public class OStickCountScalingLaw {

    private double[] arrayDeltas;
    private DcOS[] dcOses;
    private double numDCs[];
    private int numPoints;
    private double[] scalingLawParam;
    private double[] ticksPerOS;

    /**
     * The constructor of the class.
     * @param lowDelta
     * @param higDelta
     * @param numPoints
     */
    public OStickCountScalingLaw(float lowDelta, float higDelta, int numPoints){
        arrayDeltas = Tools.GenerateLogSpace(lowDelta, higDelta, numPoints);
        dcOses = new DcOS[numPoints];
        for (int i = 0; i < numPoints; i++){
            double delta = arrayDeltas[i];
            dcOses[i] = new DcOS(delta, delta, 1, delta, delta, true);
        }
        numDCs = new double[numPoints];
        ticksPerOS = new double[numPoints];
        this.numPoints = numPoints;
    }

    /**
     *
     * @param aPrice is the next observed (generated, recorded...) price
     */
    public void run(Price aPrice){
        for (int i = 0; i < numPoints; i++){
            int event = dcOses[i].run(aPrice);
            if (event == 1 || event == -1){
                ticksPerOS[i] += dcOses[i].getTicksPrevOS();
                numDCs[i] += 1;
            }
        }
    }


    /**
     * Should be called in the very end of the experiment when the data is over
     * @return the scaling law parameters [C, E, r^2]
     */
    public double[] finish(){
        for (int i = 0; i < numPoints; i++){
            ticksPerOS[i] /= numDCs[i];
        }
        return computeParams();
    }


    /**
     * The function finds the scaling law parameters defined in "Patterns in high-frequency FX data: Discovery of 12
     * empirical scaling laws J.B.", page 13
     * @return the same what the function Tools.computeScalingParams returns
     */
    public double[] computeParams(){
        scalingLawParam = Tools.computeScalingParams(arrayDeltas, ticksPerOS);
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
            String fileName = "tmTicksCountScalingLaw" + "_" + dateString + ".csv";
            PrintWriter writer = new PrintWriter(dirName + "/" + fileName, "UTF-8");
            writer.println("Delta;OSticksNum");
            for (int i = 0; i < numPoints; i++){
                writer.println(arrayDeltas[i] + ";" + numDCs[i]);
            }
            writer.close();
            System.out.println("The file is saved as:   " + fileName);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public double[] getTicksPerOS() {
        return ticksPerOS;
    }

    public double[] getArrayDeltas() {
        return arrayDeltas;
    }
}
