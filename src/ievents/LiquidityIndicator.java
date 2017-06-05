package ievents;

import market.Price;
import tools.Tools;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by author.
 *
 * This class is a realization of the "Multi-scale Representation of High Frequency Market Liquidity" paper.
 *
 * An instance of the class should be run on every new observed Price.
 *
 * ATTENTION: parameters H1 and H2 should depend on the intrinsic network. H1 = 0.4604 and H2 = 0.70818 correspond to
 * the 12 dimensional intrinsic network.
 */
public class LiquidityIndicator {

    private final double MIN_THRESHOLD = 0.00025; // is the starting threshold of the intrinsic network
    private final int N_THRESHOLDS = 12; // is how many thresholds are in the intrinsic network
    private IntrinsicNetwork intrinsicNetwork; // an instance of the inttinsic network
    private long timeWindowMS; // is a length of a moving window used for the liquidity computation. In milliseconds
    private LinkedList<Transition> transitionList; // is a list of transitions observed at the latest price
    private double surprise; // is the surprise of the latest transition of the intrinsic network
    private double liquidity; // is the level of liquidity computed on the moving window
    private final double H1 = 0.4604; // is a constant of the first order of informativeness
    private final double H2 = 0.70818; // is a constant of the second order of informativeness
    public final long WEEKEND_TIME = 12960000; // this value is used in order to avoid weekends (see the avoidWeekend method)

    /**
     *
     * @param timeWindowMS is a length of a moving window used for the liquidity computation. In milliseconds
     */
    public LiquidityIndicator(long timeWindowMS){
        this.timeWindowMS = timeWindowMS;
        this.intrinsicNetwork = new IntrinsicNetwork(N_THRESHOLDS, MIN_THRESHOLD, 1);
        this.transitionList = new LinkedList<>();
        this.surprise = 0;
        this.liquidity = 0;
    }

    /**
     *
     * @param price is a new observed price
     * @return liquidity computed on the moving window
     */
    public double run(Price price){
        ArrayList<Double> surprisesList = intrinsicNetwork.run(price);
        if (surprisesList.size() > 0){
            if (transitionList.size() > 0){
                long distanceBetweenTransitions = price.getTime() - transitionList.getLast().getTime();
                if (distanceBetweenTransitions > WEEKEND_TIME){
                    avoidWeekend(distanceBetweenTransitions);
                }
            }
            for (double surprise : surprisesList){
                Transition transition = new Transition(price.getTime(), surprise);
                updateTransitionList(transition);
            }
            liquidity = computeLiquidity(transitionList);
        }
        return liquidity;
    }

    /**
     * The method should be called as soon as a new transition happens. It adds the new transition and remove the oldest
     * ones.
     * @param newTransition is an in a copy of the Transition class
     */
    private void updateTransitionList(Transition newTransition){
        while ((transitionList.size() > 0) && (newTransition.getTime() - transitionList.getFirst().getTime() > timeWindowMS)){
            transitionList.removeFirst();
        }
        transitionList.add(newTransition);
    }

    /**
     * The method computes liquidity based on the transitions registered within the moving window time period.
     * Equation 12.
     * @param transitionList is the list of transitions registered within the moving window time period
     * @return liquidity computed on the moving window time period
     */
    private double computeLiquidity(LinkedList<Transition> transitionList){
        double trajectorySurprise = computeTrajectorySurprise(transitionList);
        double x = (trajectorySurprise - transitionList.size() * H1) / (Math.sqrt(transitionList.size() * H2));
        return 1 - Tools.CumNorm(x);
    }

    /**
     * Computes total surprise of a trajectory within the moving window
     * Equation 10
     * @return total surprise of the trajectory
     */
    private double computeTrajectorySurprise(LinkedList<Transition> transitionList){
        double trajectorySurprise = 0;
        for (Transition transition : transitionList){
            trajectorySurprise += transition.getSurprise();
        }
        return trajectorySurprise;
    }

    /**
     * Since the moving window should go only through trading day, this method should be called every time when we
     * observe a new weekend. For example, when the distance between the time of the latest transition and the time of
     * the latest transition in the current set of transitions is bigger then 1 day.
     * @param twoTickDiff distance between the time of the latest transition and the time of the latest transition in
     *                    the current set of transitions
     */
    public void avoidWeekend(long twoTickDiff){
        for (Transition transition : transitionList){
            transition.setTime(transition.getTime() + twoTickDiff);
        }
    }


    public double getLiquidity(){
        return liquidity;
    }


    /**
     * The class holds time and surprise of each transition of the intrinsic network
     */
    private class Transition{

        private long time;
        private double surprise;

        private Transition(long time, double surprise){
            this.time = time;
            this.surprise = surprise;
        }

        public long getTime() {
            return time;
        }

        public double getSurprise() {
            return surprise;
        }

        public void setTime(long time){
            this.time = time;
        }

    }


}
