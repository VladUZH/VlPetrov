package ievents;

import market.Price;

/**
 * The class InstantaneousVolatility is a practical realization of the theoretical work presented in the paper
 * "Waiting Times and Number of Directional Changes in an Intrinsic Time Framework". This Intrinsic Event volatility
 * Estimator uses the number of directional-change intrinsic events as the indicator of the realized volatility.
 * The used equation is \sigma = \delta \sqrt( N_{DC} / T )
 */

public class InstantaneousVolatility {

    private DcOS dcOS; // the instance used to register intrinsic events
    private long timeFirstPrice, timeLastPrice; // to actualize the computed volat
    private int numDCs; // in the whole sample
    private final long MLSEC_IN_YEAR = 31536000000L;
    private double threshold; // used to register intrinsic events
    private double totalVolat; // is the volatility of the whole sample
    private double annualVolat; // annualized volatility

    /**
     * Constructor
     * @param threshold size in percentage (1% = 0.01)
     */
    public InstantaneousVolatility(double threshold){
        timeFirstPrice = timeLastPrice = 0L;
        dcOS = new DcOS(threshold, threshold, 1, threshold, threshold, true);
        this.threshold = threshold;
    }

    /**
     * Should be run for each price
     * @param aPrice
     */
    public void run(Price aPrice){
        int event = dcOS.run(aPrice);
        if (event == 1 || event == -1){
            numDCs += 1;
        }
        if (timeFirstPrice == 0){
            timeFirstPrice = aPrice.getTime();
        }
        timeLastPrice = aPrice.getTime();
    }

    /**
     * Run it in the very end of the analysis
     * @return the annualized volatility
     */
    public void finish(){
        numDCtoVolatility();
    }

    /**
     * Computes volatility using the formula \sigma = \delta \sqrt( N_{DC} / T )
     */
    private void numDCtoVolatility(){
        double numYearsInWholeSample = (double) (timeLastPrice - timeFirstPrice) / MLSEC_IN_YEAR;
        annualVolat = threshold * Math.sqrt(numDCs / numYearsInWholeSample);
        totalVolat = annualVolat * Math.sqrt(numYearsInWholeSample);
    }

    public double getTotalVolat() {
        return totalVolat;
    }

    public double getAnnualVolat() {
        return annualVolat;
    }
}
