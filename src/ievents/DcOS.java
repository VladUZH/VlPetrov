package ievents; // stands for IntrinsicEvents

/**
 * Created by author.
 * The class receives prices and returns +1 or -1 in case of Directional-
 * Change (DC) Intrinsic Event (IE) upward or downward and also +2 or -2 in case of
 * an Overshoot (OS) IE upward or downward. Otherwise, returns 0;
 */

public class DcOS{

    private long extreme;
    private float thresholdUp;  // 1% == 0.01
    private float thresholdDown;
    private float osSizeUp;
    private float osSizeDown;
    private int mode; // +1 for expected upward DC, -1 for expected downward DC
    private boolean initialized;
    private long reference;

    DcOS(float thresholdUp, float thresholdDown, int initialMode, float osSizeUp, float osSizeDown){
        this.initialized = false;
        this.thresholdUp = thresholdUp;
        this.thresholdDown = thresholdDown;
        this.mode = initialMode;
        this.osSizeUp = osSizeUp;
        this.osSizeDown = osSizeDown;
    }

    DcOS(float thresholdUp, float thresholdDown, int initialMode, float osSizeUp, float osSizeDown, Price initPrice){
        this.initialized = true;
        this.thresholdUp = thresholdUp;
        this.thresholdDown = thresholdDown;
        this.mode = initialMode;
        this.osSizeUp = osSizeUp;
        this.osSizeDown = osSizeDown;
        extreme = (mode == 1 ? initPrice.getAsk() : initPrice.getBid());
        reference = extreme;
    }

    public int run(Price aPrice){
        if (!initialized){
            initialized = true;
            extreme = (mode == 1 ? aPrice.getAsk() : aPrice.getBid());
            reference = extreme;
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
                    extreme = aPrice.getBid();
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
                } else if (-Math.log(aPrice.getAsk() / extreme) >= thresholdDown){
                    extreme = aPrice.getAsk();
                    mode *= -1;
                    return -1;
                }
            }
        }

        return 0;
    }

    public long getExtreme() {
        return extreme;
    }

    public void setExtreme(long extreme) {
        this.extreme = extreme;
    }

    public float getThresholdUp() {
        return thresholdUp;
    }

    public void setThresholdUp(float thresholdUp) {
        this.thresholdUp = thresholdUp;
    }

    public float getThresholdDown() {
        return thresholdDown;
    }

    public void setThresholdDown(float thresholdDown) {
        this.thresholdDown = thresholdDown;
    }

    public float getOsSizeUp() {
        return osSizeUp;
    }

    public void setOsSizeUp(float osSizeUp) {
        this.osSizeUp = osSizeUp;
    }

    public float getOsSizeDown() {
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
