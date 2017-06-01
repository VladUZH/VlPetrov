package ievents;

/**
 * Created by author.
 *
 * This class is built to hold information about an intrinsic event. It can be either a directional-change intrinsic
 * event (IE), or an overshoot IE.
 */
public class IE {

    private int type; // is a type of the IE: +1 or -1 for DC IEs, +2 and -2 for an overshoot IE
    private long time; // is when the IE happened
    private long level; // is the price level at which the IE happened
    private double osL; // is overshoot length, in fraction of the previous DC price

    public IE(int type, long time, long level, double osL){
        this.type = type;
        this.time = time;
        this.level = level;
        this.osL = osL;
    }

    public int getType() {
        return type;
    }

    public long getTime() {
        return time;
    }

    public long getLevel() {
        return level;
    }

    public double getOsL() {
        return osL;
    }
}
