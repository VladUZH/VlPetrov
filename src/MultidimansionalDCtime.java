import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by author.
 */
public class MultidimansionalDCtime {

    public static void main(String[] args){



//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /// This part of the code is used to debug the GBM class:
//        int numDim = 20;
//        int numSteps = 100;
//        GBM gbm = new GBM(numDim, 1.0, 0, 0.2, numSteps, 0, true);
////        GBM gbm = new GBM(new double[]{1.1, 2.2}, new double[]{0.0, 0.5}, new double[]{0.1, 0.6}, numSteps, 0, true);
//
//        for (int i = 0; i < numSteps; i++){
//            double[] generatedValues = gbm.generateNextRandom().clone();
//            String toPrint = "";
//            for (int j = 0; j < numDim; j++){
//                toPrint += generatedValues[j] + ", ";
//            }
//            System.out.println(toPrint);
//        }
//        //////////////////////////////////////////////////////////////////////////////////////////////////



//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /// Test of the getSingleShift method from GBM class
//        int numDim = 4;
//        int numSteps = 10;
//        GBM gbm = new GBM(numDim, 1.0, 0, 0.2, numSteps, 0, true);
////        GBM gbm = new GBM(new double[]{1.1, 2.2}, new double[]{0.0, 0.5}, new double[]{0.1, 0.6}, numSteps, 0, true);
//        for (int i = 0; i < numSteps; i++){
//            double[] generatedValues = gbm.getSingleShift().clone();
//            String toPrint = "";
//            for (int j = 0; j < numDim; j++){
//                toPrint += generatedValues[j] + ", ";
//            }
//            System.out.println(toPrint);
//        }
//        //////////////////////////////////////////////////////////////////////////////////////////////////



//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /// Here we check behaviour of runners in multidimensional case:
//        int numDim = 1; // for the Original version ("O") number of dimensions is 1
//        int numSteps = 10000;
//        GBM gbm = new GBM(numDim, 1.0, 0, 0.2, numSteps, 0, true);
//        RunnerM runnerM = new RunnerM(0.007, "S");
//        for (int i = 0; i < numSteps; i++){
//            double[] generatedValues = gbm.generateNextRandom().clone();
//            ATickM aTickM = new ATickM(generatedValues, generatedValues);
//            String toPrint = "";
//            for (int j = 0; j < numDim; j++){
//                toPrint += generatedValues[j] + ", ";
//            }
//            int ie = runnerM.run(aTickM);
//            if (ie != 0){
//                toPrint += ie + ", " + generatedValues[0] + ", " + runnerM.prevExtreme[0];
//            }
//            System.out.println(toPrint);
//        }
//        //////////////////////////////////////////////////////////////////////////////////////////////////



//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /////    Here we test three different versions creating an OS scaling law for a simple ABM time series
//        OSscalingLawM oSscalingLawM = new OSscalingLawM(0.0001, 0.05, 100, true, "S");
//
//        int numDim = 1;
//        int numSteps = 30000000;
//        ABM abm = new ABM(1.3367, 1/6769.6, numSteps, true);
//
//        for (int i = 0; i < numSteps; i++){
//            double generatedValues = abm.generateNextRandom();
//            ATickM aTickM = new ATickM(new double[]{generatedValues}, new double[]{generatedValues});
//            oSscalingLawM.run(aTickM);
//            if (i % 10000 == 0){
//                String toPrint = "";
//                for (int j = 0; j < numDim; j++){
//                    toPrint += generatedValues + ", ";
//                }
//                System.out.println(toPrint);
//            }
//        }
//
//        oSscalingLawM.finish();
//        oSscalingLawM.saveToFile();
//        //////////////////////////////////////////////////////////////////////////////////////////////////




//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /////    Here one can get the Overshoot Scaling law for one generation using GBM
//        OSscalingLawM oSscalingLawM = new OSscalingLawM(0.0001, 0.03, 100, true, "S");
//
//        int numDim = 1;
//        int numSteps = 200000000;
//        GBM gbm = new GBM(numDim, 1.0, 0, 0.2, numSteps, 0, true);
//
//        for (int i = 0; i < numSteps; i++){
//            double[] generatedValues = gbm.generateNextRandom().clone();
//            ATickM aTickM = new ATickM(generatedValues, generatedValues);
//            oSscalingLawM.run(aTickM);
//            if (i % 1000000 == 0){
//                String toPrint = "";
//                for (int j = 0; j < numDim; j++){
//                    toPrint += generatedValues[j] + ", ";
//                }
//                System.out.println(toPrint);
//            }
//        }
//
//        oSscalingLawM.finish();
//        oSscalingLawM.saveToFile();
//        //////////////////////////////////////////////////////////////////////////////////////////////////



//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /////    Overshoot Scaling law for one real time series:
//        String fileName = "D://Data/EURCHF_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        OSscalingLawM oSscalingLawM = new OSscalingLawM(0.0001, 0.04, 100, true, "S");
//
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
//            String line = bufferedReader.readLine(); // header
//
//            int i = 0;
//            while ((line = bufferedReader.readLine()) != null) {
//                ATickM aPrice = AdditionalTools.lineToPrice(line, 2, 1);
//                oSscalingLawM.run(aPrice);
//                if (i % 1000000 == 0){
//                    System.out.println(line);
//                }
//                i++;
//            }
//            oSscalingLawM.finish();
//            oSscalingLawM.saveToFile();
//
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        //////////////////////////////////////////////////////////////////////////////////////////////////



//        ////////////////////////////////////////////////////////////////////////////////////////////////
//        ///    Here we run the OS code for various dimensions and save the result in a file.
//        ///    All dimensions updated simultaneously.
//        ArrayList<String> columnNames = new ArrayList<String>();
//        columnNames.add("Delta");
//
//        ArrayList<double[]> columnsData = new ArrayList<>();
//        boolean firstRun = true;
//        for (int numDim = 1; numDim < 5; numDim++){
//            System.out.println("\n Number of dimensions is " + numDim + ":\n");
//
//            columnNames.add("Av_OS_move_" + numDim);
//            columnNames.add("NumEvents_" + numDim);
//
//            OSscalingLawM oSscalingLawM = new OSscalingLawM(0.0001, 0.04, 50, true, "S");
//
//            if (firstRun){
//                firstRun = false;
//                columnsData.add(oSscalingLawM.arrayOfdeltas);
//            }
//
//            int numSteps = 1000000;
//            GBM cbm = new GBM(numDim, 1.0, 0, 0.2, numSteps, 0, true);
//
//            for (int i = 0; i < numSteps; i++){
//                double[] generatedValues = cbm.generateNextRandom().clone();
//                ATickM aTickM = new ATickM(generatedValues, generatedValues);
//                oSscalingLawM.run(aTickM);
//                if (i % 1000000 == 0){
//                    String toPrint = "";
//                    for (int j = 0; j < numDim; j++){
//                        toPrint += generatedValues[j] + ", ";
//                    }
//                    System.out.println(toPrint);
//                }
//            }
//            oSscalingLawM.finish();
//            columnsData.add(oSscalingLawM.sumArray);
//            columnsData.add(Arrays.stream(oSscalingLawM.numEventsArray).asDoubleStream().toArray());
//        }
//
//        AdditionalTools.saveResultsToFile("averageOvershoots", columnNames, columnsData, true);
//        ////////////////////////////////////////////////////////////////////////////////////////////////



//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /////    Here we run the OS code for various dimensions and save the result in a file.
//        /////    All dimensions are updated consequently.
//        ArrayList<String> columnNames = new ArrayList<String>();
//        columnNames.add("Delta");
//
//        ArrayList<double[]> columnsData = new ArrayList<>();
//        boolean firstRun = true;
//        for (int numDim = 1; numDim < 10; numDim++){
//            System.out.println("\n Number of dimensions is " + numDim + ":\n");
//
//            columnNames.add("Av_OS_move_" + numDim);
//            columnNames.add("NumEvents_" + numDim);
//
//            OSscalingLawM oSscalingLawM = new OSscalingLawM(0.0001, 0.04, 100, true, "S");
//
//            if (firstRun){
//                firstRun = false;
//                columnsData.add(oSscalingLawM.arrayOfdeltas);
//            }
//
//            int numSteps = 1000000;
//            GBM cbm = new GBM(numDim, 1.0, 0, 0.2, numSteps, 0, true);
//
//            for (int i = 0; i < numSteps * numDim; i++){
//                double[] generatedValues = cbm.getSingleShift().clone();
//                ATickM aTickM = new ATickM(generatedValues, generatedValues);
//                oSscalingLawM.run(aTickM);
//                if (i % 10000 == 0){
//                    String toPrint = "";
//                    for (int j = 0; j < numDim; j++){
//                        toPrint += generatedValues[j] + ", ";
//                    }
//                    System.out.println(toPrint);
//                }
//            }
//            oSscalingLawM.finish();
//            columnsData.add(oSscalingLawM.sumArray);
//            columnsData.add(Arrays.stream(oSscalingLawM.numEventsArray).asDoubleStream().toArray());
//        }
//
//        AdditionalTools.saveResultsToFile("averageOvershoots", columnNames, columnsData, true);
//        //////////////////////////////////////////////////////////////////////////////////////////////////




//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /////    OS scaling law for 2D GBM with various correlations. All dimensions updated simultaneously:
//        ArrayList<String> columnNames = new ArrayList<String>();
//        columnNames.add("Delta");
//
//        ArrayList<double[]> columnsData = new ArrayList<>();
//        int numDim = 2;
//        double corrFrom = -1;
//        double corrTo = 1;
//        int numIntervals = 10;
//        boolean firstRun = true;
//        for (int interval = 0; interval <= numIntervals; interval++){
//
//            double corr = corrFrom + (corrTo - corrFrom) / numIntervals * interval;
//            corr = Math.round(corr * 100.0) / 100.0;
//
//            System.out.println("\n Correlation " + corr + ":\n");
//
//            columnNames.add("Av_OS_move_" + corr);
//            columnNames.add("NumEvents_" + corr);
//
//            OSscalingLawM oSscalingLawM = new OSscalingLawM(0.0001, 0.04, 100, true, "S");
//
//            if (firstRun){
//                firstRun = false;
//                columnsData.add(oSscalingLawM.arrayOfdeltas);
//            }
//
//            int numSteps = 10000000;
//            GBM cbm = new GBM(numDim, 1.0, 0, 0.2, numSteps, corr, true);
//
//            for (int i = 0; i < numSteps; i++){
//                double[] generatedValues = cbm.generateNextRandom().clone();
//                ATickM aTickM = new ATickM(generatedValues, generatedValues);
//                oSscalingLawM.run(aTickM);
//                if (i % 100000 == 0){
//                    String toPrint = "";
//                    for (int j = 0; j < numDim; j++){
//                        toPrint += generatedValues[j] + ", ";
//                    }
//                    System.out.println(toPrint);
//                }
//            }
//            oSscalingLawM.finish();
//            columnsData.add(oSscalingLawM.sumArray);
//            columnsData.add(Arrays.stream(oSscalingLawM.numEventsArray).asDoubleStream().toArray());
//        }
//
//        AdditionalTools.saveResultsToFile("averageOvershoots", columnNames, columnsData, true);
//        //////////////////////////////////////////////////////////////////////////////////////////////////



//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /////    OS scaling law for 2D GBM with various correlations.
//        /////    All dimensions are updated consequently:
//        ArrayList<String> columnNames = new ArrayList<String>();
//        columnNames.add("Delta");
//
//        ArrayList<double[]> columnsData = new ArrayList<>();
//        int numDim = 2;
//        double corrFrom = -1;
//        double corrTo = 1;
//        int numIntervals = 20;
//        boolean firstRun = true;
//        for (int interval = 0; interval <= numIntervals; interval++){
//
//            double corr = corrFrom + (corrTo - corrFrom) / numIntervals * interval;
//            corr = Math.round(corr * 100.0) / 100.0;
//
//            System.out.println("\n Correlation " + corr + ":\n");
//
//            columnNames.add("Av_OS_move_" + corr);
//            columnNames.add("NumEvents_" + corr);
//
//            OSscalingLawM oSscalingLawM = new OSscalingLawM(0.0001, 0.04, 100, true, "S");
//
//            if (firstRun){
//                firstRun = false;
//                columnsData.add(oSscalingLawM.arrayOfdeltas);
//            }
//
//            int numSteps = 100000;
//            GBM cbm = new GBM(numDim, 1.0, 0, 0.2, numSteps, corr, true);
//
//            for (int i = 0; i < numSteps * numDim; i++){
//                double[] generatedValues = cbm.getSingleShift().clone();
//                ATickM aTickM = new ATickM(generatedValues, generatedValues);
//                oSscalingLawM.run(aTickM);
//                if (i % 10000 == 0){
//                    String toPrint = "";
//                    for (int j = 0; j < numDim; j++){
//                        toPrint += generatedValues[j] + ", ";
//                    }
//                    System.out.println(toPrint);
//                }
//            }
//            oSscalingLawM.finish();
//            columnsData.add(oSscalingLawM.sumArray);
//            columnsData.add(Arrays.stream(oSscalingLawM.numEventsArray).asDoubleStream().toArray());
//        }
//
//        AdditionalTools.saveResultsToFile("averageOvershoots", columnNames, columnsData, true);
//        //////////////////////////////////////////////////////////////////////////////////////////////////




//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /// Testing TimeDataManager on real data and saving results:
//        String FILE_PATH = "D:/Data/";
//        int numDim = 2;
//        String[] fileNames = new String[numDim];
////        fileNames[0] = "EURAUD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        fileNames[0] = "EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
////        fileNames[2] = "EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        fileNames[1] = "EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
////        fileNames[4] = "EURCHF_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        TimeDataManager timeDataManager = new TimeDataManager(fileNames, FILE_PATH, ",");
//        try{
//            PrintWriter writer = new PrintWriter("Results/2D_EUR_JPY_JBP.csv", "UTF-8");
//            ATickM aTickM = timeDataManager.nextPrice();
//            String oldString = "";
//            while (aTickM != null){
//                String toFile = "";
//                for (double aMid : aTickM.getMid()){
//                    toFile += Math.round(aMid * 100000.0) / 100000.0 + ",";
//                }
//                if (!toFile.equals(oldString)){
//                    writer.println(toFile);
//                    oldString = toFile;
//                }
//                aTickM = timeDataManager.nextPrice();
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//        //////////////////////////////////////////////////////////////////////////////////////////////////



//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /// All overshoots of chosen thresholds saved in a file. MultiD case. Real data.
//
//        double deltaFrom = 0.0001;
//        double deltaTo = 0.01;
//        int nSteps = 3;
//        int numFiles = 23;
//        String[] fileNames = new String[numFiles];
//
////        String FILE_PATH = "D:/Data/";
////        fileNames[0] = "EURAUD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
////        fileNames[1] = "EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
////        fileNames[2] = "EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
////        fileNames[3] = "EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
////        fileNames[4] = "EURCHF_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//
//        String FILE_PATH = "E:/Data/Forex/2012_2017/";
//        String[] ccyList = {"AUDJPY", "AUDNZD", "AUDUSD", "CADJPY", "CHFJPY", "EURAUD", "EURCAD", "EURCHF",
//        "EURGBP", "EURJPY", "EURNZD", "EURUSD", "GBPAUD", "GBPCAD", "GBPCHF", "GBPJPY", "GBPUSD", "NZDCAD",
//        "NZDJPY", "NZDUSD", "USDCAD", "USDCHF", "USDJPY"};
//
//        for (int i = 0; i < ccyList.length; i++){
//            fileNames[i] = ccyList[i] + "_UTC_Ticks_Bid_2012.07.20_2017.07.21.csv";
//        }
//
//        TimeDataManager timeDataManager = new TimeDataManager(fileNames, FILE_PATH, ",", true);
//        OSscalingLawM oSscalingLawM = new OSscalingLawM(deltaFrom, deltaTo, nSteps, true, "S");
//        oSscalingLawM.turnAllToFileOn(); // to save everything in a file
//
//        ATickM aTickM = timeDataManager.nextPrice();
//        long counter = 0;
//        while (aTickM != null){
//            oSscalingLawM.run(aTickM);
//            aTickM = timeDataManager.nextPrice();
//            if (counter % 100000 == 0){
//                System.out.println(new Date(aTickM.getTime()));
//            }
//            counter++;
//        }
//        oSscalingLawM.finish();
//
//        //////////////////////////////////////////////////////////////////////////////////////////////////



//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /// All overshoots of chosen thresholds saved in a file. Like previous but only prepared data of
//        /// significant events. MultiD case. Real data.
//
////        String FILE_PATH = "D:/Data/";
////        int numDim = 1;
////        String[] fileNames = new String[numDim];
////        fileNames[0] = "EURAUD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
////        fileNames[1] = "EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
////        fileNames[0] = "EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
////        fileNames[3] = "EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
////        fileNames[4] = "EURCHF_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        double deltaFrom = 0.0001;
//        double deltaTo = 0.01;
//        int nSteps = 3;
//
////        String FILE_PATH = "D:/Data/EURCHF_drop/";
//        String FILE_PATH = "D:/Data/Brexit/";
//////        All rates list:
////        String[] ccyList = {"AUDJPY", "AUDNZD", "AUDUSD", "CADJPY", "CHFJPY", "EURAUD", "EURCAD", "EURCHF",
////                "EURGBP", "EURJPY", "EURNZD", "EURUSD", "GBPAUD", "GBPCAD", "GBPCHF", "GBPJPY", "GBPUSD", "NZDCAD",
////                "NZDJPY", "NZDUSD", "USDCAD", "USDCHF", "USDJPY"};
//////        No CHF list:
////        String[] ccyList = {"AUDJPY", "AUDNZD", "AUDUSD", "CADJPY",  "EURAUD", "EURCAD",
////                "EURGBP", "EURJPY", "EURNZD", "EURUSD", "GBPAUD", "GBPCAD", "GBPJPY", "GBPUSD", "NZDCAD",
////                "NZDJPY", "NZDUSD", "USDCAD", "USDJPY"};
//////        No CHF, no EUR list:
////        String[] ccyList = {"AUDJPY", "AUDNZD", "AUDUSD", "CADJPY", "GBPAUD", "GBPCAD", "GBPJPY", "GBPUSD", "NZDCAD",
////                "NZDJPY", "NZDUSD", "USDCAD", "USDJPY"};
////        No GBP list:
////        String[] ccyList = {"AUDJPY", "AUDNZD", "AUDUSD", "CADJPY", "CHFJPY", "EURAUD", "EURCAD", "EURCHF", "EURJPY",
////                "EURNZD", "EURUSD", "NZDCAD", "NZDJPY", "NZDUSD", "USDCAD", "USDCHF", "USDJPY"};
//////            No GBP, no EUR list:
////        String[] ccyList = {"AUDJPY", "AUDNZD", "AUDUSD", "CADJPY", "CHFJPY", "NZDCAD", "NZDJPY", "NZDUSD", "USDCAD", "USDCHF", "USDJPY"};
////        No GBP, no EUR, no JPY list:
//        String[] ccyList = {"AUDNZD", "AUDUSD", "NZDCAD", "NZDUSD", "USDCAD", "USDCHF"};
//        String[] fileNames = new String[ccyList.length];
//        for (int i = 0; i < ccyList.length; i++){
////            fileNames[i] = ccyList[i] + "_2014.12.15_2015.02.15.csv";
//            fileNames[i] = ccyList[i] + "_2016.05.23_2016.07.23.csv";
//        }
//
//        TimeDataManager timeDataManager = new TimeDataManager(fileNames, FILE_PATH, ",", true);
//
//        OSscalingLawM oSscalingLawM = new OSscalingLawM(deltaFrom, deltaTo, nSteps, true, "S");
//        oSscalingLawM.turnAllToFileOn(); // to save everything in a file
//
//        ATickM aTickM = timeDataManager.nextPrice();
//        long counter = 0;
//        while (aTickM != null){
//            oSscalingLawM.run(aTickM);
//            aTickM = timeDataManager.nextPrice();
//            if (counter % 100000 == 0){
//                System.out.println(new Date(aTickM.getTime()));
//            }
//            counter++;
//        }
//        oSscalingLawM.finish();
//
//        //////////////////////////////////////////////////////////////////////////////////////////////////



//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /// All overshoots of chosen thresholds saved in a file. MultiD case. GBM.
//        double deltaFrom = 0.001;
//        double deltaTo = 0.001;
//        int nSteps = 1;
//        int numDim = 2;
//        int numSteps = 100000000;
//        GBM gbm = new GBM(numDim, 1.0, 0, 0.2, numSteps, 0.5, true);
//
//        OSscalingLawM oSscalingLawM = new OSscalingLawM(deltaFrom, deltaTo, nSteps, true, "S");
//        oSscalingLawM.turnAllToFileOn(); // to save everything in a file
//
//        double[] nextRandom = gbm.generateNextRandom();
//        ATickM aTickM = new ATickM(nextRandom, nextRandom);
//
//        long counter = 0;
//        while (counter < numSteps){
//            oSscalingLawM.run(aTickM);
//            nextRandom = gbm.generateNextRandom();
//            aTickM = new ATickM(nextRandom, nextRandom);
//            if (counter % 1000000 == 0){
//                System.out.println(counter + " out of " + numSteps + ", " + (double)counter/numSteps * 100.0 + "% done");
//            }
//            counter++;
//        }
//        oSscalingLawM.finish();
//        //////////////////////////////////////////////////////////////////////////////////////////////////




//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /////    OS scaling law for 2D real prices:
//        String FILE_PATH = "D:/Data/";
//        int numFiles = 5;
//        String[] fileNames = new String[numFiles];
//        fileNames[0] = "EURAUD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        fileNames[1] = "EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        fileNames[2] = "EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        fileNames[3] = "EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        fileNames[4] = "EURCHF_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//
//        ArrayList<String> columnNames = new ArrayList<String>();
//        columnNames.add("Delta");
//
//        ArrayList<double[]> columnsData = new ArrayList<>();
//
//        boolean firstRun = true;
//        for (int firstInd = 0; firstInd < numFiles - 1; firstInd++){
//            for (int secondInd = firstInd + 1; secondInd < numFiles; secondInd++){
//
//                System.out.println("\n Beginning of " + fileNames[firstInd].substring(0, 6) + "_" + fileNames[secondInd].substring(0, 6));
//
//                TimeDataManager timeDataManager = new TimeDataManager(new String[]{fileNames[firstInd], fileNames[secondInd]}, FILE_PATH, ",", true);
//
//                columnNames.add("Av_OS_move_" + fileNames[firstInd].substring(0, 6) + "_" + fileNames[secondInd].substring(0, 6));
//                columnNames.add("NumEvents_" + fileNames[firstInd].substring(0, 6) + "_" + fileNames[secondInd].substring(0, 6));
//
//                OSscalingLawM oSscalingLawM = new OSscalingLawM(0.0001, 0.04, 100, true, "S");
//
//                if (firstRun){
//                    firstRun = false;
//                    columnsData.add(oSscalingLawM.arrayOfdeltas);
//                }
//
//                ATickM aTickM;
//                int i = 0;
//                while ((aTickM = timeDataManager.nextPrice()) != null){
//                    oSscalingLawM.run(aTickM);
//                    if (i % 1000000 == 0){
//                        System.out.println(new Date(aTickM.getTime()));
//                    }
//                    i++;
//                }
//
//                oSscalingLawM.finish();
//                columnsData.add(oSscalingLawM.sumArray);
//                columnsData.add(Arrays.stream(oSscalingLawM.numEventsArray).asDoubleStream().toArray());
//
//                System.out.println("End of " + fileNames[firstInd].substring(0, 6) + "_" + fileNames[secondInd].substring(0, 6) + "\n");
//            }
//        }
//        AdditionalTools.saveResultsToFile("averageOvershoots", columnNames, columnsData, true);
//        //////////////////////////////////////////////////////////////////////////////////////////////////



////////////////////////////////////////////////////////////////////////////////////////////////////
//        /////    OS scaling law for multidimensional real prices:
//        String FILE_PATH = "D:/Data/";
////        String FILE_PATH = "E:/Data/Forex/2012_2017/";
//        int numFiles = 5;
//        String[] fileNames = new String[numFiles];
//        fileNames[0] = "EURAUD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        fileNames[1] = "EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        fileNames[2] = "EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        fileNames[3] = "EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        fileNames[4] = "EURCHF_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//
////        String[] ccyList = {"AUDJPY", "AUDNZD", "AUDUSD", "CADJPY", "CHFJPY", "EURAUD", "EURCAD", "EURCHF",
////        "EURGBP", "EURJPY", "EURNZD", "EURUSD", "GBPAUD", "GBPCAD", "GBPCHF", "GBPJPY", "GBPUSD", "NZDCAD",
////        "NZDJPY", "NZDUSD", "USDCAD", "USDCHF", "USDJPY"};
////
////
////        for (int i = 0; i < ccyList.length; i++){
////            fileNames[i] = ccyList[i] + "_UTC_Ticks_Bid_2012.07.20_2017.07.21.csv";
////        }
//
//        ArrayList<String> columnNames = new ArrayList<String>();
//        columnNames.add("Delta");
//
//        ArrayList<double[]> columnsData = new ArrayList<>();
//
//        TimeDataManager timeDataManager = new TimeDataManager(fileNames, FILE_PATH, ",", true);
//
//        columnNames.add("Av_OS_move_" + fileNames[0].substring(0, 3));
//        columnNames.add("NumEvents_" + fileNames[0].substring(0, 3));
//
//        OSscalingLawM oSscalingLawM = new OSscalingLawM(0.0001, 0.04, 100, true, "S");
//
//        columnsData.add(oSscalingLawM.arrayOfdeltas);
//
//        ATickM aTickM;
//        int i = 0;
//        while ((aTickM = timeDataManager.nextPrice()) != null){
//            oSscalingLawM.run(aTickM);
//            if (i % 1000000 == 0){
//                System.out.println(new Date(aTickM.getTime()));
//            }
//            i++;
//        }
//
//        oSscalingLawM.finish();
//        columnsData.add(oSscalingLawM.sumArray);
//        columnsData.add(Arrays.stream(oSscalingLawM.numEventsArray).asDoubleStream().toArray());
//
//        AdditionalTools.saveResultsToFile("averageOvershoots", columnNames, columnsData, true);
//        //////////////////////////////////////////////////////////////////////////////////////////////////



////        //////////////////////////////////////////////////////////////////////////////////////////////////
////        /////    OS scaling law for 1D GBM with various fixed autocorrelation:
//        ArrayList<String> columnNames = new ArrayList<String>();
//        columnNames.add("Delta");
//        ArrayList<double[]> columnsData = new ArrayList<>();
//
//        double acorr = 1.0;
//        System.out.println("\n Autocorrelation " + acorr + ":\n");
//
//        columnNames.add("Av_OS_move");
//        columnNames.add("NumEvents");
//        OSscalingLawM oSscalingLawM = new OSscalingLawM(0.00001, 0.01, 100, true, "S");
//        columnsData.add(oSscalingLawM.arrayOfdeltas);
//
//        int numSteps = 1000000;
//        AcorrGBM acorrGBM = new AcorrGBM(1.0, 0, 0.2, numSteps, 10, acorr, true);
//
//        for (int i = 0; i < numSteps; i++){
//            double generatedValue = acorrGBM.generateNextRandom();
//            double[] arrayValue = new double[]{generatedValue};
//            ATickM aTickM = new ATickM(arrayValue, arrayValue);
//            oSscalingLawM.run(aTickM);
//            if (i % 10000 == 0){
//                System.out.println(generatedValue);
//            }
//        }
//        oSscalingLawM.finish();
//        columnsData.add(oSscalingLawM.sumArray);
//        columnsData.add(Arrays.stream(oSscalingLawM.numEventsArray).asDoubleStream().toArray());
//        AdditionalTools.saveResultsToFile("averageOvershoots", columnNames, columnsData, true);
////        //////////////////////////////////////////////////////////////////////////////////////////////////



////        //////////////////////////////////////////////////////////////////////////////////////////////////
////        /////    OS scaling law, 1D GBM, 1 step autocorelation made of Brownian Bridge, external file:
//        ArrayList<String> columnNames = new ArrayList<String>();
//        columnNames.add("Delta");
//        ArrayList<double[]> columnsData = new ArrayList<>();
//
//        String fileName = "C:/Users/Vladimir Petrov/Documents/iPython/TESTS/BridgeAntiCF.csv";
//
//        columnNames.add("Av_OS_move");
//        columnNames.add("NumEvents");
//        OSscalingLawM oSscalingLawM = new OSscalingLawM(0.0001, 0.05, 100, true, "S");
//        columnsData.add(oSscalingLawM.arrayOfdeltas);
//
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
//            String line;
//            int i = 0;
//            while ((line = bufferedReader.readLine()) != null) {
//                ATickM aPrice = AdditionalTools.lineToPrice(line, 0, 0);
//                oSscalingLawM.run(aPrice);
//                if (i % 10000 == 0) {
//                    System.out.println(line);
//                }
//                i++;
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//        oSscalingLawM.finish();
//        columnsData.add(oSscalingLawM.sumArray);
//        columnsData.add(Arrays.stream(oSscalingLawM.numEventsArray).asDoubleStream().toArray());
//        AdditionalTools.saveResultsToFile("averageOvershoots", columnNames, columnsData, true);
////        //////////////////////////////////////////////////////////////////////////////////////////////////



//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /////    OS scaling law, 2D GBM, 1 time series with ACF, another without, external file:
//        ArrayList<String> columnNames = new ArrayList<String>();
//        columnNames.add("Delta");
//        ArrayList<double[]> columnsData = new ArrayList<>();
//
//        String fileName = "C:/Users/Vladimir Petrov/Documents/iPython/TESTS/2D_Brow_with_acor.csv";
//
//        columnNames.add("Av_OS_move");
//        columnNames.add("NumEvents");
//        OSscalingLawM oSscalingLawM = new OSscalingLawM(0.0001, 0.05, 100, true, "S");
//        columnsData.add(oSscalingLawM.arrayOfdeltas);
//
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
//            String line;
//            int i = 0;
//            while ((line = bufferedReader.readLine()) != null) {
//                String[] lineData = line.split(",");
//                double[] bids = new double[]{Double.parseDouble(lineData[0]), Double.parseDouble(lineData[1])};
//                ATickM aPrice = new ATickM(bids, bids);
//                oSscalingLawM.run(aPrice);
//                if (i % 10000 == 0) {
//                    System.out.println(line);
//                }
//                i++;
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//        oSscalingLawM.finish();
//        columnsData.add(oSscalingLawM.sumArray);
//        columnsData.add(Arrays.stream(oSscalingLawM.numEventsArray).asDoubleStream().toArray());
//        AdditionalTools.saveResultsToFile("averageOvershoots", columnNames, columnsData, true);
////        //////////////////////////////////////////////////////////////////////////////////////////////////




//        //////////////////////////////////////////////////////////////////////////////////////////////////
//        /////    States of the multidimensional market formalized in the modes of intrinsic events at
//        /////    different scales (states described in the "Multi-scale... liquidity" paper)
////        String FILE_PATH = "D:/Data/";
//        String FILE_PATH = "E:/Data/Forex/2012_2017/";
//        int numFiles = 23;
//        String[] fileNames = new String[numFiles];
//        String[] ccyList = {"AUDJPY", "AUDNZD", "AUDUSD", "CADJPY", "CHFJPY", "EURAUD", "EURCAD", "EURCHF",
//                "EURGBP", "EURJPY", "EURNZD", "EURUSD", "GBPAUD", "GBPCAD", "GBPCHF", "GBPJPY", "GBPUSD", "NZDCAD",
//                "NZDJPY", "NZDUSD", "USDCAD", "USDCHF", "USDJPY"};
//
//        for (int i = 0; i < ccyList.length; i++){
//            fileNames[i] = ccyList[i] + "_UTC_Ticks_Bid_2012.07.20_2017.07.21.csv";
//        }
//
//        TimeDataManager timeDataManager = new TimeDataManager(fileNames, FILE_PATH, ",", true);
//        MarketState marketState = new MarketState(0.00025, 0.5);
//
//        int numTransitonsToSave = 10000;
//        String[] statesList = new String[numTransitonsToSave];
//
//        ATickM aTickM;
//        int counter = 0;
////        while ((aTickM = timeDataManager.nextPrice()) != null){
//        while (counter < numTransitonsToSave){
//            aTickM = timeDataManager.nextPrice();
//            if (marketState.runTick(aTickM)){ // new transition happened
//                String toPrint = "";
//                for (short aState : marketState.currentState){
//                    toPrint += aState;
//                }
//                statesList[counter] = toPrint;
//                if (counter % 100 == 0){
//                    System.out.println(toPrint + ", " + new Date(aTickM.getTime()));
//                }
//                counter += 1;
//            }
//        }
//
//        ArrayList<String> namesList = new ArrayList<>();
//        namesList.add("States");
//        ArrayList<String[]> listForData = new ArrayList<>();
//        listForData.add(statesList);
//
//
//        AdditionalTools.saveResultsToFile("LiquidityIndicator", namesList, listForData);
////        //////////////////////////////////////////////////////////////////////////////////////////////////






    }


}
