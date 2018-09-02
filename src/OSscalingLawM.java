import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Vladimir Petrov on 08.09.2016.
 */
public class OSscalingLawM {

    public double minDelta;
    public double maxDelta;
    public boolean logScale;
    public int nSteps;
    public double[] arrayOfdeltas;
    public RunnerM[] runnerAs;
    public double[] sumArray;
    public long[] numEventsArray;
    public String type; // define the version of the used algorithm. V (Vladimir), A(Anton), O(original)
    private boolean allToFile; // indicates whether we need to save all overshoots into a file: Timestamp,delta,osL
    private PrintWriter writer;


    /**
     *
     * @param minDelta in factions (0.01 = 1%)
     * @param maxDelta
     * @param nSteps
     * @param logScale
     */
    OSscalingLawM(double minDelta, double maxDelta, int nSteps, boolean logScale, String type){

        this.minDelta = minDelta;
        this.maxDelta = maxDelta;
        this.logScale = logScale;
        this.nSteps = nSteps;
        this.type = type;

        if (logScale){
            arrayOfdeltas = AdditionalTools.GenerateLogSpace(minDelta, maxDelta, nSteps);
        } else {
            arrayOfdeltas = AdditionalTools.GenerateLinSpace(minDelta, maxDelta, nSteps);
        }


        runnerAs = new RunnerM[nSteps];

        for (int i = 0; i < nSteps; i++){
            runnerAs[i] = new RunnerM(arrayOfdeltas[i], type);
        }

        sumArray = new double[nSteps];
        numEventsArray = new long[nSteps];

        System.out.println("Average Overshoot Move is initialized");

    }


    public void turnAllToFileOn(){
        this.allToFile = true;
        System.out.println("Save statistic to file = True");
        String dateString = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date());
        try {
            this.writer = new PrintWriter("Results/allOSs_" + dateString + ".csv","UTF-8");
            writer.println("Time,Delta,OSl");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void run(ATickM aTickM){
        for (int i = 0; i < nSteps; i++){
            if (Math.abs(runnerAs[i].run(aTickM)) == 1){
                sumArray[i] += runnerAs[i].osL;
                numEventsArray[i] += 1;
                if (allToFile){
                    String toFile = aTickM.getTime() + "," + Math.round(arrayOfdeltas[i] * 100000) / 100000.0  + ","
                            + Math.round(runnerAs[i].osL * 100000) / 100000.0;
//                    System.out.println(toFile);
                    writer.println(toFile);
                }
            }
        }
    }


    public void finish(){
        for (int i = 0; i < nSteps; i++){
            sumArray[i] /= numEventsArray[i];
        }
        if (allToFile){
            writer.close();
        }
    }


    public void saveToFile(){
        ArrayList<String> columnNames = new ArrayList<String>();
        columnNames.add("Delta");
        columnNames.add("Av_OS_move");
        columnNames.add("NumEvents");

        ArrayList<double[]> columnsData = new ArrayList<>();
        columnsData.add(arrayOfdeltas);
        columnsData.add(sumArray);
        columnsData.add(Arrays.stream(numEventsArray).asDoubleStream().toArray());

        AdditionalTools.saveResultsToFile("averageOvershoots", columnNames, columnsData, true);
    }


}
