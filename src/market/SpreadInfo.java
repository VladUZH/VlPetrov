package market;

import java.util.TreeMap;

/**
 * Created by author.
 * This class should compute information about the spread of the given set of prices.
 *
 * The class computes: minimum and maximum spread, mean and medium values. It also stores number of all given prices.
 */
public class SpreadInfo {

    private int medianSpread;
    private int minSpread;
    private int maxSpread;
    private double meanSpread;
    private TreeMap<Integer, Integer> spreadTreeMap;
    private long nPrices;

    public SpreadInfo(){
        spreadTreeMap = new TreeMap<>();
        nPrices = 0L;
    }

    /**
     * The function takes a new price and puts the spread of it to a TreeMap dictionary
     * @param aPrice is a new price
     */
    public void run(Price aPrice){
        nPrices++;
        int spread = (int) aPrice.getSpread();
        if (spreadTreeMap.containsKey(spread)){
            spreadTreeMap.put(spread, spreadTreeMap.get(spread) + 1);
        } else {
            spreadTreeMap.put(spread, 1);
        }
    }

    /**
     * The method must be called when there is no data for analysis anymore
     */
    public void finish(){
        minSpread = computeMinSpread();
        maxSpread = computeMaxSpread();
        medianSpread = computeMedianSpread();
        meanSpread = computeMeanSpread();
    }

    private int computeMinSpread(){
        return spreadTreeMap.firstKey();
    }

    private int computeMaxSpread(){
        return spreadTreeMap.lastKey();
    }

    private int computeMedianSpread(){
        long halfNprices = nPrices / 2;
        long numSpreads = 0L;
        int medianSpread = 0;
        for (int key : spreadTreeMap.keySet()){
            numSpreads += spreadTreeMap.get(key);
            if (numSpreads >= halfNprices){
                medianSpread = key;
                break;
            }
        }
        return medianSpread;
    }

    private double computeMeanSpread(){
        long sumValues = 0L;
        for (int spread : spreadTreeMap.keySet()){
            sumValues += spread * spreadTreeMap.get(spread);
        }
        return (double) sumValues / nPrices;
    }

    public void printReport(){
        System.out.println("SpreadInfo: \nNum Prices: "  + nPrices + "; Min: " + minSpread + "; Max: " + maxSpread +
                "; Mean: " + meanSpread + "; Median: " + medianSpread + "\n");
    }

    public int getMedianSpread() {
        return medianSpread;
    }

    public int getMinSpread() {
        return minSpread;
    }

    public int getMaxSpread() {
        return maxSpread;
    }

    public double getMeanSpread() {
        return meanSpread;
    }

    public long getnPrices() {
        return nPrices;
    }
}
