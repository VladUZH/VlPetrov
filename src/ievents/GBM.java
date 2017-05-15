package ievents;

import java.util.Random;

/**
 * Created by author.
 * This class uses Geometrical Brownian Motion in order to generate a set of value with given number of elements.
 * The description of the used parameters can be seen above the first constructor.
 */
public class GBM {

    private float initialValue;
    private float sigma;
    private int nYears;
    private long nGenerations;
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
    public GBM(float initialValue, float sigma, int nYears, long nGenerations, float mu){
        this.initialValue = initialValue;
        this.sigma = sigma;
        this.nYears = nYears;
        this.nGenerations = nGenerations;
        this.mu = mu;
        deltaT = (float) nYears / nGenerations;
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
