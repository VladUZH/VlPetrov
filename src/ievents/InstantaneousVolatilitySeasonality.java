package ievents;

import market.*;

import static tools.Tools.findBinId;


/**
 * This class should do the following: compute weekly volatility seasonality similar to the one computed in Dacorogna
 * et. al. "A geographical model for the daily and weekly seasonal volatility in the foreign exchange market", page 420,
 * but it uses the number of directional-change intrinsic events as the indicator of the realized volatility (see "Waiting
 * Times and Number of Directional Changes in an Intrinsic Time Framework" (in progress) paper).
 *
 * The whole process consists of the following parts:
 *  1) define the input parameters of the analysis, such like size of threshold and the length of one bin (for example, 10 min);
 *  2) find number of directional changes for each bin of a week;
 *  3) compute average or median value of the previous step;
 *  4) compute volatility as \sigma = \delta \sqrt( N_{DC} / T ) where delta is the size of used threshold, N_{DC} is the
 *  number of DC events per given bin and T is the length in time of each bin;
 *  5) profit.
 */

public class InstantaneousVolatilitySeasonality {

    private static final long MLS_WEAK = 604800000L; // number of milliseconds in a week
    private static final long MLS_YEAR = 31536000000L; // number of milliseconds in a year
    private long lenOfBin; // defines the length (in milliseconds) of one bin
    private DcOS dCoS; // an instance of the DcOS class which is used to compute all interested parameters.
    private long nBinsInWeek; // how many bins we have in one week considering the chosen bin size.
    private double[] activityList; // here we will store activity data for every bin.
    private double[] dcCountList; // here we shall store total number of DC IEs at each bin
    private long dateFirstTick, dateLastTick; // the date in milliseconds of the first and the last tick in the sample
    private double threshold;
    private boolean firstTick;
    private long[] timestampsOfBins;

    /**
     * @param threshold is size of the threshold used to find the number of DC and the variability of overshoots
     * @param lenOfBin is length (in milliseconds) of one bin
     */
    public InstantaneousVolatilitySeasonality(double threshold, long lenOfBin){
        this.threshold = threshold;
        this.lenOfBin = lenOfBin;
        dCoS = new DcOS(threshold, threshold, 1, threshold, threshold, true);
        nBinsInWeek = MLS_WEAK / lenOfBin;
        this.timestampsOfBins = createTimestampsOfBins(lenOfBin);
        activityList = new double[(int) nBinsInWeek];
        dcCountList = new double[(int) nBinsInWeek];
        firstTick = true;
    }

    /**
     * This method should be called for every new price. It checks whether the algo finds a new DC IE at the given
     * price or not and adds +1 to the number of DC in the proper bin.
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
            int binId = findBinId(dcTime, timestampsOfBins);
            dcCountList[binId] += 1;
        }
    }

    /**
     * Creates a list of timestamps of all bins in a week
     * @param lenOfBin len in milliseconds of a bin
     * @return array of timestamps
     */
    private long[] createTimestampsOfBins(long lenOfBin){
        long[] timestampsOfBins = new long[(int)nBinsInWeek];
        for (int i = 0; i < nBinsInWeek; i++){
            timestampsOfBins[i] = (i + 1) * lenOfBin;
        }
        return timestampsOfBins;
    }

    /**
     * The method translates array of the numbers of intrinsic events into the volatility seasonality array and
     * returns it. Should be called in the end of the experiment.
     * @return activity distribution array
     */
    public double[] finish(){
        numDCtoVolatility();
        return activityList;
    }

    /**
     * Computes volatility seasonality array using the formula \sigma = \delta \sqrt( N_{DC} / T )
     */
    private void numDCtoVolatility(){
        double numWeeksInWholeSample = (double) (dateLastTick - dateFirstTick) / MLS_WEAK;
        for (int i = 0; i < dcCountList.length; i++){
            dcCountList[i] /= numWeeksInWholeSample; // find average num events per given bin // TODO: add median option
        }
        double numYearsInBin = (double) lenOfBin / MLS_YEAR;
        for (int i = 0; i < activityList.length; i++){
            activityList[i] = threshold * Math.sqrt((dcCountList[i]) / numYearsInBin) ;
        }
    }



}
