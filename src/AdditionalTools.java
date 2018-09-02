import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vladimir on 12.04.16.
 */
public class AdditionalTools {


    static void saveResultsToFile(String fileName, ArrayList<String> columnNames, ArrayList<double[]> columns, boolean Double){
        try {
            String dateString = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date());

            fileName = fileName + "_" + dateString + ".csv";

            PrintWriter writer = new PrintWriter("Results/" + fileName, "UTF-8");

            String colimnString = "";
            for (String columnName : columnNames){
                colimnString += columnName + ";";
            }
            writer.println(colimnString);

            int index = 0;
            while (index < columns.get(0).length){
                String string = "";
                for (double[] array : columns){
                    string += array[index] + ";";
                }
                writer.println(string);
                index += 1;
            }
            writer.close();
            System.out.println("The result is saved like " + fileName);

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    static void saveResultsToFile(String fileName, ArrayList<String> columnNames, ArrayList<String[]> columns){
        try {
            String dateString = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date());

            fileName = fileName + "_" + dateString + ".csv";

            PrintWriter writer = new PrintWriter("Results/" + fileName, "UTF-8");

            String colimnString = "";
            for (String columnName : columnNames){
                colimnString += columnName + ";";
            }
            writer.println(colimnString);

            int index = 0;
            while (index < columns.get(0).length){
                String string = "";
                for (String[] array : columns){
                    string += array[index] + ";";
                }
                writer.println(string);
                index += 1;
            }
            writer.close();
            System.out.println("The result is saved like " + fileName);

        } catch (IOException e){
            e.printStackTrace();
        }

    }


    static double[] GenerateLogSpace(double min, double max, int nBins)
    {
        double[] logList = new double[nBins];
        double m = 1.0 / (nBins - 1);
        double quotient =  Math.pow(max / min, m);
        logList[0] = min;
        for (int i = 1; i < nBins; i++){
            logList[i] = logList[i - 1] * quotient;
        }

        return logList;
    }


    static double[] GenerateLinSpace(double min, double max, int nBins)
    {
        double[] linList = new double[nBins];

        double step = (max - min) / nBins;

        double nextPoint = min;

        for (int i = 0; i < nBins; i++){
            linList[i] = nextPoint;
            nextPoint += step;
        }

        return linList;
    }


    static void saveKDEToFile(String fileName, ArrayList<String> columnNames, ArrayList<ArrayList<Double>> columns){
        try {
            String dateString = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date());

            fileName = fileName + "_" + dateString + ".csv";

            PrintWriter writer = new PrintWriter("Results/" + fileName, "UTF-8");

            String colimnString = "";
            for (String columnName : columnNames){
                colimnString += columnName + ";";
            }
            writer.println(colimnString);

            int index = 0;
            while (index < columns.get(0).size()){
                String string = "";
                for (ArrayList<Double> array : columns){
                    if (index < array.size()) {
                        string += array.get(index) + ";";
                    } else {
                        string += "NaN;";
                    }
                }
                writer.println(string);
                index += 1;
            }
            writer.close();
            System.out.println("The result is saved like " + fileName);

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * The method converts an input String representing a certain tick into a ATickM element
     * @param line
     * @param bidIndex
     * @param askIndex
     * @return
     */
    public static ATickM lineToPrice(String line, int bidIndex, int askIndex){
        String lineData[] = line.split(",");
        return (new ATickM(new double[]{Double.parseDouble(lineData[bidIndex])}, new double[]{Double.parseDouble(lineData[askIndex])}));
    }



    /**
     * Converts a given string into Date format using the given dateFormat
     */
    static Date stringToDate(String inputStringDate, String dateFormat){
        DateFormat formatDate = new SimpleDateFormat(dateFormat, Locale.US);
        try {
            return formatDate.parse(inputStringDate);
        } catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }


}
