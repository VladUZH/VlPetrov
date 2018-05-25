package scalinglaws;

import market.Price;
import tools.Tools;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by author.
 *
 * This class computats of the "Price move count scaling law", Law 4 from
 * the "Patterns in high-frequency FX data: Discovery of 12 empirical scaling laws".
 *
 * One of the results of the computation is a file with two columns: Delta, MeanTimePriceMove.
 * Another result is the coefficients of the scaling law.
 *
 * The time is measured in seconds, price moves in fractions (0.01 = 1%)
 *
 * In order to run the code one should:
 *  choose the lowest and highest size of price moves;
 *  define number of points between the chosen thresholds;
 *  choose whether to store results to a file or no.
 */

public class MeanTimePriceMoveScalingLaw {

    private double[] arrayDeltas;
    private int numPoints;
    private final long MLSEC_IN_YEAR = 31557600000L;
    private double[] scalingLawParam;
    private long startTime, finalTime;
    private boolean initialized;
    private Price[] oldPrices; // levels at which the previous price move was registered
    private double[] numPriceMoves; // of specific size
    private double[] timeOfPriceMove; // is the time which have to elapse before the price experience a move of given size

    /**
     * The constructor of the class.
     * @param lowDelta
     * @param higDelta
     * @param numPoints
     */
    public MeanTimePriceMoveScalingLaw(float lowDelta, float higDelta, int numPoints){
        arrayDeltas = Tools.GenerateLogSpace(lowDelta, higDelta, numPoints);
        this.numPoints = numPoints;
        oldPrices = new Price[numPoints];
        numPriceMoves = new double[numPoints];
        timeOfPriceMove = new double[numPoints];
        initialized = false;
    }

    /**
     *
     * @param aPrice is the next observed (generated, recorded...) price
     */
    public void run(Price aPrice){
        if (!initialized){
            startTime = aPrice.getTime();
            for (int i = 0; i < numPoints; i++){
                oldPrices[i] = aPrice.clone();
            }
            initialized = true;
        }
        double midPrice = aPrice.getFloatMid();
        for (int i = 0; i < numPoints; i++){
            if (Math.abs(midPrice - oldPrices[i].getFloatMid()) / oldPrices[i].getFloatMid() >= arrayDeltas[i]){
                numPriceMoves[i] += 1;
                timeOfPriceMove[i] += aPrice.getTime() - oldPrices[i].getTime(); // aggregated time in milliseconds
                oldPrices[i] = aPrice.clone();
            }
        }
        finalTime = aPrice.getTime();
    }

    /**
     * Should be called in the very end of the experiment when the data is over
     * @return the scaling law parameters [C, E, r^2]
     */
    public double[] finish(){
        for (int i = 0; i < numPoints; i++){
            timeOfPriceMove[i] /= numPriceMoves[i] * 1000; // to find the average time in seconds
        }
        return computeParams();
    }

    /**
     * The function finds the scaling law parameters defined in "Patterns in high-frequency FX data: Discovery of 12
     * empirical scaling laws J.B.", page 13
     * @return the same what the function Tools.computeScalingParams returns
     */
    public double[] computeParams(){
        scalingLawParam = Tools.computeScalingParams(arrayDeltas, timeOfPriceMove);
        return scalingLawParam;
    }

    /**
     * @param dirName is the name of the output folder.
     */
    public void saveResults(String dirName){
        Tools.CheckDirectory(dirName);
        try {
            String dateString = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date());
            String fileName = "meanTimePriceMoveScalingLaw" + "_" + dateString + ".csv";
            PrintWriter writer = new PrintWriter(dirName + "/" + fileName, "UTF-8");
            writer.println("Delta;MeanTimePriceMove");
            for (int i = 0; i < numPoints; i++){
                writer.println(arrayDeltas[i] + ";" + numPriceMoves[i]);
            }
            writer.close();
            System.out.println("The file is saved as:   " + fileName);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public double[] getTimeOfPriceMove() {
        return timeOfPriceMove;
    }

    public double[] getArrayDeltas() {
        return arrayDeltas;
    }

}
