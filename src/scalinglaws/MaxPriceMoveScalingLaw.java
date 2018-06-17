package scalinglaws;

import market.Price;
import tools.Tools;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * This class computes the "Maximal price move during scaling law", Law 3, p=1 from
 * the "Patterns in high-frequency FX data: Discovery of 12 empirical scaling laws".
 *
 * One of the results of the computation is a file with two columns: DeltaT, MaxPriceMove.
 * Another result is the coefficients of the scaling law.
 *
 * *** ATTENTION *** the scaling law coefficients are computed assuming time intervals measured in seconds and the
 * percentage term like 0.01 (equal to 1%)
 *
 * In order to run the code one should:
 *  choose the lowest and highest size of the time interval deltaT (in seconds);
 *  define the number of points between the chosen thresholds;
 *  choose whether to store results to a file or no.
 */

public class MaxPriceMoveScalingLaw {

    private double[] arrayDeltasT; // to keep information on the size of each time interval to check
    private long[] endTimestamps; // end of each interval deltaT;
    private double[] minPrices; // minimum prices
    private double[] maxPrices; // maximum prices

    private double[] sumMaxPriceMoves; // holds the sum of all relative maximum price moves registered within all deltaTs
    private double[] meanMaxPriceMoves; // here the final result should be stored
    // and then average sizes
    private int[] numIntervals; // keeps numbers of all deltaT periods registered in the time series (to compute the
    // average in the end)
    private int numSteps; // how many intervals will be used to compute the scaling law
    private double[] scalingLawParam;
    private boolean initialized;
    private double[] pricesOpen;
    private double[] pricesEnd;


    /**
     * The constructor of the class.
     * @param numSteps
     */
    public MaxPriceMoveScalingLaw(float minDeltaT, float maxDeltaT, int numSteps){
        arrayDeltasT = Tools.GenerateLogSpace(minDeltaT, maxDeltaT, numSteps);
        endTimestamps = new long[numSteps];
        minPrices = new double[numSteps];
        maxPrices = new double[numSteps];
        numIntervals = new int[numSteps];
        sumMaxPriceMoves = new double[numSteps];
        meanMaxPriceMoves = new double[numSteps];
        pricesOpen = new double[numSteps];
        pricesEnd = new double[numSteps];
        this.numSteps = numSteps;
        initialized = false;
    }


    /**
     * @param aPrice is the next observed (generated, recorded...) price
     */
    public void run(Price aPrice){
        if (!initialized){
            for (int i = 0; i < numSteps; i++){
                endTimestamps[i] = aPrice.getTime() + (long) arrayDeltasT[i] * 1000;
                double midPrice = aPrice.getFloatMid();
                minPrices[i] = midPrice;
                maxPrices[i] = midPrice;
                pricesOpen[i] = midPrice;
                pricesEnd[i] = midPrice;
            }
            initialized = true;
        }
        for (int i = 0; i < numSteps; i++){
            while (aPrice.getTime() > endTimestamps[i]){
                double maxPriceMove = (maxPrices[i] - minPrices[i]) /  minPrices[i];
                sumMaxPriceMoves[i] += maxPriceMove;
                numIntervals[i] += 1;
                pricesOpen[i] = pricesEnd[i];
                minPrices[i] = pricesEnd[i];
                maxPrices[i] = pricesEnd[i];
                endTimestamps[i] += (long) arrayDeltasT[i] * 1000;
            }
            double midPrice = aPrice.getFloatMid();
            pricesEnd[i] = midPrice;
            if (midPrice < minPrices[i]){
                minPrices[i] = midPrice;
            }
            if (midPrice > maxPrices[i]){
                maxPrices[i] = midPrice;
            }
        }

    }


    /**
     * Should be called in the very end of the experiment when the data is over
     * @return the scaling law parameters [C, E, r^2]
     */
    public double[] finish(){
        for (int i = 0; i < numSteps; i++){
            meanMaxPriceMoves[i] = sumMaxPriceMoves[i] / numIntervals[i];
        }
        return computeParams();
    }

    /**
     * The function finds the scaling law parameters defined in "Patterns in high-frequency FX data: Discovery of 12
     * empirical scaling laws J.B.", page 13
     * @return the same what the function Tools.computeScalingParams returns
     */
    public double[] computeParams(){
        scalingLawParam = Tools.computeScalingParams(arrayDeltasT, meanMaxPriceMoves);
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
            String fileName = "5_maxPriceMoveScalingLaw" + "_" + dateString + ".csv";
            PrintWriter writer = new PrintWriter(dirName + "/" + fileName, "UTF-8");
            writer.println("DeltaT;MaxPriceMove");
            for (int i = 0; i < numSteps; i++){
                writer.println(arrayDeltasT[i] + ";" + meanMaxPriceMoves[i]);
            }
            writer.close();
            System.out.println("The file is saved as:   " + fileName);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public double[] getArrayDeltasT() {
        return arrayDeltasT;
    }

    public double[] getMeanMaxPriceMoves() {
        return meanMaxPriceMoves;
    }

    public double[] getScalingLawParam(){
        return scalingLawParam;
    }
}
