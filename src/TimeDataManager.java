import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Vladimir Petrov on 06.09.2016.
 */
public class TimeDataManager {

    public boolean firstStep;
    public Date startDate;
    public double[][] prices;
    public String[] fileNames;
    public BufferedReader[] bufferedReaders;
    public String[][] newestInf;
    public String[][] usedInf;
    public ATickM aTickM;
    private int numDim;
    private String delimeter;
    private String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
    private ATickM firstTickM;
    private boolean normalize;
    private Date fromDate, toDate;
    private boolean specialInterval;


    TimeDataManager(String[] fileNames, String globalPath, String delimeter, boolean normalize){
        this.specialInterval = false;
        this.numDim = fileNames.length;
        this.fileNames = fileNames.clone();
        prices = new double[2][numDim];
        bufferedReaders = new BufferedReader[numDim];
        newestInf = new String[numDim][];
        usedInf = new String[numDim][];
        this.firstStep = true;
        this.delimeter = delimeter;
        this.normalize = normalize;

        Date[] firstDates = new Date[numDim];
        for (int i = 0; i < fileNames.length; i++){
            try {
                bufferedReaders[i] = new BufferedReader(new FileReader(globalPath + fileNames[i]));
                bufferedReaders[i].readLine(); // simply to read header only once
                String[] rawData = bufferedReaders[i].readLine().split(delimeter);
                firstDates[i] = AdditionalTools.stringToDate(rawData[0], dateFormat);
                usedInf[i] = rawData.clone(); // keep here the first data in the files
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        startDate = findLatestDate(firstDates);

    }


    TimeDataManager(String[] fileNames, String globalPath, String delimeter, boolean normalize, Date fromDate, Date toDate){
        this.specialInterval = true;
        this.numDim = fileNames.length;
        this.fileNames = fileNames.clone();
        prices = new double[2][numDim];
        bufferedReaders = new BufferedReader[numDim];
        newestInf = new String[numDim][];
        usedInf = new String[numDim][];
        this.firstStep = true;
        this.delimeter = delimeter;
        this.normalize = normalize;
        this.fromDate = fromDate;
        this.toDate = toDate;

        Date[] firstDates = new Date[numDim];
        for (int i = 0; i < fileNames.length; i++){
            try {
                bufferedReaders[i] = new BufferedReader(new FileReader(globalPath + fileNames[i]));
                bufferedReaders[i].readLine(); // simply to read header only once
                String[] rawData = bufferedReaders[i].readLine().split(delimeter);
                boolean foundDate = false;
                while (!foundDate){
                    rawData = bufferedReaders[i].readLine().split(delimeter);
                    Date currentDate = AdditionalTools.stringToDate(rawData[0], dateFormat);
                    if (currentDate.after(fromDate)){
                        foundDate = true;
                    }
                }
                firstDates[i] = AdditionalTools.stringToDate(rawData[0], dateFormat);
                usedInf[i] = rawData.clone(); // keep here the first data in the files
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        System.out.println("Found the initial date");
        startDate = findLatestDate(firstDates);

    }


    public ATickM nextPrice(){

        if (firstStep){ // here we are trying to find the date right before or at the moment of the startDate
            for (int i = 0; i < numDim; i++){
                try {
                    boolean notFoundLatest = true;
                    while (notFoundLatest){
                        newestInf[i] = bufferedReaders[i].readLine().split(delimeter);
                        if (AdditionalTools.stringToDate(newestInf[i][0], dateFormat).after(startDate)){
                            prices[0][i] = Double.parseDouble(usedInf[i][2]); // bid prices
                            prices[1][i] = Double.parseDouble(usedInf[i][1]); // ask prices
                            notFoundLatest = false;
                        } else {
                            usedInf[i] = newestInf[i].clone();
                        }
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            firstStep = false;
            aTickM = new ATickM(prices[0], prices[1], startDate.getTime());
            firstTickM = aTickM.clone();
            if (normalize){
                aTickM.normalize(firstTickM);
            }
            return aTickM;

        } else {
            LinkedList<Integer> lowestDateIndexes = new LinkedList<>(); // there could be updates of a price in several
            // dimensions simultaneously
            lowestDateIndexes.add(0); // we assume that the next tick happens in the dimension 0
//            int lowestDateIndex = 0;
//            String[] tempInf = newestInf[lowestDateIndex];
            Date lowestDate = AdditionalTools.stringToDate(newestInf[lowestDateIndexes.get(0)][0], dateFormat);
            for (int i = 1; i < numDim; i++){
                Date tempDate = AdditionalTools.stringToDate(newestInf[i][0], dateFormat);
                if (tempDate.before(lowestDate)){
                    lowestDate = tempDate;
                    lowestDateIndexes.clear(); // must remove all precious elements
                    lowestDateIndexes.add(i);
                } else if (tempDate.equals(lowestDate)){ // the case when another dimension changed simultaneously
                    lowestDateIndexes.add(i);
                }
            }

            for (int changedIndex : lowestDateIndexes){
                usedInf[changedIndex] = newestInf[changedIndex].clone();
                prices[0][changedIndex] = Double.parseDouble(usedInf[changedIndex][2]); // updated bid price
                prices[1][changedIndex] = Double.parseDouble(usedInf[changedIndex][1]); // updated ask price
                try { // now we are trying to read a new piece of information for the data we just updated:
                    String newLine;
                    if ((newLine = bufferedReaders[changedIndex].readLine()) != null){
                        newestInf[changedIndex] = newLine.split(delimeter);
                    } else { // there could be nothing left in the input file, so just stop working:
                        return (null);
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            aTickM = new ATickM(prices[0], prices[1], lowestDate.getTime());
            if (normalize){
                aTickM.normalize(firstTickM);
            }
            if (specialInterval){ // should not produce any ticks after the selected interval
                if (aTickM.getTime() > toDate.getTime()){
                    System.out.println("Reached the last date");
                    return (null);
                }
            }
            return aTickM;
        }


    }


    /**
     * Finds the latest date out of an array of dates.
     */
    private Date findLatestDate(Date[] dates){
        Date latestDate = dates[0];
        for (int i = 1; i < dates.length; i++){
            if (dates[i].after(latestDate)){
                latestDate = dates[i];
            }
        }
        return latestDate;
    }


}
