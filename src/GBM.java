import java.util.Random;

/**
 * This class is able to create a sequence of random multidimensional values with the defined coefficient of correlation.
 */
public class GBM {

    public double[] mu;
    public double[] sigma;
    public long nSteps;
    public Random random;
    public long step;
    public double deltaT;
    public double[] prevValue;
    public double corr;
    public int numDimensions;
    private int dimChanged; //shows which dimension should be randomly shifted in case of the getSingleShift method
    private double[] tempShiftArray;


    /**
     * This is a primitive constructor: could be used to generate numDimensions arrays of random values with the same
     * trends and volatilityes.
     */
    GBM(int numDimensions, double startValue, double mu, double sigma, long nSteps, double corr, boolean fixSeed){
        this.numDimensions = numDimensions;
        this.mu = new double[numDimensions];
        this.sigma = new double[numDimensions];
        this.prevValue = new double[numDimensions];
        for (int i = 0; i < numDimensions; i++){
            this.prevValue[i] = startValue;
            this.mu[i] = mu;
            this.sigma[i] = sigma;
        }
        this.nSteps = nSteps;
        if (fixSeed){
            random = new Random(0);
        } else {
            random = new Random();
        }
        step = 1;
        deltaT = 1.0 / nSteps;
        this.corr = corr;
        dimChanged = 0;
        tempShiftArray = new double[numDimensions];
    }


    /**
     * In this constructor one can choose different trends and volatility unique for each generated time series.
     */
    GBM(double[] startValue, double[] mu, double[] sigma, long nSteps, double corr, boolean fixSeed){
        numDimensions = startValue.length;
        this.mu = new double[numDimensions];
        this.sigma = new double[numDimensions];
        this.prevValue = new double[numDimensions];
        for (int i = 0; i < numDimensions; i++){
            this.prevValue[i] = startValue[i];
            random = new Random();
        }
        this.mu = mu.clone();
        this.sigma = sigma.clone();
        this.nSteps = nSteps;
        if (fixSeed){
            random = new Random(0);
        } else {
            random = new Random();
        }
        step = 1;
        deltaT = 1.0 / nSteps;
        this.corr = corr;
    }

    /**
     * The method generates array of random values each of which is correlated with the first element with coefficient "corr".
     * All elements are generated simultaneously.
     * @return array of correlated random values.
     */
    public double[] generateNextRandom(){
        double[] R = new double[numDimensions];
        R[0] = random.nextGaussian();
        prevValue[0] += prevValue[0] * (mu[0] * deltaT + sigma[0] * Math.sqrt(deltaT) * R[0]);
        for (int i = 1; i < numDimensions; i ++){
            double tempR = random.nextGaussian();
            R[i] = corr * R[0] + Math.sqrt(1 - corr * corr) * tempR;
            prevValue[i] += prevValue[i] * (mu[i] * deltaT + sigma[i] * Math.sqrt(deltaT) * R[i]);
        }
        step++;
        return prevValue;
    }

//    /**
//     * The method generates array of random values each of which is correlated with the first element with coefficient "corr".
//     * All elements are generated simultaneously.
//     * @return array of correlated random values.
//     */
//    public double[] getSingleShift(){
//        if (dimChanged == 0){
//            tempShiftArray = prevValue.clone();
//            prevValue = generateNextRandom();
//        }
//        tempShiftArray[dimChanged] = prevValue[dimChanged];
//        dimChanged++;
//        if (dimChanged == numDimensions){
//            dimChanged = 0;
//        }
//        return tempShiftArray;
//    }


}
