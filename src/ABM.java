import java.util.Random;


public class ABM {

    public double sigma;
    public long nSteps;
    public Random random;
    public long step;
    public double prevValue;


    /**
     */
    ABM(double startValue, double sigma, long nSteps, boolean fixSeed){
        this.sigma = sigma;
        this.prevValue = startValue;
        this.nSteps = nSteps;
        if (fixSeed){
            random = new Random(0);
        } else {
            random = new Random();
        }
        step = 1;
    }

    /**
     */
    public double generateNextRandom(){
        double increment = random.nextGaussian() * sigma;
        prevValue += increment;
        return prevValue;
    }


}
