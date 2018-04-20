package ievents;

import market.Price;

/**
 * Created by author.
 *
 * The class RealizedVolatility is a practical realization of the theoretical work presented in the working paper
 * "Bridging the gap between physical and intrinsic time". This Intrinsic Event volatility Estimator is based on the
 * eq. 13. The right hand side of the equation is used to compute squared returns. It is the only crucial component of
 * traditional volatility estimators.
 */

public class RealizedVolatility {

    private double totalVolatility;
    private double normalizedVolatility;
    private DcOS dcOS;
    private long timeFirstPrice, timeLastPrice;
    private double sqrtOsDeviation;
    private final long MLSEC_IN_YEAR = 31536000000L;


    public RealizedVolatility(double threshold){
        timeFirstPrice = timeLastPrice = 0L;
        dcOS = new DcOS(threshold, threshold, -1, threshold, threshold, true);
    }

    public void run(Price aPrice){
        int event = dcOS.run(aPrice);
        if (event == 1 || event == -1){
            sqrtOsDeviation += dcOS.computeSqrtOsDeviation();
        }
        if (timeFirstPrice == 0){
            timeFirstPrice = aPrice.getTime();
        }
        timeLastPrice = aPrice.getTime();
    }

    public double finish(){
        totalVolatility = Math.sqrt(sqrtOsDeviation);
        return totalVolatility;
    }

    public double normalizeVolatility(double totalVolatility){
        double coef = (double) MLSEC_IN_YEAR / (timeLastPrice - timeFirstPrice);
        normalizedVolatility = totalVolatility * Math.sqrt(coef);
        return normalizedVolatility;
    }

    public double getTotalVolatility() {
        return totalVolatility;
    }

    public double getNormalizedVolatility() {
        return normalizedVolatility;
    }

    public long getTimeFirstPrice() {
        return timeFirstPrice;
    }

    public long getTimeLastPrice() {
        return timeLastPrice;
    }
}
