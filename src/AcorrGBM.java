import java.util.LinkedList;
import java.util.Random;

/**
 * This class is able to create a sequence of random 1D values with the defined coefficient of 1 step autocorrelation.
 */
public class AcorrGBM {

    public double mu;
    public double sigma;
    public long nSteps;
    public Random random;
    public long step;
    public double deltaT;
    public double prevValue;
    public double acorr;
    private double sqrtDeltaT;
    private int tauLag;
    private LinkedList<Double> histShifts; // contains tauLag previous shifts to ensure the possibility to generate autocorrelation of fixed lag

    /**
     */
    AcorrGBM(double startValue, double mu, double sigma, long nSteps, int tauLag, double acorr, boolean fixSeed){
        this.mu = mu;
        this.sigma = sigma;
        this.prevValue = startValue;
        this.nSteps = nSteps;
        if (fixSeed){
            random = new Random(0);
        } else {
            random = new Random();
        }
        step = 0;
        deltaT = 1.0 / nSteps;
        sqrtDeltaT = Math.sqrt(deltaT);
        this.acorr = acorr;
        this.tauLag = tauLag;
        histShifts = new LinkedList<>();
    }


    /**
     * The method generates array of random values each of which is correlated with the first element with coefficient "corr".
     * All elements are generated simultaneously.
     * @return array of correlated random values.
     */
    public double generateNextRandom(){
        double shift = prevValue * (mu * deltaT + sigma * sqrtDeltaT * random.nextGaussian());
        histShifts.add(shift);
        if (step >= tauLag){
            prevValue += shift + histShifts.removeFirst() * acorr;
        }
        step++;
        return prevValue;
    }

}
