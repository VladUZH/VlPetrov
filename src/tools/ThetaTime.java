package tools;

/**
 * This class realizes the concept of theta time described in the work of Docorogna et. al. 1993 "A geographical model
 * for the daily and weekly seasonal volatility in the foreign exchange market". In the nutshell: theta time is used to
 * define time intervals of equal activity (in contrast to the physical time where the time distance between two ticks
 * is constant).
 */
public class ThetaTime {

    private static final long MLS_WEEK = 604800000L; // number of milliseconds in a week

    /**
     * The method takes the weekly activity seasonality as input and creates a set of Theta timestamps. One week will
     * contain numThetaBins intervals.
     * @param weeklyActivitySeasonality double array
     * @return array of timestamps in milliseconds starting from 0 and finishing at MLS_WEEK
     */
    public static long[] thetaTimestampsFromSeasonalityArray(double[] weeklyActivitySeasonality, int numThetaBins){
        int numActivityBins = weeklyActivitySeasonality.length;
        long lenOfSeasonalityBin = MLS_WEEK / numActivityBins;
        long[] thetaTimeStamps = new long[numThetaBins];
        double sumWeeklyActivity = 0;
        for (double activity : weeklyActivitySeasonality){
            sumWeeklyActivity += activity;
        }
        double activityPerThetaBin = sumWeeklyActivity / numThetaBins;

        double collectedActivity = 0;
        int thetaIndex = 0;

        for (int i = 0; i < numActivityBins; i++){
            long tBegOfBin = i * lenOfSeasonalityBin;

            collectedActivity += weeklyActivitySeasonality[i];
            while (collectedActivity >= activityPerThetaBin){
                double oldCollectedActivity = collectedActivity - weeklyActivitySeasonality[i];
                double activityToCover = activityPerThetaBin - oldCollectedActivity;  // the part of the activity covered by the new part of the seasonality
                thetaTimeStamps[thetaIndex] = (long) (tBegOfBin + lenOfSeasonalityBin * activityToCover / weeklyActivitySeasonality[i]);

                collectedActivity -= activityPerThetaBin;
                thetaIndex += 1;
            }
        }

        thetaTimeStamps[numThetaBins - 1] = MLS_WEEK;
        return thetaTimeStamps;
    }




}
