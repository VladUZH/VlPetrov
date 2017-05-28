package ievents;

import market.Price;

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
    private double surprise; // is the level of surprise at each moment of time

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
            int mode = i%2 == 0 ? 1 : -1;
            dcOSes[i] = new DcOS(threshold, threshold, mode, threshold * coefOSsize, threshold * coefOSsize);
            modeList[i] = mode;
            threshold *= 2;
        }
        surprise = 0;
    }

    /**
     * This method must be called at each new price
     * @param price is a new price
     * @return current level of surprise
     */
    public double run(Price price){
        int[] tempModeList = new int[nThresholds];
        for (int i = 0; i < nThresholds; i++){
            dcOSes[i].run(price);
            tempModeList[i] = dcOSes[i].getMode();
        }
        if (!Arrays.equals(tempModeList, modeList)){
            surprise = computeSurprise(modeList, tempModeList);
        }
        modeList = tempModeList.clone();
        return surprise;
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
     * The method compute transition probability of the intrinsic network, equations 11-14
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
        while (newModeList[indexNonEqualFirst] == newModeList[0]){ // here we are trying to find an index of the first mode non equal to the first one
            indexNonEqualFirst++;
        }
        if (indexNonEqualFirst == 1){
            if (indexUpdatedMode > 0){
                return Math.exp(-(dcOSes[1].getThresholdUp() - dcOSes[0].getThresholdUp()) / dcOSes[0].getThresholdUp());
            } else {
                return (1.0 - Math.exp(-(dcOSes[1].getThresholdUp() - dcOSes[0].getThresholdUp()) / dcOSes[0].getThresholdUp()));
            }
        } else if (indexNonEqualFirst > 1){
            double numerator = 0.0;
            for (int i = 1; i <= indexNonEqualFirst; i++){
                numerator -= (dcOSes[i].getThresholdUp() - dcOSes[i-1].getThresholdUp()) / dcOSes[i-1].getThresholdUp();
            }
            numerator = Math.exp(numerator);
            double denominator = 0.0;
            for (int i = 1; i <= indexNonEqualFirst - 1; i++){
                double secondValue = 0.0;
                for (int j  = i + 1; j <= indexNonEqualFirst; j++){
                    secondValue -= (dcOSes[j].getThresholdUp() - dcOSes[j-1].getThresholdUp()) / dcOSes[j-1].getThresholdUp();
                }
                denominator += (1.0 - Math.exp(-(dcOSes[i].getThresholdUp() - dcOSes[i-1].getThresholdUp()) / dcOSes[i-1].getThresholdUp())) * Math.exp(secondValue);
            }
            if (indexUpdatedMode > 0){
                return numerator / (1.0 - denominator);
            } else {
                return (1.0 - numerator / (1.0 - denominator));
            }
        } else {
            return 1.0;
        }
    }


}
