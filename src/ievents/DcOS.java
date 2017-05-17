package ievents; // stands for IntrinsicEvents

/**
 * Created by author.
 * The class receives prices and returns +1 or -1 in case of Directional-
 * Change (DC) Intrinsic Event (IE) upward or downward and also +2 or -2 in case of
 * an Overshoot (OS) IE upward or downward. Otherwise, returns 0;
 */

public class DcOS{

    private long extreme;
    private long prevExtreme;
    private double thresholdUp;  // 1% == 0.01
    private double thresholdDown;
    private double osSizeUp;
    private double osSizeDown;
    private int mode; // +1 for expected upward DC, -1 for expected downward DC
    private boolean initialized;
    private long reference;
    private long latestDCprice; // this is the price of the latest registered DC IE
    private long prevDCprice; // this is the price of the DC IE before the latest one

    public DcOS(double thresholdUp, double thresholdDown, int initialMode, double osSizeUp, double osSizeDown){
        this.initialized = false;
        this.thresholdUp = thresholdUp;
        this.thresholdDown = thresholdDown;
        this.mode = initialMode;
        this.osSizeUp = osSizeUp;
        this.osSizeDown = osSizeDown;
    }

    public DcOS(double thresholdUp, double thresholdDown, int initialMode, double osSizeUp, double osSizeDown, Price initPrice){
        this.initialized = true;
        this.thresholdUp = thresholdUp;
        this.thresholdDown = thresholdDown;
        this.mode = initialMode;
        this.osSizeUp = osSizeUp;
        this.osSizeDown = osSizeDown;
        extreme = prevExtreme = reference = prevDCprice = latestDCprice = (mode == 1 ? initPrice.getAsk() : initPrice.getBid());

    }

    public int run(Price aPrice){
        if (!initialized){
            initialized = true;
            extreme = prevExtreme = reference =  (mode == 1 ? aPrice.getAsk() : aPrice.getBid());
        } else {
            if (mode == 1){
                if (aPrice.getAsk() < extreme){
                    extreme = aPrice.getAsk();
                    if ( -Math.log((double) extreme / reference) >= osSizeDown){
                        reference = extreme;
                        return -2;
                    }
                    return 0;
                } else if (Math.log((double) aPrice.getBid() / extreme) >= thresholdUp){
                    prevDCprice = latestDCprice;
                    latestDCprice = aPrice.getBid();
                    prevExtreme = extreme;
                    extreme = reference = aPrice.getBid();
                    mode *= -1;
                    return 1;
                }
            }
            else if (mode == -1){
                if (aPrice.getBid() > extreme){
                    extreme = aPrice.getBid();
                    if (Math.log((double) extreme / reference) >= osSizeUp){
                        reference = extreme;
                        return 2;
                    }
                    return 0;
                } else if (-Math.log((double) aPrice.getAsk() / extreme) >= thresholdDown){
                    prevDCprice = latestDCprice;
                    latestDCprice = aPrice.getAsk();
                    prevExtreme = extreme;
                    extreme = reference = aPrice.getAsk();
                    mode *= -1;
                    return -1;
                }
            }
        }

        return 0;
    }

    /**
     * The function computes OS variability defined like a squared difference between the size of overshoot and
     * correspondent threshold. Details can be found in "Bridging the gap between physical and intrinsic time"
     * @return double variability of an overshoot.
     */
    public double computeOSvariability(){
        double osVariability;
        if (mode == 1){
            osVariability = Math.pow(computeOSsize() - thresholdUp, 2);
        } else {
            osVariability = Math.pow(computeOSsize() - thresholdDown, 2);
        }
        return osVariability;
    }

    public double computeOSsize(){
        return Math.abs(Math.log((double) latestDCprice / prevDCprice));
    }

    public long getLatestDCprice() {
        return latestDCprice;
    }

    public long getPrevDCprice() {
        return prevDCprice;
    }

    public long getExtreme() {
        return extreme;
    }

    public long getPrevExtreme() { return prevExtreme; }

    public void setExtreme(long extreme) {
        this.extreme = extreme;
    }

    public double getThresholdUp() {
        return thresholdUp;
    }

    public void setThresholdUp(float thresholdUp) {
        this.thresholdUp = thresholdUp;
    }

    public double getThresholdDown() {
        return thresholdDown;
    }

    public void setThresholdDown(float thresholdDown) {
        this.thresholdDown = thresholdDown;
    }

    public double getOsSizeUp() {
        return osSizeUp;
    }

    public void setOsSizeUp(float osSizeUp) {
        this.osSizeUp = osSizeUp;
    }

    public double getOsSizeDown() {
        return osSizeDown;
    }

    public void setOsSizeDown(float osSizeDown) {
        this.osSizeDown = osSizeDown;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public long getReference() {
        return reference;
    }

    public void setReference(long reference) {
        this.reference = reference;
    }
}
