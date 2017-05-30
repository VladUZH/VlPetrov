package ievents;

import market.Price;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by author.
 *
 * IntrinsicNetwork is a realization of the Intrinsic Network introduced in the "Multi-scale Representation of High
 * Frequency Market Liquidity" paper.
 *
 * The class returns level of surprise for each new price.
 */
public class IntrinsicNetwork {

    private int nThresholds; // is the number of thresholds in the intrinsic network
    private double minThreshold; // is the lowest thresholds in the intrinsic network
    private DcOS[] dcOSes; // is array if DcOS instances which represents the mentioned network
    private double coefOSsize; // is an option to change set the size of the overshoot price move before a directional-change intrinsic event non equal to the size of base threshold
    private int[] modeList; // the state list holds current modes of the intrinsic event indicators. Only 1 or -1.
    private double latestSurprise; // is surprise of the latest observed transition of the intrinsic network

    /**
     *
     * @param nThresholds is the number of thresholds in the intrinsic network
     * @param minThreshold is the lowest thresholds in the intrinsic network
     * @param coefOSsize is an option to change set the size of the overshoot price move before a directional-change intrinsic event non equal to the size of base threshold
     */
    public IntrinsicNetwork(int nThresholds, double minThreshold, double coefOSsize){
        this.nThresholds = nThresholds;
        this.minThreshold = minThreshold;
        this.coefOSsize = coefOSsize;
        modeList = new int[nThresholds];
        dcOSes = new DcOS[nThresholds];
        double threshold = minThreshold;
        for (int i = 0; i < nThresholds; i++){
            int mode = (i%2 == 0 ? 1 : -1);
            dcOSes[i] = new DcOS(threshold, threshold, mode, threshold * coefOSsize, threshold * coefOSsize);
            modeList[i] = mode;
            threshold *= 2;
        }
        latestSurprise = 0;
    }

    /**
     * This method must be called at each new price. One tick can trigger several intrinsic events, this is important
     * to take into account. This why the method returns a list of surprises.
     * @param price is a new price
     * @return a list of all surprise observed at the given tick
     */
    public ArrayList<Double> run(Price price){
        int[] tempModeList = modeList.clone();
        ArrayList<Double> surpriseList = new ArrayList<>();
        for (int i = 0; i < nThresholds; i++){
            if (Math.abs(dcOSes[i].run(price)) == 1){
                tempModeList[i] = dcOSes[i].getMode();
                latestSurprise = computeSurprise(modeList, tempModeList);
                modeList = tempModeList.clone();
                surpriseList.add(latestSurprise);
            }
        }
        return surpriseList;
    }

    /**
     * The method compute surprise, equation 15
     * @param oldModeList is a list of IE modes before changes
     * @param newModeList is a new list of the IE modes, after changes
     * @return surprise
     */
    private double computeSurprise(int[] oldModeList, int[] newModeList){
        double transitionProbability = computeTransitionProbability(oldModeList, newModeList);
        return -Math.log(transitionProbability);
    }

    /**
     * The method compute transition probability of the intrinsic network, equations 3-6
     * @param oldModeList is a list of IE modes before changes
     * @param newModeList is a new list of the IE modes, after changes
     * @return transition probability
     */
    private double computeTransitionProbability(int[] oldModeList, int[] newModeList){
        int indexUpdatedMode = 0;
        while (newModeList[indexUpdatedMode] == oldModeList[indexUpdatedMode]){ // here we are looking for an index of the updated mode
            indexUpdatedMode++;
        }
        int indexNonEqualFirst = 1;
        while (oldModeList[indexNonEqualFirst] == oldModeList[0]){ // here we are trying to find an index of the first mode non equal to the first one. We use oldModeList, and this is correct.
            indexNonEqualFirst++;
        }
        if (indexNonEqualFirst == 1){ // equations 3-4
            if (indexUpdatedMode == 1){ // eq 3
                return Math.exp(-(dcOSes[1].getThresholdUp() - dcOSes[0].getThresholdUp()) / dcOSes[0].getThresholdUp());
            } else if (indexUpdatedMode == 0){ // eq 4
                return (1.0 - Math.exp(-(dcOSes[1].getThresholdUp() - dcOSes[0].getThresholdUp()) / dcOSes[0].getThresholdUp()));
            } else {
                System.out.println("Should not happen!");
            }
        } else if (indexNonEqualFirst > 1){ // equations 5-6
            double numerator = 1.0;
            for (int i = 1; i <= indexNonEqualFirst; i++){
                numerator *= Math.exp(-(dcOSes[i].getThresholdUp() - dcOSes[i-1].getThresholdUp()) / dcOSes[i-1].getThresholdUp());
            }
            double denominator = 0.0;
            for (int i = 1; i <= indexNonEqualFirst - 1; i++){
                double denominatorMultip = 1.0;
                for (int j = i + 1; j <= indexNonEqualFirst; j++){
                    denominatorMultip *= Math.exp(-(dcOSes[j].getThresholdUp() - dcOSes[j-1].getThresholdUp()) / dcOSes[j-1].getThresholdUp());
                }
                denominator += (1 - Math.exp(-(dcOSes[i].getThresholdUp() - dcOSes[i-1].getThresholdUp()) / dcOSes[i-1].getThresholdUp())) * denominatorMultip;
            }
            if (indexUpdatedMode > 0){ // eq 5
                return numerator / (1.0 - denominator);
            } else { // eq 6
                return (1.0 - numerator / (1.0 - denominator));
            }
        } else {
            return 1.0;
        }
        return 1.0;
    }

    public double getMinThreshold() {
        return minThreshold;
    }

    public double getCoefOSsize() {
        return coefOSsize;
    }

    public double getLatestSurprise() {
        return latestSurprise;
    }
}
