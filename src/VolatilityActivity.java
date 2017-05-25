import ievents.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by author.
 *
 * This class is in essence a shell for the "VolatilitySeasonality". Here one can simply create an instance of the
 * class and get a result output without any additional actions. The class automatically finds the best thresholds for
 * the volatility analysis.
 *
 * The class returns the following information:
 *
 *  the coefficients of the DCcountScalingLaw;
 *  the best size of the intrinsic events threshold;
 *  complete information about spread;
 *  normalized volatility activity;
 *
 * It is worth noting that the algorithm implies a normalization function, which multiplies all activity values to a
 * coefficient which makes the average value of the distribution equal to 1.0
 *
 */

public class VolatilityActivity {

    private final float MIN_DELTA = 0.0001f; // min size of the intrinsic event threshold used for DCcountScalingLaw
    private final float MAX_DELTA = 0.10f; // max size of the intrinsic event threshold used for DCcountScalingLaw
    private final int NUM_DELTAS = 20; // is how many thresholds we use in order to get the DCcountScalingLaw
    private final int SHOW_EVERY = 1000000; // means that at every "SHOW_EVERY"th step the program shows data of the current price
    private String inputFileName; // is the name of a text filed used for analysis
    private String dateFormat; // if the file contains String format of date, in "dateFormat" one should contain format of the date. If date is Long in seconds, then leave ""
    private int nDecimals; // since we use the Long type for all prices, "nDecimals" keeps number of decimals
    private DCcountScalingLaw dCcountScalingLaw; // is an instance of the DCcountScalingLaw, we need it to find the best threshold
    private long timeOfBean; // here one can define the length of a bin for the volatility distribution, in milliseconds
    private double bestThreshold; // contains the size of the optimal threshold computed by with help of DCcountScalingLaw
    private long timeFirstPrice; // is Long time of the first given price which is used to find a normalized DCcountScalingLaw
    private long timeLastPrice; // is Long time of the last given price which is used to find a normalized DCcountScalingLaw
    private int expectedNDCperBean; // is used in order to find the best size of the threshold. How many DC IEs do you want to see within each bin of the distribution?
    private String delimiter; // since a file contains price data in the string format, "delimiter" is used to distinguish different fields of a raw
    private int askIndex, bidIndex, timeIndex; // contains indexes of the price details in a list of the price information
    private SpreadInfo spreadInfo; // in instance of the SpreadInfo which shows detailed information about the spread of the fed data
    private double[] volActivity; // is array of the volatility activity. Basically, we are looking for this array)
    private int[] dcCountList; // each element of the array contains total number of DC IEs observed within a given time period (bin).


    /**
     *
     * @param inputFileName is the name of a text file used for analysis: "D:/Data/crypto/btceUSD.csv"
     * @param dateFormat if the file contains String format of the price date, in "dateFormat" one should put format of that date ("yyyy.MM.dd HH:mm:ss.SSS"). If date is Long in milliseconds, then type ""
     * @param nDecimals "nDecimals" keeps number of decimals of the given set of prices: 4
     * @param timeOfBean here one can define the length of a bin for the volatility distribution, in milliseconds: 3600000L for an hour, 600000L for 10 minuts
     * @param expectedNDCperBean is how many DC IEs in average do you want to see within each bin of the distribution?: 1
     * @param delimiter of the text: ","
     * @param askIndex contains indexes of the price details in a list of the price information
     * @param bidIndex contains indexes of the price details in a list of the price information
     * @param timeIndex contains indexes of the price details in a list of the price information
     */
    public VolatilityActivity(String inputFileName, String dateFormat, int nDecimals, long timeOfBean, int expectedNDCperBean, String delimiter, int askIndex, int bidIndex, int timeIndex){
        this.inputFileName = inputFileName;
        this.dateFormat = dateFormat;
        this.nDecimals = nDecimals;
        this.timeOfBean = timeOfBean;
        this.expectedNDCperBean = expectedNDCperBean;
        this.delimiter = delimiter;
        this.askIndex = askIndex;
        this.bidIndex = bidIndex;
        this.timeIndex = timeIndex;
        this.spreadInfo = new SpreadInfo();
        DateTimeZone.setDefault(DateTimeZone.UTC); // it is an important field: without this the algorithm will
        // interpret time like my local time. https://stackoverflow.com/questions/9397715/defaulting-date-time-zone-to-utc-for-jodatimes-datetime

    }

