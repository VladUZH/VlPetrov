package ievents;

import market.Price;

import java.util.LinkedList;

/**
 * Created by author.
 *
 * This class computes annual volatility a movingWindow sample of data of the fixed length (movingWindowMS).
 * It uses Intrinsic Events approach from the paper "Bridging the gap between physical and intrinsic time", eq. 13.
 */
public class MovingWindowVolatilityEstimator {

    private double totalVolatility; // is volatility of the closes movingWindow period of time
    private double annualVolatility; // is the annual volatility computed by multiplying totalVolatility by the sqrt of the number of movingWindow in a year
    private DcOS dcOS; // is an instance of the DcOS class
    private final long MLSEC_IN_YEAR = 31536000000L; //
    private long movingWindowMS; // is the size of the used movingWindow in milliseconds
    private LinkedList<IE> ieLinkedList; // is the list of all intrinsic events within the moving window.
    private double sumSqrtOsDeviations; // is the sum of all overshoot deviations registered in the movingWindow time series

    /**
     *
     * @param threshold is the size of the threshold used by the DcOS class. Value has impact on the number of registered events
     * @param movingWindowMS is the size of the used movingWindow in milliseconds
     */
    public MovingWindowVolatilityEstimator(double threshold, long movingWindowMS){
        dcOS = new DcOS(threshold, threshold, -1, threshold, threshold, true);
        sumSqrtOsDeviations = 0;
        ieLinkedList = new LinkedList<>();
        this.movingWindowMS = movingWindowMS;
    }

    /**
     * The method must be called at each new price
     * @param aPrice is the new price
     */
    public void run(Price aPrice){
        int event = dcOS.run(aPrice);
        if (event == 1 || event == -1){
            IE ie = new IE(event, aPrice.getTime(), dcOS.getLatestDCprice(), dcOS.getOsL(), dcOS.computeSqrtOsDeviation());
            updateIELinkedList(ie, movingWindowMS);
        }
    }

    /**
     * The methods adds a new IE element to a list of all observed events. It removes all events out of the movingWindow
     * @param ie is the new IE
     * @param movingWindowMS is the size of the moving window in milliseconds
     */
    private void updateIELinkedList(IE ie, long movingWindowMS){
        ieLinkedList.add(ie);
        sumSqrtOsDeviations += ie.getSqrtOsDeviation();
        while ((ieLinkedList.size() > 0) && (ie.getTime() - ieLinkedList.getFirst().getTime() > movingWindowMS)){
            IE removedIE = ieLinkedList.removeFirst();
            sumSqrtOsDeviations -= removedIE.getSqrtOsDeviation();
        }
    }

    /**
     * Computes volatility on the chosen moving window time period
     * @return volatility of the chosen period
     */
    public double computeTotalVolatility(){
        totalVolatility = Math.sqrt(sumSqrtOsDeviations);
        return totalVolatility;
    }

    /**
     * Computes annual volatility based on the volatility of the chosen period movingWindow
     * @return annual volatility
     */
    public double computeAnnualVolatility(){
        totalVolatility = computeTotalVolatility();
        double coef = (double) MLSEC_IN_YEAR / movingWindowMS;
        annualVolatility = totalVolatility * Math.sqrt(coef);
        return annualVolatility;
    }

    public double getTotalVolatility() {
        return totalVolatility;
    }

    public double getAnnualVolatility() {
        return annualVolatility;
    }

    public int getNumIE(){
        return ieLinkedList.size();
    }
}