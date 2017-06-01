package tools;

import java.util.Random;

/**
 * Created by author.
 * This class uses Brownian Motion in order to generate a set of value with given number of elements.
 *
 * IMPORTANT! Since the Brownian motion can be negative and is measured in absolute values (not relative), the initial
 * value of the generated time series must be equal to 1.0 in order to be able to compute volatility and other relative
 * measures.
 *
 */
public class BM {

    private float sigma;
    private float mu;
    private float deltaT;
    private float prevValue;
    private Random rand;
    private long nStep;

    /**
     * @param initialValue  - initial value;
     * @param sigma         - expected annual volatility;
     * @param nYears        - number of years;
     * @param nGenerations  - total number of generations;
     * @param mu            - yearly trend;
     */
    public BM(float initialValue, float sigma, float nYears, long nGenerations, float mu){
        prevValue = initialValue;
        this.sigma = sigma;
        this.mu = mu;
        deltaT = nYears / nGenerations;
        rand = new Random();
        nStep = 0L;
    }

    /**
     * @return - the next generated value;
     */
    public float generateNextValue(){
        prevValue += (float) (mu * deltaT + sigma * Math.sqrt(deltaT) * rand.nextGaussian());
        return prevValue;
    }

}