    /**
     * The function should be called to start the analysis
     */
    public void go(){
        bestThreshold = findBestThreshold();
        computeVolatilitySeasonality(bestThreshold, timeOfBean);
        System.out.println("VolatilityActivity: DONE");
    }

    /**
     * The method computes the DCcountScalingLaw and finds the best size of the thresholds used for the intrinsic event
     * dissection function. The function needs to know how many DC IEs in average one expects to see within a bin of
     * the distribution.
     * @return the size of the optimal threshold
     */
    private double findBestThreshold(){
        dCcountScalingLaw = new DCcountScalingLaw(MIN_DELTA, MAX_DELTA, NUM_DELTAS);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
            bufferedReader.readLine().split(",").clone();
            String priceLine;
            long i = 0L;
            while ((priceLine = bufferedReader.readLine()) != null) {
                Price aPrice = Tools.priceLineToPrice(priceLine, delimiter, nDecimals, dateFormat, askIndex, bidIndex, timeIndex);
                dCcountScalingLaw.run(aPrice);
                spreadInfo.run(aPrice);
                if (i % SHOW_EVERY == 0) {
                    System.out.println(new DateTime(aPrice.getTime()));
                }
                if (timeFirstPrice == 0){
                    timeFirstPrice = aPrice.getTime();
                }
                timeLastPrice = aPrice.getTime();
                i++;
            }
            dCcountScalingLaw.normalize(timeFirstPrice, timeLastPrice);
            dCcountScalingLaw.saveResults("Results");
            spreadInfo.finish();
            double[] params = dCcountScalingLaw.computeParams();
            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r^2: " + params[2] + "\n");
            spreadInfo.printReport();

        } catch (Exception ex){
            ex.printStackTrace();
        }
        double bestThreshold = dCcountScalingLaw.findBestThreshold(timeOfBean, expectedNDCperBean);
        System.out.println("Best threshold: " + bestThreshold);
        return bestThreshold;
    }

    /**
     * The method compute the desired volatility distribution and saves it in an array.
     * @param threshold is the size of the optimal threshold used as an input of the VolatilitySeasonality instance
     * @param timeOfBean is the length of a bin in milliseconds
     */
    private void computeVolatilitySeasonality(double threshold, long timeOfBean){
        VolatilitySeasonality volatilitySeasonality = new VolatilitySeasonality(threshold, timeOfBean);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
            bufferedReader.readLine().split(",").clone();
            String priceLine;
            long i = 0L;
            while ((priceLine = bufferedReader.readLine()) != null) {
                Price aPrice = Tools.priceLineToPrice(priceLine, delimiter, nDecimals, dateFormat, askIndex, bidIndex, timeIndex);
                volatilitySeasonality.run(aPrice);
                if (i % SHOW_EVERY == 0) {
                    System.out.println(new DateTime(aPrice.getTime()));
                }
                i++;
            }
            volActivity = volatilitySeasonality.finish();
            dcCountList = volatilitySeasonality.getDcCountList();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * The method prints all computed results on a screen
     */
    public void printResults(){
        System.out.println("Bin N, Activity Coefficient, Total Num DC IEs");
        for (int index = 0; index < volActivity.length; index++){
            System.out.println(index + ", " + volActivity[index] + ", " + dcCountList[index]);
        }
    }

    /**
     * The method saves computed information to a *.csv file.
     * @param dirName is the path to a directory
     * @param name is preferred a part of the file name
     * @param simpleFormat is an option to save a file with all relevant information or only values of the volatility distribution
     */
    public void saveResults(String dirName, String name, boolean simpleFormat){
        Tools.CheckDirectory(dirName);
        try {
            String dateString = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date());
            String fileName = "volatActivity_" + name + "_" + String.format("%.5f", bestThreshold) + "_" + dateString + ".csv";
            PrintWriter writer = new PrintWriter(dirName + "/" + fileName, "UTF-8");
            if (simpleFormat){
                for (double activity : volActivity){
                    writer.println(activity);
                }
            } else {
                writer.println("Bin;Activity;TotalEvents");
                for (int i = 0; i < volActivity.length; i++){
                    writer.println(i + ";" + volActivity[i] + ";" + dcCountList[i]);
                }
            }
            writer.close();
            System.out.println("The file is saved as:   " + fileName);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


}
