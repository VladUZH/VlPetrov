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
 * This class computats of the "Price move count scaling law", Law 2 from
 * the "Patterns in high-frequency FX data: Discovery of 12 empirical scaling laws".
 *
 * The results is normalized to one year of physical time.
 *
 * One of the results of the computation is a file with two columns: Delta, NumPriceMove.
 * Another result is the coefficients of the scaling law.
 *
 * In order to run the code one should:
 *  choose the lowest and highest size of thresholds;
 *  define number of points between the chosen thresholds;
 *  choose whether to store results to a file or no.
 */

public class PriceMoveCountScalingLaw {

    private double[] arrayDeltas;
    private int numPoints;
    private final long MLSEC_IN_YEAR = 31557600000L;
    private double[] scalingLawParam;
    private long startTime, finalTime;
    private boolean initialized;
    private double[] oldLevels; // levels at which the previous price move was registered
    private double[] numPriceMoves; // of specific size

    /**
     * The constructor of the class.
     * @param lowDelta
     * @param higDelta
     * @param numPoints
     */
    public PriceMoveCountScalingLaw(float lowDelta, float higDelta, int numPoints){
        arrayDeltas = Tools.GenerateLogSpace(lowDelta, higDelta, numPoints);
        this.numPoints = numPoints;
        oldLevels = new double[numPoints];
        numPriceMoves = new double[numPoints];
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
                oldLevels[i] = aPrice.getFloatMid();
            }
            initialized = true;
        }
        for (int i = 0; i < numPoints; i++){
            double midPrice = aPrice.getFloatMid();
            if (Math.abs(midPrice - oldLevels[i]) / oldLevels[i] >= arrayDeltas[i]){
                numPriceMoves[i] += 1;
                oldLevels[i] = midPrice;
            }
        }
        finalTime = aPrice.getTime();
    }

    /**
     * Should be called in the very end of the experiment when the data is over
     * @return the scaling law parameters [C, E, r^2]
     */
    public double[] finish(){
        normalize();
        return computeParams();
    }

    /**
     * The function normalizes the number of price moves to one year.
     */
    public void normalize(){
        double normalCoeff = (double) MLSEC_IN_YEAR / (finalTime - startTime) ;
        for (int i = 0; i < numPoints; i++){
            numPriceMoves[i] *= normalCoeff;
        }
        System.out.println("Normalize coefficients to 1 year: DONE");
    }

    /**
     * The function finds the scaling law parameters defined in "Patterns in high-frequency FX data: Discovery of 12
     * empirical scaling laws J.B.", page 13
     * @return the same what the function Tools.computeScalingParams returns
     */
    public double[] computeParams(){
        scalingLawParam = Tools.computeScalingParams(arrayDeltas, numPriceMoves);
        return scalingLawParam;
    }

    /**
     * @param dirName is the name of the output folder.
     */
    public void saveResults(String dirName){
        Tools.CheckDirectory(dirName);
        try {
            String dateString = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date());
            String fileName = "4_priceMoveCountScalingLaw" + "_" + dateString + ".csv";
            PrintWriter writer = new PrintWriter(dirName + "/" + fileName, "UTF-8");
            writer.println("Delta;NumPriceMove");
            for (int i = 0; i < numPoints; i++){
                writer.println(arrayDeltas[i] + ";" + numPriceMoves[i]);
            }
            writer.close();
            System.out.println("The file is saved as:   " + fileName);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public double[] getNumPriceMoves() {
        return numPriceMoves;
    }

    public double[] getArrayDeltas() {
        return arrayDeltas;
    }

}
