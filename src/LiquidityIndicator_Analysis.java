import ievents.LiquidityIndicator;
import market.Price;
import tools.Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by author.
 *
 * This class is a simple shell for the LiquidityIndicator class from the ievent package. It just needs a path to a
 * file and several parameters.
 *
 * The final result will be saved to a file. It contains a time and liquidity of each tick
 */
public class LiquidityIndicator_Analysis {

    private LiquidityIndicator liquidityIndicator; // is an instance of the ievent LiquidityIndicator
    private String inputFileName; // is a path to the analysed file
    private String dateFormat; // format of a date in the analyzed file, for example "yyyy-MM-dd HH:mm:ss.SSS"
    private int nDecimals; // is number of decimals of each price in the data set
    private long timeWindowMS; // is a length of a moving window used for the liquidity computation. In milliseconds
    private String delimiter; // is delimiter between data in a String from the analysed file
    private int askIndex, bidIndex, timeIndex; // are indexes of the mentioned information an a String row from the file
    private double minThreshold; // is the start threshold of the intrinsic network
    private int nThresholds; // is how many thresholds are in the intrinsic network
    private final int SHOW_EVERY = 1000000; // means that at every "SHOW_EVERY"th step the program shows data of the current price

    /**
     *
     * @param inputFileName is a path to the analysed file
     * @param dateFormat format of a date in the analyzed file, for example "yyyy-MM-dd HH:mm:ss.SSS"
     * @param nDecimals is number of decimals of each price in the data set
     * @param timeWindowMS is a length of a moving window used for the liquidity computation. In milliseconds
     * @param minThreshold is the start threshold of the intrinsic network
     * @param nThresholds is how many thresholds are in the intrinsic network
     * @param delimiter is delimiter between data in a String from the analysed file
     * @param askIndex is an index of the mentioned information an a String row from the file
     * @param bidIndex is an index of the mentioned information an a String row from the file
     * @param timeIndex is an index of the mentioned information an a String row from the file
     */
    public LiquidityIndicator_Analysis(String inputFileName, String dateFormat, int nDecimals, long timeWindowMS, double minThreshold, int nThresholds, String delimiter, int askIndex, int bidIndex, int timeIndex){
        this.inputFileName = inputFileName;
        this.dateFormat = dateFormat;
        this.nDecimals = nDecimals;
        this.timeWindowMS = timeWindowMS;
        this.minThreshold = minThreshold;
        this.nThresholds = nThresholds;
        this.delimiter = delimiter;
        this.askIndex = askIndex;
        this.bidIndex = bidIndex;
        this.timeIndex = timeIndex;
        liquidityIndicator = new LiquidityIndicator(minThreshold, nThresholds, timeWindowMS);
    }

    /**
     * The method should be called in order to start calculations
     */
    public void go(){
        String dirName = "Results";
        Tools.CheckDirectory(dirName);
        try {
            String dateString = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date());
            String fileName = "liquidityIE_" + dateString + ".csv";
            PrintWriter writer = new PrintWriter(dirName + "/" + fileName, "UTF-8");
            writer.println("Time,Liquidity");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
            bufferedReader.readLine();
            String priceLine;

            long i = 0L;
            while ((priceLine = bufferedReader.readLine()) != null) {
                Price aPrice = Tools.priceLineToPrice(priceLine, delimiter, nDecimals, dateFormat, askIndex, bidIndex, timeIndex);
                double liquidity = liquidityIndicator.run(aPrice);
                if (liquidity != 0){
                    writer.println(aPrice.getTime() + "," + liquidity);
                }
                if (i % SHOW_EVERY == 0){
                    System.out.println(new Date(aPrice.getTime()) + ", liquidity: " + liquidity);
                }
                i++;
            }

            bufferedReader.close();
            writer.close();
            System.out.println("The file is saved as:   " + fileName);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


}
