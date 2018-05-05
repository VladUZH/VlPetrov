package ievents;

import market.Price;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import tools.Tools;

import java.util.ArrayList;
import java.util.List;


/**
 * This class should do the following: compute weekly volatility distribution similar to the one computed in "A geographical
 * model for the daily and weekly seasonal volatility in the foreign exchange market", page 420, but it uses the
 * intrinsic event approach from ""Patterns in high-frequency FX data: Discovery of 12 empirical scaling laws" and
 * "Bridging the gap between physical and intrinsic time", equation 13. In other word, the computed volatility is based
 * on the sum of the variability of overshoots (OS).
 *
 * The whole process consists of the following parts:
 *  1) to define the input parameters of the analysis, such like size of threshold and the length of one bin (for example, 10 min);
 *  2) to find sum of the variability of overshoots for each bin of a week;
 *  3) to compute average value of the previous step
 *  4) to normalize the final value in such a way that the mean activity is equal to 1.0
 *  5) profit
 */
public class RealizedVolatilitySeasonality {

    private static final long MLS_WEAK = 604800000L; // number of milliseconds in a week
    private static final long MLS_YEAR = 31536000000L; // number of milliseconds in a year
    private long timeOfBin; // defines the length (in milliseconds) of one bin
    private DcOS dCoS; // an instance of the DcOS class which is used to compute all interested parameters.
    private double[] volatilityList; // here we will store activity data for every bin.
    private long timeFirstDC; // holds time of the first registered DC; will be used in the "computeTotalNumBins"
    private long dateFirstTick, dateLastTick; // the date in milliseconds of the first and the last tick in the sample
    private int previousBinId;
    private double sumSqrtOsDeviation;
    private boolean firstTick;

    /**
     *
     * @param threshold is size of the threshold used to find the number of DC and the variability of overshoots
     * @param timeOfBin is length (in milliseconds) of one bin
     */
    public RealizedVolatilitySeasonality(double threshold, long timeOfBin){
        this.timeOfBin = timeOfBin;
        dCoS = new DcOS(threshold, threshold, 1, threshold, threshold, true);
        int nBinsInWeek = (int) (MLS_WEAK / timeOfBin);
        volatilityList = new double[nBinsInWeek];
        sumSqrtOsDeviation = 0.0;
        firstTick = true;
    }


    /**
     * This method should be called for every new price. It checks whether the algo finds a new DC IE at the given
     * price or not and saves variability of overshoots to a preliminary array of data.
     * @param aPrice is every new price.
     */
    public void run(Price aPrice){
        if (firstTick){
            dateFirstTick = aPrice.getTime();
            firstTick = false;
        } else {
            dateLastTick = aPrice.getTime();
        }
        int iEvent = dCoS.run(aPrice);
        if (iEvent == 1 || iEvent == -1){
            long dcTime = aPrice.getTime();
            if (timeFirstDC == 0){
                timeFirstDC = dcTime;
                previousBinId = findBinId(dcTime);
            }
            int binId = findBinId(dcTime);
            if (binId == previousBinId){
                sumSqrtOsDeviation += dCoS.computeSqrtOsDeviation();
            } else {
                double volatOfBin = sumSqrtOsDeviation;
                volatilityList[previousBinId] += volatOfBin;
                previousBinId = binId;
                sumSqrtOsDeviation = dCoS.computeSqrtOsDeviation();
            }
        }
    }


    /**
     * This method compute the index of a bin in which the DC IE occurs.
     * @param dcTime is the time of the observed DC IE.
     * @return index (Id) ot the correspondent bin.
     */
    private int findBinId(long dcTime){
        DateTime dcDateTime = new DateTime(dcTime);
        long mondayBeforeTime = dcDateTime.withDayOfWeek(DateTimeConstants.MONDAY).withTimeAtStartOfDay().getMillis();
        long millsFromMonday = dcTime - mondayBeforeTime - 1;
        if (millsFromMonday > MLS_WEAK){
            System.out.println("ATTENTION! Winter time");
            millsFromMonday -= 3600000;
        }
        return (int)(millsFromMonday / timeOfBin);
    }


    /**
     * The finish function averages, normalizes to 1.0 and returns the all values in the volatilityList
     * @return activity distribution array
     */
    public double[] finish(){
        annualizeVolatility();
        return volatilityList;
    }


    /**
     * Computed volatility of each specific bin has to be annualized by multiplying the number by the sqrt of the number
     * of bins in a year.
     */
    private void annualizeVolatility(){
        double numWeeksInWholeSample = (double) (dateLastTick - dateFirstTick) / MLS_WEAK;
        for (int i = 0; i < volatilityList.length; i++){
            volatilityList[i] /= numWeeksInWholeSample; // find average overshoot variability per given bin // TODO: add median option
            volatilityList[i] = Math.sqrt(volatilityList[i]);
        }
        double numYearsInBin = (double) timeOfBin / MLS_YEAR;
        for (int i = 0; i < volatilityList.length; i++){
            volatilityList[i] /= Math.sqrt(numYearsInBin); // annualizing the volatility distribution
        }
    }


    public double[] getVolatilityList(){
        return volatilityList;
    }


}
