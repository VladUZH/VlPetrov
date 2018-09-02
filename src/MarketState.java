import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Vladimir Petrov on 29.06.2016.
 */
public class MarketState {

    public ArrayList<RunnerM> runners;
    public ArrayList<Double> thresholdsList;
    public short[] currentState;
    public short[] prevState;
    public boolean firstStep;


    MarketState(double lowestThreshold, double biggestThreshold){
        runners = new ArrayList<>();
        thresholdsList = new ArrayList<>();
        double threshold = lowestThreshold;
        while (threshold < biggestThreshold){
            thresholdsList.add(threshold);
            runners.add(new RunnerM(threshold, "S"));
            threshold = 2 * threshold;
        }
        currentState = CalcMarketState();
        prevState = currentState.clone();
        firstStep = true;
    }


    public boolean runTick(ATickM aTickM){
        for (RunnerM aRunnerM : runners){
            aRunnerM.run(aTickM);
        }
        prevState = currentState.clone();
        currentState = CalcMarketState();
        return !Arrays.equals(currentState, prevState);

    }


    public short[] CalcMarketState(){
        short[] marketState = new short[runners.size()];
        for (int i = 0; i < runners.size(); i++){
            if (runners.get(i).mode == 1){
                marketState[i] = 1;
            } else {
                marketState[i] = 0;
            }
        }
        return marketState;
    }


}
