package ievents;

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

    Price(){}

    Price(int bid, int ask, long time){
        this.bid = bid;
        this.ask = ask;
        this.time = time;
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

    public float getMid(){
        return (bid + ask) / 2.0f;
    }

    public void setAsk(int ask){
        this.ask = ask;
    }

    public void setTime(long time){
        this.time = time;
    }

}
