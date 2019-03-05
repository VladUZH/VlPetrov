package tools;
import market.Price;
import java.util.LinkedList;

/**
 * This class is an example of the classical volatility estimator based on the squared returns. The class computes
 * annual volatility on a given moving window period of time. It uses an instance of the tools/TraditionalVolatility.
 */
public class MovingWindowVolatilityEstimator {

    private long movingWindowMS; // is the size of the used movingWindow in milliseconds
    private LinkedList<Price> priceLinkedList; // is the list of all prices which are in the moving window.
    private long periodLenMs; // is the length of a time period used to compute returns
    private TraditionalVolatility traditionalVolatility; // instance of the tools/TraditionalVolatility

    /**
     * Constructor
     * @param movingWindowMS // is the size of the used movingWindow in milliseconds
     * @param periodLenMs // is the length of a time period used to compute returns
     */
    public MovingWindowVolatilityEstimator(long movingWindowMS, long periodLenMs){
        priceLinkedList = new LinkedList<>();
        this.movingWindowMS = movingWindowMS;
        this.periodLenMs = periodLenMs;
    }

    /**
     * The method should be run for each new price
     * @param price is a new price
     */
    public void run(Price price){
        updatePriceLinkedList(price, movingWindowMS);
    }

    /**
     * The methods adds a new price to the list of all prices inside of the moving window and removes all old prices outside one.
     * @param price it a new price
     * @param movingWindowMS // is the size of the used movingWindow in milliseconds
     */
    private void updatePriceLinkedList(Price price, long movingWindowMS){
        priceLinkedList.add(price);
        while ((priceLinkedList.size() > 1) && (priceLinkedList.getLast().getTime() - priceLinkedList.get(1).getTime() > movingWindowMS)){
            priceLinkedList.removeFirst();
        }
    }

    /**
     * Computes volatility on the chosen moving window time period
     * @return volatility of the chosen moving window period
     */
    public double getTotalVolatility() {
        traditionalVolatility = new TraditionalVolatility(periodLenMs);
        for (Price price : priceLinkedList){
            traditionalVolatility.run(price);
        }
        traditionalVolatility.finish();
        return traditionalVolatility.getTotalVolatility();
    }

    /**
     * Computes annual volatility based on the volatility of the chosen period movingWindow
     * @return annual volatility
     */
    public double getAnnualVolatility() {
        traditionalVolatility = new TraditionalVolatility(periodLenMs);
        for (Price price : priceLinkedList){
            traditionalVolatility.run(price);
        }
        traditionalVolatility.finish();
        return traditionalVolatility.getAnnualVolatility();
    }
}
