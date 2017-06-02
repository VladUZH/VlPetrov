package market;

/**
 * Created by author.
 * Just simple class for prices.
 * The idea of Long prices comes from the fact that one should not use floats or doubles for prices. BigDecimals class
 * would be the best solution in this case, but it is about 1000 (!) slower then operations with Long numbers.
 */

public class Price {

    private long bid;
    private long ask;
    private long time;
    private int nDecimals; //this value is saved in order to show how many decimals has the original(not Long) value

    public Price(){}

    public Price(long bid, long ask, long time, int nDecimals){
        this.bid = bid;
        this.ask = ask;
        this.time = time;
        this.nDecimals = nDecimals;
    }

    public long getAsk() {
        return ask;
    }

    public long getBid() {
        return bid;
    }

    public long getTime() {
        return time;
    }
    
    public int getnDecimals() {
        return nDecimals;
    }

    public float getMid(){
        return (bid + ask) / 2.0f;
    }

    public void setAsk(int ask){
        this.ask = ask;
    }

    public void setBid(int bid){
        this.bid = bid;
    }

    public void setTime(long time){
        this.time = time;
    }

    public long getSpread(){
        return ask - bid;
    }

    public float getFloatAsk(){
        return (float) (ask * Math.pow(10, -nDecimals));
    }

    public float getFloatBid(){
        return (float) (bid * Math.pow(10, -nDecimals));
    }

    public float getFloatMid(){
        return (float) (getMid() * Math.pow(10, -nDecimals));
    }

}
