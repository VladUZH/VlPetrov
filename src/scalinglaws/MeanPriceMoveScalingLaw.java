package scalinglaws;

import market.Price;
import tools.Tools;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * This class computes the "Number of Directional-Changes scaling law", Law 0a from
 * the "Patterns in high-frequency FX data: Discovery of 12 empirical scaling laws".
 *
 * One of the results of the computation is a file with two columns: DeltaT, MeanPriceMove.
 * Another result is the coefficients of the scaling law.
 *
 * *** ATTENTION *** the scaling law coefficients are computed assuming time intervals measured in seconds and the
 * percentage term 0.01 = 1%)
 *
 * In order to run the code one should:
 *  choose the lowest and highest size of the time interval deltaT (in seconds);
 *  define the number of points between the chosen thresholds;
 *  choose whether to store results to a file or no.
 */

public class MeanPriceMoveScalingLaw {

    private double[] arrayDeltasT; // to keep information on the size of each time interval to check
    private long[] endTimestamps; // end of each interval deltaT;
    private double[] sumPriceMoves; // holds the sum of all relative price moves registered within all deltaTs
    private double[] meanPriceMoves; // here the final result should be stored
    // and then average sizes
    private int[] numIntervals; // keeps numbers of all deltaT periods registered in the time series (to compute the
    // average in the end)
    private int numSteps; // how many intervals will be used to compute the scaling law
    private double[] scalingLawParam;
    private boolean initialized;
    private Price[] pricesOpen; // is the price registered right before the beginning of each interval
    private Price[] pricesEnd; // the prices right before the end of each deltaT
    private Price newestPrice; //


    /**
     * The constructor of the class.
     * @param numSteps
     */
    public MeanPriceMoveScalingLaw(float minDeltaT, float maxDeltaT, int numSteps){
        arrayDeltasT = Tools.GenerateLogSpace(minDeltaT, maxDeltaT, numSteps);
        endTimestamps = new long[numSteps];
        sumPriceMoves = new double[numSteps];
        numIntervals = new int[numSteps];
        pricesOpen = new Price[numSteps]; // TODO: change to double, will be faster
        pricesEnd = new Price[numSteps];
        meanPriceMoves = new double[numSteps];
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
                pricesOpen[i] = aPrice.clone();
                pricesEnd[i] = aPrice.clone();
            }
            newestPrice = aPrice;
            initialized = true;
        }
        for (int i = 0; i < numSteps; i++){
            while (aPrice.getTime() > endTimestamps[i]){
                newestPrice = aPrice.clone();
                double relatPriceMove = (pricesEnd[i].getFloatMid() - pricesOpen[i].getFloatMid()) /  pricesOpen[i].getFloatMid();
                sumPriceMoves[i] += Math.abs(relatPriceMove);
                numIntervals[i] += 1;
                pricesOpen[i] = pricesEnd[i].clone();
                endTimestamps[i] += (long) arrayDeltasT[i] * 1000;
            }
            pricesEnd[i] = aPrice.clone();
        }

    }


    /**
     * Should be called in the very end of the experiment when the data is over
     * @return the scaling law parameters [C, E, r^2]
     */
    public double[] finish(){
        for (int i = 0; i < numSteps; i++){
            meanPriceMoves[i] = sumPriceMoves[i] / numIntervals[i];
        }
        return computeParams();
    }

    /**
     * The function finds the scaling law parameters defined in "Patterns in high-frequency FX data: Discovery of 12
     * empirical scaling laws J.B.", page 13
     * @return the same what the function Tools.computeScalingParams returns
     */
    public double[] computeParams(){
        scalingLawParam = Tools.computeScalingParams(arrayDeltasT, meanPriceMoves);
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
            String fileName = "meanPriceMoveScalingLaw" + "_" + dateString + ".csv";
            PrintWriter writer = new PrintWriter(dirName + "/" + fileName, "UTF-8");
            writer.println("DeltaT;MeanPriceMove");
            for (int i = 0; i < numSteps; i++){
                writer.println(arrayDeltasT[i] + ";" + meanPriceMoves[i]);
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

    public double[] getMeanPriceMoves() {
        return meanPriceMoves;
    }

    public double[] getScalingLawParam(){
        return scalingLawParam;
    }
}
