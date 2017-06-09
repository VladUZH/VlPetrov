import ievents.MovingWindowVolatilityEstimator;
import market.Price;
import tools.Tools;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class can be used to perform a complete analysis of a given price set.
 * It saves such features as Time,Price,TraditVolat,IEvolat,NumDC
 */
public class VolatilityEstimatorMovingWindow_Analysis {

    private long updateEveryMS; // defines frequency of our measurements, in milliseconds
    private boolean initialized; //
    private long previousTime; // is time of the previous computation (measure)
    private MovingWindowVolatilityEstimator eImovingWindowVolatilityEstimator; // is an instance of the ievents/MovingWindowVolatilityEstimator
    private tools.MovingWindowVolatilityEstimator traditionalMovingWindowVolatilityEstimator; // is an instance of the tools.MovingWindowVolatilityEstimator
    private PrintWriter writer; // use it to write results to a file
    private final String dirName = "Results"; // shows where the results are going to be saved

    /**
     *
     * @param movingWindowMS is the size of the used movingWindow in milliseconds
     * @param updateEveryMS defines frequency of our measurements, in milliseconds
     * @param scalingParams scaling law parameters of the ievents/DCcountScalingLaw. The parameters must be annualized!
     */
    public VolatilityEstimatorMovingWindow_Analysis(long movingWindowMS, long updateEveryMS, double[] scalingParams){
        this.updateEveryMS = updateEveryMS;
        this.initialized = false;
        long periodLenMs = movingWindowMS / 1000L;
        double threshold = Tools.findDCcountThreshold(periodLenMs, 1, scalingParams);
        eImovingWindowVolatilityEstimator = new MovingWindowVolatilityEstimator(threshold, movingWindowMS);
        traditionalMovingWindowVolatilityEstimator = new tools.MovingWindowVolatilityEstimator(movingWindowMS, periodLenMs);
        initializeWriter(dirName);
    }

    /**
     * The method should be run for each new price
     * @param price is the new price
     */
    public void run(Price price){
        if (!initialized){
            previousTime = price.getTime();
            initialized = true;
        }
        eImovingWindowVolatilityEstimator.run(price);
        traditionalMovingWindowVolatilityEstimator.run(price);
        while (price.getTime() - previousTime > updateEveryMS){
            double traditionalVolatility = traditionalMovingWindowVolatilityEstimator.getAnnualVolatility();
            double ieVolatility = eImovingWindowVolatilityEstimator.computeAnnualVolatility();
            int numDc = eImovingWindowVolatilityEstimator.getNumIE();
            String toWrite = previousTime + "," + price.getFloatMid() + "," + traditionalVolatility + "," + ieVolatility + "," + numDc;
            System.out.println(toWrite);
            writeString(toWrite);
            previousTime += updateEveryMS;
        }
    }

    /**
     * The method creates a writer to save all results to a file afterward
     * @param dirName is where we'd like to save the results
     */
    private void initializeWriter(String dirName){
        try {
            Tools.CheckDirectory(dirName);
            String dateString = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date());
            String fileName = "movingWindowAnalysis_" + dateString + ".csv";
            writer = new PrintWriter(dirName + "/" + fileName, "UTF-8");
            String header = "Time,Price,TraditVolat,IEvolat,NumDC";
            writer.println(header);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * The method writes a given string to an external file
     * @param toWrite is the text line to write
     */
    private void writeString(String toWrite){
        writer.println(toWrite);
    }



}
