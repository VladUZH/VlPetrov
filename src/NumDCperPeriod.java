import ievents.*;

import market.*;
import tools.ThetaTime;

import java.util.ArrayList;
import java.util.Date;

import static tools.Tools.findBinId;


/**
 * This class computes number of directional changes in each period of week divided by bins. The bins could be
 * either equally spaced (physical time) or could be the so-called theta time (Dacorogna et. al. 1993).
 *
 * To use theta time one should upload seasonality array using the "uploadWeeklyActivitySeasonality" method. The
 * seasonality array could bo constructed by, for example, computing weekly volatility seasonality (see the
 * InstantaneousVolatilitySeasonality class).
 *
 * In order to run the code one should:
 *  choose a threshold used to register DC intrinsic events;
 *  select number of bins used to divide a week;
 *  choose whether to use the theta time or no.
 */

public class NumDCperPeriod {

    private static final long MLS_WEAK = 604800000L; // number of milliseconds in a week
    private long lenOfBin; // length (in milliseconds) of one bin in physical time
    private DcOS dCoS; // an instance of the DcOS class which is used to compute all interested parameters.
    private boolean firstTick; // used to properly initialize the process
    private int previousBinId;
    private int numDCinBin; // holds number of DCs in each bin
    private int numBins; // num bins in a week
    private ArrayList<Integer> binIndexesArray = new ArrayList<>(); // contains indexes of all observed bins
    private ArrayList<Integer> numDCsPerBinArray = new ArrayList<>(); // contains number of DCs in each observed bin
    private long[] timestampsOfBins;
    private long prevDCtime; // containss the timestamo of the precious tick. Used to check the distance between two consecutive events



    /**
     * Constructor
     * @param threshold is size of the threshold used to find the number of DC and the variability of overshoots
     */
    public NumDCperPeriod(double threshold, int numBins, boolean thetaTime){
        this.lenOfBin = MLS_WEAK / numBins;
        this.numBins = numBins;
        dCoS = new DcOS(threshold, threshold, 1, threshold, threshold, true);
        if (!thetaTime){
            timestampsOfBins = createTimestampsOfBins(lenOfBin);
        }
        firstTick = true;
        numDCinBin = 0;
    }


    /**
     * The method takes the the weekly activity seasonality as the input file. Used in case if one wants to use
     * the Theta time
     */
    public void uploadWeeklyActivitySeasonality(double[] weeklyActivitySeasonality){
        timestampsOfBins = ThetaTime.thetaTimestampsFromSeasonalityArray(weeklyActivitySeasonality, numBins);
    }


    /**
     * This method should be called for every new price. It checks whether the algo finds a new DC IE at the given
     * price or not and sums up all intrinsic events found in a bin together.
     * @param aPrice is every new price.
     */
    public void run(Price aPrice){
        int iEvent = dCoS.run(aPrice);
        if (iEvent == 1 || iEvent == -1){
            long dcTime = aPrice.getTime();
            if (firstTick){
                previousBinId = findBinId(dcTime, timestampsOfBins);
                prevDCtime = aPrice.getTime();
                firstTick = false;
            }
            int binId = findBinId(dcTime, timestampsOfBins);
            if (binId == previousBinId && (dcTime - prevDCtime) < MLS_WEAK / 2.0 ){  // same bin ID in the same week (divided by two just to be sure)
                numDCinBin += 1;
            } else {
//                System.out.println((new Date(aPrice.getTime())) + ", " + (previousBinId) + ", " + numDCinBin);
                binIndexesArray.add(previousBinId);
                numDCsPerBinArray.add(numDCinBin);


                // now we are filling all empty bins by zero
                int binDist = binId - previousBinId;
                if (binDist > 1){  // so there is some gap and the bins are in the same week
                    for ( int i = 1; i < binDist; i++){
                        binIndexesArray.add(previousBinId + i);
                        numDCsPerBinArray.add(0);
                    }
                } else if (binDist < -1){  // different weeks
                    int nBinTillEnd = numBins - previousBinId;
                    for ( int i = 1; i < nBinTillEnd; i++){  // filling till the end of the old week
                        binIndexesArray.add(previousBinId + i);
                        numDCsPerBinArray.add(0);
                    }
                    for ( int i = 0; i < binId; i++){  // filling from the beginning of the new week
                        binIndexesArray.add(i);
                        numDCsPerBinArray.add(0);
                    }
                }


//                if (binId < previousBinId){
//                    int distToEndWeek = numBins - previousBinId;
//                    int stepToFill = 0;
//                    while (distToEndWeek > 0){
//                        binIndexesArray.add(previousBinId + stepToFill);
//                        numDCsPerBinArray.add(0);
//                        stepToFill++;
//                        distToEndWeek--;
//                    }
//                    int distFromBegWeek = binId - numBins;
//                    stepToFill = 0;
//                    while (distFromBegWeek > 0){
//                        binIndexesArray.add(stepToFill);
//                        numDCsPerBinArray.add(0);
//                        stepToFill++;
//                        distToEndWeek--;
//                    }
//                }
//                else {
//                    for (int i = 1; i < binId - previousBinId; i++){
////                    System.out.println((new Date(aPrice.getTime())) + ", " + (previousBinId + i) + ", " + 0);
//                        binIndexesArray.add(previousBinId + i);
//                        numDCsPerBinArray.add(0);
//                    }
//                }


                prevDCtime = aPrice.getTime();
                previousBinId = binId;
                numDCinBin = 1;
            }
        }
    }

    /**
     * Creates a list of timestamps of all bins in a week
     * @param lenOfBin len in milliseconds of a bin
     * @return array of timestamps
     */
    private long[] createTimestampsOfBins(long lenOfBin){
        long[] timestampsOfBins = new long[numBins];
        for (int i = 0; i < numBins; i++){
            timestampsOfBins[i] = (i + 1) * lenOfBin;
        }
        return timestampsOfBins;
    }

    public ArrayList<Integer> getBinIndexesArray() {
        return binIndexesArray;
    }

    public ArrayList<Integer> getNumDCsPerBinArray() {
        return numDCsPerBinArray;
    }
}
