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
 * This class is dedicated to the computation of the "Number of Directional-Changes scaling law", Law 0b from
 * the "Patterns in high-frequency FX data: Discovery of 12 empirical scaling laws".
 *
 * The results can be normalized to one year of physical time, which which can be useful to compare parameters of the
 * scaling law for several time series with different time spans.
 *
 * One of the results of the computation is a file with two columns: Delta, NumDC.
 * Another result is the coefficients of the scaling law.
 *
 * In order to run the code one should:
 *  choose the lowest and highest size of thresholds;
 *  define number of points between the chosen thresholds;
 *  chose whether to normalize the result to one year of physical time or not;
 *  choose whether to store results to a file or no.
 */

public class DCcountScalingLaw {

    private double[] arrayDeltas;
    private DcOS[] dcOses;
    private double numDCs[];
    private int numPoints;
    private final long MLSEC_IN_YEAR = 31557600000L;
    private double[] scalingLawParam;
    private long startTime, finalTime;
    private boolean initialized;

    /**
     * The constructor of the class.
     * @param lowDelta
     * @param higDelta
     * @param numPoints
     */
    public DCcountScalingLaw(float lowDelta, float higDelta, int numPoints){
        arrayDeltas = Tools.GenerateLogSpace(lowDelta, higDelta, numPoints);
        dcOses = new DcOS[numPoints];
        for (int i = 0; i < numPoints; i++){
            double delta = arrayDeltas[i];
            dcOses[i] = new DcOS(delta, delta, 1, delta, delta, true);
        }
        numDCs = new double[numPoints];
        this.numPoints = numPoints;
        initialized = false;
    }

    /**
     *
     * @param aPrice is the next observed (generated, recorded...) price
     */
    public void run(Price aPrice){
        for (int i = 0; i < numPoints; i++){
            int event = dcOses[i].run(aPrice);
            if (event == 1 || event == -1){
                numDCs[i] += 1;
            }
        }
        if (!initialized){
            startTime = aPrice.getTime();
            initialized = true;
        }
        finalTime = aPrice.getTime();
    }

    /**
     * The function normalizes the number of DCs be multiplying them by moralization coefficient equal to the number
     * of analyzed time intervals in a full year.
     */
    public void normalize(){
        double normalCoeff = (double) MLSEC_IN_YEAR / (finalTime - startTime) ;
        for (int i = 0; i < numPoints; i++){
            numDCs[i] *= normalCoeff;
        }
        System.out.println("Normalize coefficients to 1 year: DONE");
    }

    /**
     * The function finds the scaling law parameters defined in "Patterns in high-frequency FX data: Discovery of 12
     * empirical scaling laws J.B.", page 13
     * @return the same what the function Tools.computeScalingParams returns
     */
    public double[] computeParams(){
        scalingLawParam = Tools.computeScalingParams(arrayDeltas, numDCs);
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
            String fileName = "3_dcCountScalingLaw" + "_" + dateString + ".csv";
            PrintWriter writer = new PrintWriter(dirName + "/" + fileName, "UTF-8");
            writer.println("Delta;NumDC");
            for (int i = 0; i < numPoints; i++){
                writer.println(arrayDeltas[i] + ";" + numDCs[i]);
            }
            writer.close();
            System.out.println("The file is saved as:   " + fileName);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * The function should return size of the best threshold which in average registers nDCs within timeInterval
     * @param timeInterval is the period of time used to find the best threshold, in milliseconds
     * @param expectedNDCs is how many DC IEs we would like to have in the given time interval (in average)
     * @return
     */
    public double findBestThreshold(long timeInterval, int expectedNDCs){
        return Tools.findDCcountThreshold(timeInterval, expectedNDCs, scalingLawParam);
    }

    public double[] getNumDCs() {
        return numDCs;
    }

    public double[] getArrayDeltas() {
        return arrayDeltas;
    }
}
