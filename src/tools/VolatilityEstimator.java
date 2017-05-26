package tools;

import market.Price;

import java.util.ArrayList;

/**
 * Created by author.
 *
 * This class is traditional volatility estimator. The algorithm has been taken from this resource:
 * http://www.macroption.com/historical-volatility-calculation/
 *
 * Here we use closing prices of a chosen period.
 *
 * Note, that the case when a price does not move within several time intervals has bot been considered. It is actually
 * a drawback of traditional volatility estimators.
 */
public class VolatilityEstimator {

    private final long MLSEC_IN_YEAR = 31557600000L; // number milliseconds in a year
    private long periodLenMs; // time length of a period used to compute returns
    private Price closingPrice; // contains closing price of each full period
    private boolean initiated; // shows whether one has put the first price to the algo or not
    private ArrayList<Double> compReturnArray; // contains returns for all observed full periods
    private Price prevPrice; // always holds the previous observed price
    private long initialTime; // holds the value of time in milliseconds of the price set beginning
    private long numPeriods; // is how many full periods the code observes withing the experiment
    private long timeLag; // is the distance from the first time and the time of the Nth period
    private double localVolatility; // is the average volatility of the chosen period
    private double totalVolatility; // is the total volatility of the full time range
    private double yearlyVolatility; // is the normalized to one year volatility

    public VolatilityEstimator(Long initialTime, Long periodLenMs){
        this.initialTime = initialTime;
        this.periodLenMs = periodLenMs;
        initiated = false;
        compReturnArray = new ArrayList<>();
        numPeriods = 0;
        timeLag = periodLenMs;
    }

    /**
     * Every new price should go through this method
     * @param aPrice
     */
    public void run(Price aPrice){
        if (!initiated){
            closingPrice = aPrice;
            prevPrice = aPrice;
            initiated = true;
        }
        else if (aPrice.getTime() - initialTime >= timeLag){
            double compReturn = Math.log(prevPrice.getMid() / closingPrice.getMid());
            compReturnArray.add(compReturn);
            closingPrice = prevPrice;
            numPeriods++;
            timeLag = periodLenMs * (numPeriods + 1);
        } else {
            prevPrice = aPrice;
        }
    }

    /**
     * The method should be called at the end of experiment. It computes three types of volatility.
     */
    public void finish(){
        double averageReturn = computeAverageReturn(compReturnArray);
        double sumSqrtDeviation = computeSumSqrtDeviation(compReturnArray, averageReturn);
        localVolatility = computeLocalVolatility(sumSqrtDeviation, numPeriods);
        totalVolatility = computeTotalVolatility(localVolatility, numPeriods);
        yearlyVolatility = computeYearlyVolatility(localVolatility);
    }

    /**
     * @param compReturnArray is an array with all observed returns
     * @return simply the average value of the given array
     */
    private double computeAverageReturn(ArrayList<Double> compReturnArray){
        double averageReturn = 0;
        for (double compReturn : compReturnArray){
            averageReturn += compReturn;
        }
        averageReturn /= compReturnArray.size();
        return averageReturn;
    }


    /**
     * The method is auxilarry function, computes sum of squared deviation of returns.
     * @param compReturnArray is an array with all observed returns
     * @param averageReturn simply the average value of the given array
     * @return sum of squared deviation of returns
     */
    private double computeSumSqrtDeviation(ArrayList<Double> compReturnArray, double averageReturn){
        double sumSqrtDeviation = 0;
        for (double compReturn : compReturnArray){
            sumSqrtDeviation += Math.pow(compReturn - averageReturn, 2);
        }
        return sumSqrtDeviation;
    }

    /**
     * The methods computes average volatility of the chosen time period
     * @return the average volatility (local volatility)
     */
    private double computeLocalVolatility(double sumSqrtDeviation, long numPeriods){
        return Math.sqrt(sumSqrtDeviation / (numPeriods - 1));
    }

    /**
     * The method compute volatility of the whole given time range of the data-set
     * @param localVolatility is the average volatility of the chosen period
     * @param numPeriods is the number of periods in the total time range
     * @return volatility of the whole time range of the given data-set
     */
    private double computeTotalVolatility(double localVolatility, long numPeriods){
        return Math.sqrt(numPeriods) * localVolatility;
    }

    /**
     * The method finds the normalized yearly volatility
     * @return normalized yearly volatility
     */
    private double computeYearlyVolatility(double localVolatility){
        double periodsInYear = (double) MLSEC_IN_YEAR / periodLenMs;
        return Math.sqrt(periodsInYear) * localVolatility;
    }

    public double getLocalVolatility() {
        return localVolatility;
    }

    public double getTotalVolatility(){
        return totalVolatility;
    }

    public double getYearlyVolatility(){
        return yearlyVolatility;
    }

    public long getNumPeriods() {
        return numPeriods;
    }
}
