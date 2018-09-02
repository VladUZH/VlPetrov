package market;

/**
 * The multidimensional version of price. Sets of bids and asks are formed by individual components of several exchange rates.
 */

public class PriceMultiD {

    private double[] bid;
    private double[] ask;
    private long time;


    PriceMultiD(double[] bid, double[] ask){
        this.bid = bid.clone();
        this.ask = ask.clone();
    }

    PriceMultiD(double[] bid, double[] ask, long time){
        this.bid = bid.clone();
        this.ask = ask.clone();
        this.time = time;
    }

    PriceMultiD(){
    }

    public double[] getBid() {
        return bid;
    }

    public double[] getAsk() {
        return ask;
    }

    public double[] getMid(){
        double[] midArray = new double[bid.length];
        for (int i = 0; i < bid.length; i++){
            midArray[i] = ( bid[i] + ask[i] ) / 2.0;
        }
        return midArray;
    }

    public long getTime(){
        return time;
    }

    public PriceMultiD clone(){
        return new PriceMultiD(this.bid.clone(), this.ask.clone(), this.time);
    }

    /**
     * The method normalizes all bids and asks on the referenceTick (useful when several dimensions are not of the same scale)
     * @param referenceTick
     */
    public void normalize(PriceMultiD referenceTick){
        double normMid[] = referenceTick.getMid();
        for (int i = 0; i < normMid.length; i++){
            bid[i] /= normMid[i];
            ask[i] /= normMid[i];
        }
    }


}
