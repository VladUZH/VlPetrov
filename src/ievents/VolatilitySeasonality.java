package ievents;

import market.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import tools.Tools;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by author.
 *
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
public class VolatilitySeasonality {

    private static final long MLS_WEAK = 604800000L; // number of milliseconds in a week
    private static final long MLS_YEAR = 31536000000L; // number of milliseconds in a year
    private long timeOfBin; // defines the length (in milliseconds) of one bin
    private DcOS dCoS; // an instance of the DcOS class which is used to compute all interested parameters.
    private long nBinsInWeek; // how many bins we have in one week considering the chosen bin size.
    private double[] activityList; // here we will store activity data for every bin.
    private List<List<Double>>  activityArrayList;
    private long timeFirstDC; // holds time of the first registered DC; will be used in the "computeTotalNumBins"
    private long timeLastDC; // holds time of the last registered DC; will be used in the "computeTotalNumBins"
    private int[] dcCountList; // here we shall store total number of DC IEs at each bin
    private int previousBinId;
    private double sumSqrtOsDeviation;
    private String averagingType;

    /**
     *
     * @param threshold is size of the threshold used to find the number of DC and the variability of overshoots
     * @param timeOfBin is length (in milliseconds) of one bin
     * @param averagingType is an option to chose "median" or "average" computation of volatility of each bin. Just put
     *                   "median" or "average"
     */
    public VolatilitySeasonality(double threshold, long timeOfBin, String averagingType){
        this.timeOfBin = timeOfBin;
        this.averagingType = averagingType;
        dCoS = new DcOS(threshold, threshold, 1, threshold, threshold, true);
        nBinsInWeek = MLS_WEAK / timeOfBin;
        activityList = new double[(int) nBinsInWeek];
        dcCountList = new int[(int) nBinsInWeek];
        activityArrayList = new ArrayList<List<Double>>((int) nBinsInWeek);
        initializeListOfList(activityArrayList, (int) nBinsInWeek);
        sumSqrtOsDeviation = 0.0;
    }

    /**
     * This method should be called for every new price. It checks whether the algo finds a new DC IE at the given
     * price or not and saves variability of overshoots to a preliminary array of data.
     * @param aPrice is every new price.
     */
    public void run(Price aPrice){
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
                activityArrayList.get(previousBinId).add(sumSqrtOsDeviation);
                previousBinId = binId;
                sumSqrtOsDeviation = dCoS.computeSqrtOsDeviation();
            }
            dcCountList[binId] += 1;
            timeLastDC = dcTime;
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
     * The finish function averages, normalizes to 1.0 and returns the all values in the activityList
     * @return activity distribution array
     */
    public double[] finish(){
        if (averagingType.equals("median")){
            medianActivity();
        } else if (averagingType.equals("average")){
            averageActivity();
        }
        normalizeActivity();
        return activityList;
    }


    /**
     * The function normalizes the activity distribution in order to have the mean value of it equal to 1.0
     */
    private void normalizeActivity(){
        double coef = (double) MLS_YEAR / timeOfBin;
        for (int i = 0; i < activityList.length; i++){
            activityList[i] = Math.sqrt(activityList[i] * coef);
        }
    }

    /**
     * The function simply divides all values in the activityList by the total number of full periods (weeks) in order
     * to find the average activity of each bin.
     */
    private void averageActivity(){
        for (int i = 0; i < activityList.length; i++){
            activityList[i] = Tools.findAverage(activityArrayList.get(i));
        }
    }

    /**
     * The method finds median values of the volatility sets
     */
    private void medianActivity(){
        for (int i = 0; i < activityList.length; i++){
            activityList[i] = Tools.findMedian(activityArrayList.get(i));
        }
    }

    public int[] getDcCountList(){
        return dcCountList;
    }

    public double[] getActivityList(){
        return activityList;
    }

    /**
     * This auxiliary function only initializes a List<List<Double>> adding empty lists
     * @param originalList
     * @param length
     */
    private void initializeListOfList(List<List<Double>> originalList, int length){
        for (int i = 0; i < length; i++){
            originalList.add(new ArrayList<Double>());
        }
    }




}
