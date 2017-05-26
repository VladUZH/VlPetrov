package tools;

import java.util.Random;

/**
 * Created by author.
 * This class uses Geometrical Brownian Motion in order to generate a set of value with given number of elements.
 */
public class GBM {

    private float sigma;
    private float mu;
    private float deltaT;
    private float prevValue;
    private Random rand;
    private boolean initiated;

    /**
     * @param initialValue  - initial value;
     * @param sigma         - expected annual volatility;
     * @param nYears        - number of years;
     * @param nGenerations  - total number of generations;
     * @param mu            - yearly trend;
     */
    public GBM(float initialValue, float sigma, float nYears, long nGenerations, float mu){
        this.sigma = sigma;
        this.mu = mu;
        deltaT = nYears / nGenerations;
        prevValue = initialValue;
        rand = new Random();
        initiated = false;
    }

    /**
     * @return - the next generated value;
     */
    public float generateNextValue(){
        if (!initiated){
            initiated = true;
        } else {
            prevValue += prevValue * (mu * deltaT + sigma * Math.sqrt(deltaT) * rand.nextGaussian());
        }
        return prevValue;
    }



}
