package tools;
import market.Price;

import static tools.Tools.findBinId;

/**
 * This the class which finds the volatility seasonality described in the paper of Dacorogna et. al. "A geographical
 * model for the daily and weekly seasonal volatility in the foreign exchange market", page 420. The classical volatility
 * estimator uses traditional way of the computing volatility: squared returns. As the default time interval we take
 * 1 min. The length of each bin can be arbitrary specified.
 *
 * Here the Close price is put in the Ask value, when the Open price is in Bid one.
 */
public class ClassicVolatilitySeasonality {

    private static final long MLS_MIN = 60000L;
    private static final long MLS_WEAK = 604800000L; // number of milliseconds in a week
    private static final long MLS_YEAR = 31536000000L; // number of milliseconds in a year
    private long lenOfBin; // defines the length (in milliseconds) of one bin
    private long nBinsInWeek; // how many bins we have in one week considering the chosen bin size.
    private double[] activityList; // here we will store activity data for every bin.
    private long dateFirstTick, dateLastTick; // the date in milliseconds of the first and the last tick in the sample
    private boolean firstTick;
    private long[] timestampsOfBins;

    /**
     * @param lenOfBin is length (in milliseconds) of one bin
     */
    public ClassicVolatilitySeasonality(long lenOfBin){
        this.lenOfBin = lenOfBin;
        nBinsInWeek = MLS_WEAK / lenOfBin;
        this.timestampsOfBins = createTimestampsOfBins(lenOfBin);
        activityList = new double[(int) nBinsInWeek];
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
        double sqrtReturn = Math.pow(Math.log((double) aPrice.getAsk() / aPrice.getBid()), 2);
        int binId = findBinId(aPrice.getTime(), timestampsOfBins);
        activityList[binId] += sqrtReturn;
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
        returnsToVolatility();
        return activityList;
    }

    /**
     * Computes volatility seasonality array using the formula \sigma = \delta \sqrt( N_{DC} / T )
     */
    private void returnsToVolatility(){
        double numWeeksInWholeSample = (double) (dateLastTick - dateFirstTick) / MLS_WEAK;
        int numMinPerBin = (int)(lenOfBin / MLS_MIN);
        double numYearsInBin = (double) lenOfBin / MLS_YEAR;
        for (int i = 0; i < nBinsInWeek; i++){
            activityList[i] /= numWeeksInWholeSample; // find average sum of sqrt returns in each bin
            activityList[i] /= (numMinPerBin - 1);
            activityList[i] = Math.sqrt(activityList[i]);
            activityList[i] *= Math.sqrt(1.0 / numYearsInBin);
        }
    }



}
