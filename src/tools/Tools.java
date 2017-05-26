package tools;

import market.Price;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by author.
 * The class Tools holds a set of auxiliary functions which can be used in order to simplify work on the general ideas.
 */
public class Tools {

    /**
     * The function checks if the certain directory exists and create it if it does not.
     * @param dirName is the name of the directory to check
     * @return true if the directory was created
     */
    public static boolean CheckDirectory(String dirName){
        boolean dirCreated = false;
        File theDir = new File(dirName);
        if (!theDir.exists()) {
            try{
                dirCreated = theDir.mkdir();
                System.out.println("DIR created");
            }
            catch(SecurityException se){
                se.printStackTrace();
            }
        }
        return dirCreated;
    }

    /**
     * The function GenerateLogSpace generates a list of logarithmically distributed values.
     * @param min
     * @param max
     * @param nBins
     * @return array of logarithmically distributed floats
     */
    public static double[] GenerateLogSpace(float min, float max, int nBins)
    {
        double[] logList = new double[nBins];
        float m = 1.0f / (nBins - 1);
        float quotient =  (float) Math.pow(max / min, m);
        logList[0] = min;
        for (int i = 1; i < nBins; i++){
            logList[i] = logList[i - 1] * quotient;
        }
        return logList;
    }

    /**
     * The function returns a random value which is correlated with the random input value. The formula has been taken from the
     * https://quant.stackexchange.com/questions/24472/two-correlated-brownian-motions.
     * @param inputRand is the input value which you want to use as the original value.
     * @param corrCoeff is the size of the correlation coeff.
     * @return correlated value.
     */
    public static double getCorrelatedRandom(double inputRand, double corrCoeff){
        Random random = new Random();
        return corrCoeff * inputRand + Math.sqrt(1 - corrCoeff * corrCoeff) * random.nextGaussian();
    }

    /**
     * The function computes parameters of the simple linear regression. It takes two arrays (X and Y) and returns an
     * array of three parameters: slope, intersection, correlation coefficient.
     * @param setX is input x
     * @param setY is input y
     * @return float array: slope, intersection, r-sqrt (error)
     */
    public static double[] linRegres(double[] setX, double[] setY)
    {
        double sumx  = 0.0;                     // sum { x[i],      i = 1..n }
        double sumy  = 0.0;                     // sum { y[i],      i = 1..n }
        double sumx2 = 0.0;                     // sum { x[i]*x[i], i = 1..n }
        double sumy2 = 0.0;                     // sum { y[i]*y[i], i = 1..n }
        double sumxy = 0.0;                     // sum { x[i]*y[i], i = 1..n }
        int lenInput = setX.length;
        // read data and compute statistics
        for (int i = 0; i < lenInput; i++) {
            sumx  += setX[i];
            sumy  += setY[i];
            sumx2 += setX[i] * setX[i];
            sumy2 += setY[i] * setY[i];
            sumxy += setX[i] * setY[i];
        }
        double slope = (lenInput * sumxy - sumx * sumy) / (lenInput * sumx2 - sumx * sumx);
        double intersect = (sumy - slope * sumx) / lenInput;
        double rSqrt = Math.pow((lenInput * sumxy - sumx * sumy) / Math.sqrt((lenInput * sumx2 - sumx * sumx) * (lenInput * sumy2 - sumy * sumy)), 2);

        double[] toReturn = new double[3];
        toReturn[0] = slope;
        toReturn[1] = intersect;
        toReturn[2] = rSqrt;

        return toReturn;
    }

    /**
     * The function to compute log values of an array.
     * @param inputArray is what you want to convert to log values
     * @return array with logs of the initial values
     */
    public static double[] toLog(double[] inputArray){
        double[] logArray = new double[inputArray.length];
        for (int i = 0; i < inputArray.length; i++){
            if (inputArray[i] != 0){
                logArray[i] = Math.log(inputArray[i]);
            }
        }
        return logArray;
    }

    /**
     * The function is dedicated to the computation of the scaling law parameters. These parameters are C, E and
     * R-squared. The used formula was found in the page 13 of the "Patterns in high-frequency FX data: Discovery
     * of 12 empirical scaling laws J.B.". IMPORTANT! For the scaling laws here we use 0.01 to define 1%. In the
     * original article they use 1.0 for the same value. Therefore, the parameter C should be multiplied by 100
     * in order to have the comparable coefficients.
     * @param arrayX is array x values
     * @param arrayY is array y values
     * @return a double array of the following values: [C, E, r^2]
     */
    public static double[] computeScalingParams(double[] arrayX, double[] arrayY){
        double[] linRegParam = Tools.linRegres(Tools.toLog(arrayX), Tools.toLog(arrayY));
        double[] params = new double[3];
        double paramC = Math.exp(-linRegParam[1] / linRegParam[0]);
        params[0] = paramC;
        double paramE = linRegParam[0];
        params[1] = paramE;
        double rSqrt = linRegParam[2];
        params[2] = rSqrt;
        return params;
    }


    /**
     * The method converts a String into a Date instance.
     * @param inputStringDate is the date in the String format, for example "1992.12.01 13:23:54.012"
     * @param dateFormat is the date format, for example "yyyy.MM.dd HH:mm:ss.SSS"
     * @return converted to the Date format
     */
    public static Date stringToDate(String inputStringDate, String dateFormat){
        DateFormat formatDate = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        try {
            return formatDate.parse(inputStringDate);
        } catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method should convert a string of information about price to the proper Price format. IMPORTANT: by default
     * the time of a price is supposed to be given in sec.
     * @param inputString is a string which describes a price. For example, "1.23,1.24,12213"
     * @param delimiter in the previous example the delimiter is ","
     * @param nDecimals is how many numbers a price has after the point. 2 in the example
     * @param dateFormat is the date format if any. Otherwise, one should write ""
     * @param askIndex index of the ask price in the string format
     * @param bidIndex index of the bid price in the string format
     * @param timeIndex index of the time in the string format
     * @return an instance Price
     */
    public static Price priceLineToPrice(String inputString, String delimiter, int nDecimals, String dateFormat, int askIndex, int bidIndex, int timeIndex){
        String[] priceInfo = inputString.split(delimiter);
        long bid = (long) (Double.parseDouble(priceInfo[bidIndex]) * Math.pow(10, nDecimals));
        long ask = (long) (Double.parseDouble(priceInfo[askIndex]) * Math.pow(10, nDecimals));
        long time;
        if (dateFormat.equals("")){
            time = Long.parseLong(priceInfo[timeIndex]) * 1000L;
        } else {
            time = stringToDate(priceInfo[timeIndex], dateFormat).getTime();
        }
        return new Price(bid, ask, time, nDecimals);
    }






}