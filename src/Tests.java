import ievents.InstantaneousVolatility;
import ievents.RealizedVolatility;
import ievents.RealizedVolatilitySeasonality;
import market.Price;
import scalinglaws.*;
import scalinglaws.MaxPriceMoveScalingLaw;
import tools.GBM;
import tools.ThetaTime;
import tools.Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by author.
 *
 * Here I test all my classes and functions.
 *
 */
public class Tests {

    public Tests(){}

    public void run(){
        /**
         * Test GBM:
         */
//        GBM gbm = new GBM(100, 0.2f, 1, 10000, 0);
//        for (int i = 0; i < 10000; i++){
//            System.out.println(gbm.generateNextValue());
//        }
//        // works well.

        /**
         * Test DcOs:
         */
//        double deltaSize = 0.01;
//        DcOS dcOS = new DcOS(deltaSize, deltaSize, 1, deltaSize, deltaSize, true);
//        GBM gbm = new GBM(100, 0.05f, 4, 1000, 0);
//        for (int i = 0; i < 10000; i++){
//            long generatedValue = (long)(gbm.generateNextValue() * 10000);
//            Price price = new Price(generatedValue, generatedValue, i, 4);
//            if (dcOS.run(price) != 0){
//                System.out.println("PrevExtreme: " + dcOS.getPrevExtreme() / Math.pow(10, price.getnDecimals()) + "; IE:" +
//                        " " + price.getMid() / Math.pow(10, price.getnDecimals()) + "." +
//                        " Mode: " + dcOS.getMode());
//                System.out.println("OS size: " + dcOS.getOsL());
//                System.out.println("OS variability: " + dcOS.computeSqrtOsDeviation());
//                System.out.println("Time PrevOS: " + dcOS.gettPrevOS() + ", Time PrevDC: " + dcOS.gettPrevDcIE() +
//                        ", Time OS: " + dcOS.gettOS() + ", Time DC: " + dcOS.gettDcIE() + ", Time Extreme: " + dcOS.gettExtreme() +
//                        ", Time OsIE: " + dcOS.gettOsIE());
//            }
//        }
//        // works well

        /**
         * Test OSmoveScalingLaw on GBM:
         */
//        int numDeltas = 250;
//        OSmoveScalingLaw oSmoveScalingLaw = new OSmoveScalingLaw(0.0001f, 0.05f, numDeltas);
//        int numGenerations = 1000000;
//        GBM gbm = new GBM(1.336723f, 0.15f, 1.0f, numGenerations, 0);
//        for (int i = 0; i < numGenerations; i++){
//            long value = (long) (gbm.generateNextValue() * 10000);
//            Price aPrice = new Price(value, value, 0,  0);
//            oSmoveScalingLaw.run(aPrice);
//        }
//        oSmoveScalingLaw.finish();
//        System.out.println("Delta + OS moves: ");
//        for (int i = 0; i < numDeltas; i++){
//            System.out.println(oSmoveScalingLaw.getArrayDeltas()[i] + " , " + oSmoveScalingLaw.getOsMoves()[i]);
//        }
//        oSmoveScalingLaw.saveResults("Results");
//        double[] params = oSmoveScalingLaw.computeParams();
//        System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        // works well.


        /**
         * Test OSmoveScalingLaw on real data:
         */
//        int numDeltas = 250;
//        OSmoveScalingLaw oSmoveScalingLaw = new OSmoveScalingLaw(0.0001f, 0.05f, numDeltas);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", 3, dateFormat, 1, 2, 0);
//                oSmoveScalingLaw.run(price);
//            }
//            oSmoveScalingLaw.finish();
//            System.out.println("Delta + OS moves: ");
//            for (int i = 0; i < numDeltas; i++){
//                System.out.println(oSmoveScalingLaw.getArrayDeltas()[i] + " , " + oSmoveScalingLaw.getOsMoves()[i]);
//            }
//            oSmoveScalingLaw.saveResults("Results");
//            double[] params = oSmoveScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.

        /**
         * Test TimeTotMoveScalLaw on GBM:
         */
//        int numDeltas = 250;
//        TimeTotMoveScalLaw timeTotMoveScalLaw = new TimeTotMoveScalLaw(0.0001f, 0.05f, numDeltas);
//        int numGenerations = 1000000;
//        GBM gbm = new GBM(1.336723f, 0.15f, 1.0f, numGenerations, 0);
//        for (int i = 0; i < numGenerations; i++){
//            long value = (long) (gbm.generateNextValue() * 10000);
//            Price aPrice = new Price(value, value, i,  0);
//            timeTotMoveScalLaw.run(aPrice);
//        }
//        timeTotMoveScalLaw.finish();
//        System.out.println("Delta + OS moves: ");
//        for (int i = 0; i < numDeltas; i++){
//            System.out.println(timeTotMoveScalLaw.getArrayDeltas()[i] + " , " + timeTotMoveScalLaw.getOsMoves()[i]);
//        }
//        timeTotMoveScalLaw.saveResults("Results");
//        double[] params = timeTotMoveScalLaw.computeParams();
//        System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        // works well.


        /**
         * Test TimeTotMoveScalLaw on real data:
         */
//        int numDeltas = 250;
//        TimeTotMoveScalLaw timeTotMoveScalLaw = new TimeTotMoveScalLaw(0.0001f, 0.05f, numDeltas);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 3;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                timeTotMoveScalLaw.run(price);
//            }
//            timeTotMoveScalLaw.finish();
//            System.out.println("Delta + OS moves: ");
//            for (int i = 0; i < numDeltas; i++){
//                System.out.println(timeTotMoveScalLaw.getArrayDeltas()[i] + " , " + timeTotMoveScalLaw.getOsMoves()[i]);
//            }
//            timeTotMoveScalLaw.saveResults("Results");
//            double[] params = timeTotMoveScalLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


        /**
         * Test MeanPriceMoveScalingLaw on GBM:
         */
//        int numSteps = 50;
//        MeanPriceMoveScalingLaw meanPriceMoveScalingLaw = new MeanPriceMoveScalingLaw(10, 1000000, numSteps);
//        double nYears = 1.0;
//        long nGenrations = (long) (nYears * 60 * 60 * 24 * 365L); // one tick each second
//        GBM gbm = new GBM(1.336723f, 0.15f, 1.0f, nGenrations, 0);
//        for (int i = 0; i < nGenrations; i++){
//            long value = (long) (gbm.generateNextValue() * 10000);
//            Price aPrice = new Price(value, value, i * 1000,  0);
//            meanPriceMoveScalingLaw.run(aPrice);
//        }
//        meanPriceMoveScalingLaw.finish();
//        System.out.println("DeltaT + MeanPriceMove: ");
//        for (int i = 0; i < numSteps; i++){
//            System.out.println(meanPriceMoveScalingLaw.getArrayDeltasT()[i] + " , "
//                    + meanPriceMoveScalingLaw.getMeanPriceMoves()[i]);
//        }
//        meanPriceMoveScalingLaw.saveResults("Results");
//        double[] params = meanPriceMoveScalingLaw.computeParams();
//        System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        // works well.


        /**
         * Test MeanPriceMoveScalingLaw on real data:
         */
//        int numSteps = 50;
//        MeanPriceMoveScalingLaw meanPriceMoveScalingLaw = new MeanPriceMoveScalingLaw(10, 1000000, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                meanPriceMoveScalingLaw.run(price);
//            }
//            meanPriceMoveScalingLaw.finish();
//            System.out.println("DeltaT + MeanPriceMove: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(meanPriceMoveScalingLaw.getArrayDeltasT()[i] + " , "
//                        + meanPriceMoveScalingLaw.getMeanPriceMoves()[i]);
//            }
//            meanPriceMoveScalingLaw.saveResults("Results");
//            double[] params = meanPriceMoveScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


        /**
         * Test QuadraticMeanPriceMoveScalingLaw on GBM:
         */
//        int numSteps = 50;
//        QuadraticMeanPriceMoveScalingLaw quadraticMeanPriceMoveScalingLaw = new QuadraticMeanPriceMoveScalingLaw(10, 1000000, numSteps);
//        double nYears = 1.0;
//        long nGenrations = (long) (nYears * 60 * 60 * 24 * 365L); // one tick each second
//        GBM gbm = new GBM(1.336723f, 0.15f, 1.0f, nGenrations, 0);
//        for (int i = 0; i < nGenrations; i++){
//            long value = (long) (gbm.generateNextValue() * 10000);
//            Price aPrice = new Price(value, value, i * 1000,  0);
//            quadraticMeanPriceMoveScalingLaw.run(aPrice);
//        }
//        quadraticMeanPriceMoveScalingLaw.finish();
//        System.out.println("DeltaT + QuadraticMeanPriceMove: ");
//        for (int i = 0; i < numSteps; i++){
//            System.out.println(quadraticMeanPriceMoveScalingLaw.getArrayDeltasT()[i] + " , "
//                    + quadraticMeanPriceMoveScalingLaw.getQuadMeanPriceMoves()[i]);
//        }
//        quadraticMeanPriceMoveScalingLaw.saveResults("Results");
//        double[] params = quadraticMeanPriceMoveScalingLaw.computeParams();
//        System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        // works well.



        /**
         * Test QuadraticMeanPriceMoveScalingLaw on real data:
         */
//        int numSteps = 50;
//        QuadraticMeanPriceMoveScalingLaw quadraticMeanPriceMoveScalingLaw = new QuadraticMeanPriceMoveScalingLaw(10, 1000000, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                quadraticMeanPriceMoveScalingLaw.run(price);
//            }
//            quadraticMeanPriceMoveScalingLaw.finish();
//            System.out.println("DeltaT + QuadraticMeanPriceMove: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(quadraticMeanPriceMoveScalingLaw.getArrayDeltasT()[i] + " , "
//                        + quadraticMeanPriceMoveScalingLaw.getQuadMeanPriceMoves()[i]);
//            }
//            quadraticMeanPriceMoveScalingLaw.saveResults("Results");
//            double[] params = quadraticMeanPriceMoveScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


        /**
         * Test CheckDirectory:
         */
//        Tools.CheckDirectory("Results");
//        // works well.

        /**
         * Test GenerateLogSpace:
         */
//        int nBins = 3;
//        float[] generatedArray = Tools.GenerateLogSpace(0.01f, 0.2f, nBins);
//        for (float value : generatedArray){
//            System.out.println(value);
//        }
//        // works well.

//        /**
//         * Test DCcountScalingLaw on GBM:
//         */
//        int numDeltas = 10;
//        DCcountScalingLaw dCcountScalingLaw = new DCcountScalingLaw(0.0001f, 0.05f, numDeltas);
//        int numGenerations = 20000000;
//        GBM gbm = new GBM(1.336723f, 0.1f, 0.5f, numGenerations, 0);
//        for (int i = 0; i < numGenerations; i++){
//            long value = (long) (gbm.generateNextValue() * 10000);
//            Price aPrice = new Price(value, value, 0,  0);
//            dCcountScalingLaw.run(aPrice);
//        }
//        System.out.println("Pure Delta + Num DCs:");
//        for (int i = 0; i < numDeltas; i++){
//            System.out.println(dCcountScalingLaw.getArrayDeltas()[i] + " , " + dCcountScalingLaw.getNumDCs()[i]);
//        }
//        dCcountScalingLaw.finish();
//        System.out.println("Normalized Delta + Num DCs:");
//        for (int i = 0; i < numDeltas; i++){
//            System.out.println(dCcountScalingLaw.getArrayDeltas()[i] + " , " + dCcountScalingLaw.getNumDCs()[i]);
//        }
//        dCcountScalingLaw.saveResults("Results");
//        double[] params = dCcountScalingLaw.computeParams();
//        System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        System.out.println("The best threshold for 10min and 1 IE: " + dCcountScalingLaw.findBestThreshold(600000L, 1));
//        // works well.

        /**
         * Test DCcountScalingLaw on real data:
         */
//        int numDeltas = 50;
//        DCcountScalingLaw dCcountScalingLaw = new DCcountScalingLaw(0.0001f, 0.05f, numDeltas);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2014-01-01_2015-01-01.csv";
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", 5, dateFormat, 1, 2, 0);
//                dCcountScalingLaw.run(price);
//            }
//            dCcountScalingLaw.finish();
//            System.out.println("Pure Delta + Num DCs:");
//            for (int i = 0; i < numDeltas; i++){
//                System.out.println(dCcountScalingLaw.getArrayDeltas()[i] + " , " + dCcountScalingLaw.getNumDCs()[i]);
//            }
//            double[] params = dCcountScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//            System.out.println("The best threshold for 10min and 1 IE: " + dCcountScalingLaw.findBestThreshold(600000L, 1));
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well

        /**
         * Test PriceMoveCountScalingLaw on GBM:
         */
//        int numDeltas = 10;
//        PriceMoveCountScalingLaw priceMoveCountScalingLaw = new PriceMoveCountScalingLaw(0.0001f, 0.05f, numDeltas);
//        int numGenerations = 1000000;
//        GBM gbm = new GBM(1.336723f, 0.2f, 1.0f, numGenerations, 0);
//        for (int i = 0; i < numGenerations; i++){
//            long value = (long) (gbm.generateNextValue() * 10000);
//            Price aPrice = new Price(value, value, i * 1000,  0);
//            priceMoveCountScalingLaw.run(aPrice);
//        }
//        priceMoveCountScalingLaw.finish();
//        System.out.println("Delta + Num DCs:");
//        for (int i = 0; i < numDeltas; i++){
//            System.out.println(priceMoveCountScalingLaw.getArrayDeltas()[i] + " , " + priceMoveCountScalingLaw.getNumPriceMoves()[i]);
//        }
//        priceMoveCountScalingLaw.saveResults("Results");
//        double[] params = priceMoveCountScalingLaw.computeParams();
//        System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        // works well.


        /**
         * Test PriceMoveCountScalingLaw on real data:
         */
//        int numDeltas = 30;
//        PriceMoveCountScalingLaw priceMoveCountScalingLaw = new PriceMoveCountScalingLaw(0.0001f, 0.05f, numDeltas);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv";
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", 5, dateFormat, 1, 2, 0);
//                priceMoveCountScalingLaw.run(price);
//            }
//            priceMoveCountScalingLaw.finish();
//            System.out.println("Delta + Num DCs:");
//            for (int i = 0; i < numDeltas; i++){
//                System.out.println(priceMoveCountScalingLaw.getArrayDeltas()[i] + " , " + priceMoveCountScalingLaw.getNumPriceMoves()[i]);
//            }
//            double[] params = priceMoveCountScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well


//        /**
//         * Test MaxPriceMoveScalingLaw on real data:
//         */
//        int numSteps = 20;
//        MaxPriceMoveScalingLaw maxPriceMoveScalingLaw = new MaxPriceMoveScalingLaw(10, 1000000, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                maxPriceMoveScalingLaw.run(price);
//            }
//            maxPriceMoveScalingLaw.finish();
//            System.out.println("DeltaT + MeanPriceMove: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(maxPriceMoveScalingLaw.getArrayDeltasT()[i] + " , "
//                        + maxPriceMoveScalingLaw.getMeanMaxPriceMoves()[i]);
//            }
//            maxPriceMoveScalingLaw.saveResults("Results");
//            double[] params = maxPriceMoveScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


//        /**
//         * Test QuadraticMeanMaxPriceMoveScalingLaw on real data:
//         */
//        int numSteps = 20;
//        QuadraticMeanMaxPriceMoveScalingLaw quadraticMeanMaxPriceMoveScalingLaw = new QuadraticMeanMaxPriceMoveScalingLaw(10, 1000000, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                quadraticMeanMaxPriceMoveScalingLaw.run(price);
//            }
//            quadraticMeanMaxPriceMoveScalingLaw.finish();
//            System.out.println("DeltaT + MeanPriceMove: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(quadraticMeanMaxPriceMoveScalingLaw.getArrayDeltasT()[i] + " , "
//                        + quadraticMeanMaxPriceMoveScalingLaw.getMeanMaxPriceMoves()[i]);
//            }
//            quadraticMeanMaxPriceMoveScalingLaw.saveResults("Results");
//            double[] params = quadraticMeanMaxPriceMoveScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


//        /**
//         * Test MeanTimePriceMoveScalingLaw on real data:
//         */
//        int numSteps = 20;
//        MeanTimePriceMoveScalingLaw meanTimePriceMoveScalingLaw = new MeanTimePriceMoveScalingLaw(0.0001f, 0.05f, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2014-01-01_2015-01-01.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                meanTimePriceMoveScalingLaw.run(price);
//            }
//            meanTimePriceMoveScalingLaw.finish();
//            System.out.println("DeltaT + MeanTimePriceMove: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(meanTimePriceMoveScalingLaw.getArrayDeltas()[i] + " , "
//                        + meanTimePriceMoveScalingLaw.getTimeOfPriceMove()[i]);
//            }
//            meanTimePriceMoveScalingLaw.saveResults("Results");
//            double[] params = meanTimePriceMoveScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


//        /**
//         * Test TimeDuringDCScalingLaw on real data:
//         */
//        int numSteps = 20;
//        TimeDuringDCScalingLaw timeDuringDCScalingLaw = new TimeDuringDCScalingLaw(0.0001f, 0.05f, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                timeDuringDCScalingLaw.run(price);
//            }
//            timeDuringDCScalingLaw.finish();
//            System.out.println("Delta + Time During DCs: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(timeDuringDCScalingLaw.getArrayDeltas()[i] + " , "
//                        + timeDuringDCScalingLaw.getTimeDC()[i]);
//            }
//            timeDuringDCScalingLaw.saveResults("Results");
//            double[] params = timeDuringDCScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


//        /**
//         * Test TotalMoveScalingLaw on real data:
//         */
//        int numSteps = 20;
//        TotalMoveScalingLaw totalMoveScalingLaw = new TotalMoveScalingLaw(0.0001f, 0.05f, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                totalMoveScalingLaw.run(price);
//            }
//            totalMoveScalingLaw.finish();
//            System.out.println("Delta + Total Move: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(totalMoveScalingLaw.getArrayDeltas()[i] + " , "
//                        + totalMoveScalingLaw.getTotalMoves()[i]);
//            }
//            totalMoveScalingLaw.saveResults("Results");
//            double[] params = totalMoveScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


//        /**
//         * Test TotalMoveScalingLaw on real data:
//         */
//        int numSteps = 20;
//        TMtickCountScalingLaw tMtickCountScalingLaw = new TMtickCountScalingLaw(0.0001f, 0.05f, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                tMtickCountScalingLaw.run(price);
//            }
//            tMtickCountScalingLaw.finish();
//            System.out.println("Delta + TM ticks num: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(tMtickCountScalingLaw.getArrayDeltas()[i] + " , "
//                        + tMtickCountScalingLaw.getTicksPerTM()[i]);
//            }
//            tMtickCountScalingLaw.saveResults("Results");
//            double[] params = tMtickCountScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


//        /**
//         * Test TotalMoveScalingLaw on real data:
//         */
//        int numSteps = 20;
//        DCtickCountScalingLaw dCtickCountScalingLaw = new DCtickCountScalingLaw(0.0001f, 0.05f, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                dCtickCountScalingLaw.run(price);
//            }
//            dCtickCountScalingLaw.finish();
//            System.out.println("Delta + DC ticks num: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(dCtickCountScalingLaw.getArrayDeltas()[i] + " , "
//                        + dCtickCountScalingLaw.getTicksPerDC()[i]);
//            }
//            dCtickCountScalingLaw.saveResults("Results");
//            double[] params = dCtickCountScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


//        /**
//         * Test TotalMoveScalingLaw on real data:
//         */
//        int numSteps = 20;
//        OStickCountScalingLaw oStickCountScalingLaw = new OStickCountScalingLaw(0.0001f, 0.05f, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                oStickCountScalingLaw.run(price);
//            }
//            oStickCountScalingLaw.finish();
//            System.out.println("Delta + OS ticks num: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(oStickCountScalingLaw.getArrayDeltas()[i] + " , "
//                        + oStickCountScalingLaw.getTicksPerOS()[i]);
//            }
//            oStickCountScalingLaw.saveResults("Results");
//            double[] params = oStickCountScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


//        /**
//         * Test CumulTMScalingLaw on real data:
//         */
//        int numSteps = 20;
//        CumulTMScalingLaw cumulTMScalingLaw = new CumulTMScalingLaw(0.0001f, 0.05f, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                cumulTMScalingLaw.run(price);
//            }
//            cumulTMScalingLaw.finish();
//            System.out.println("Delta + Cumul TM: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(cumulTMScalingLaw.getArrayDeltas()[i] + " , "
//                        + cumulTMScalingLaw.getTotalMoves()[i]);
//            }
//            cumulTMScalingLaw.saveResults("Results");
//            double[] params = cumulTMScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


//        /**
//         * Test CumulOSScalingLaw on real data:
//         */
//        int numSteps = 20;
//        CumulOSScalingLaw cumulOSScalingLaw = new CumulOSScalingLaw(0.0001f, 0.05f, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                cumulOSScalingLaw.run(price);
//            }
//            cumulOSScalingLaw.finish();
//            System.out.println("Delta + Cumul OS: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(cumulOSScalingLaw.getArrayDeltas()[i] + " , "
//                        + cumulOSScalingLaw.getOsMoves()[i]);
//            }
//            cumulOSScalingLaw.saveResults("Results");
//            double[] params = cumulOSScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


//        /**
//         * Test CumulDCScalingLaw on real data:
//         */
//        int numSteps = 20;
//        CumulDCScalingLaw cumulDCScalingLaw = new CumulDCScalingLaw(0.0001f, 0.05f, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                cumulDCScalingLaw.run(price);
//            }
//            cumulDCScalingLaw.finish();
//            System.out.println("Delta + Cumul DC: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(cumulDCScalingLaw.getArrayDeltas()[i] + " , "
//                        + cumulDCScalingLaw.getCumulDClens()[i]);
//            }
//            cumulDCScalingLaw.saveResults("Results");
//            double[] params = cumulDCScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


//        /**
//         * Test TimeDCScalingLaw on real data:
//         */
//        int numSteps = 20;
//        TimeDCScalingLaw timeDCScalingLaw = new TimeDCScalingLaw(0.0001f, 0.05f, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                timeDCScalingLaw.run(price);
//            }
//            timeDCScalingLaw.finish();
//            System.out.println("Delta + Time DC: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(timeDCScalingLaw.getArrayDeltas()[i] + " , "
//                        + timeDCScalingLaw.getTimesDC()[i]);
//            }
//            timeDCScalingLaw.saveResults("Results");
//            double[] params = timeDCScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.



//        /**
//         * Test TimeOSScalingLaw on real data:
//         */
//        int numSteps = 20;
//        TimeOSScalingLaw timeOSScalingLaw = new TimeOSScalingLaw(0.0001f, 0.05f, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                timeOSScalingLaw.run(price);
//            }
//            timeOSScalingLaw.finish();
//            System.out.println("Delta + Time OS: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(timeOSScalingLaw.getArrayDeltas()[i] + " , "
//                        + timeOSScalingLaw.getTimesOS()[i]);
//            }
//            timeOSScalingLaw.saveResults("Results");
//            double[] params = timeOSScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.


//        /**
//         * Test DCmoveScalingLaw on real data:
//         */
//        int numSteps = 20;
//        DCmoveScalingLaw dCmoveScalingLaw = new DCmoveScalingLaw(0.0001f, 0.05f, numSteps);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                dCmoveScalingLaw.run(price);
//            }
//            dCmoveScalingLaw.finish();
//            System.out.println("Delta + DC move: ");
//            for (int i = 0; i < numSteps; i++){
//                System.out.println(dCmoveScalingLaw.getArrayDeltas()[i] + " , "
//                        + dCmoveScalingLaw.getDcMoves()[i]);
//            }
//            dCmoveScalingLaw.saveResults("Results");
//            double[] params = dCmoveScalingLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well.




        /**
         * Test getCorrelatedValue:
         */
//        Random random = new Random();
//        System.out.println("Fully correlated:");
//        for (double i = 0; i < 5; i++) {
//            double randValue = random.nextGaussian();
//            System.out.println(randValue + " , " + Tools.getCorrelatedRandom(randValue, 1));
//        }
//        System.out.println("Fully ANTYcorrelated:");
//        for (double i = 0; i < 5; i++) {
//            double randValue = random.nextGaussian();
//            System.out.println(randValue + " , " + Tools.getCorrelatedRandom(randValue, -1));
//        }
//        System.out.println("Zero correlation:");
//        for (double i = 0; i < 5; i++) {
//            double randValue = random.nextGaussian();
//            System.out.println(randValue + " , " + Tools.getCorrelatedRandom(randValue, 0));
//        }
//        // works well

        /**
         * Test linRegress:
         */
//        Random random = new Random();
//        int nValues = 10;
//        double[] arrayX = new double[nValues];
//        double[] arrayY = new double[nValues];
//        for (int i = 0; i < nValues; i++){
//            arrayX[i] = i;
//            arrayY[i] = i * 3 - 10 + random.nextGaussian();
//        }
//        double[] results = Tools.linRegres(arrayX, arrayY);
//        for (double value : results){
//            System.out.println(value);
//        }
//        // works well

        /**
         * Test toLog:
         */
//        double[] testArray = new double[3];
//        for (int i = 0; i < 3; i++){
//            testArray[i] = i;
//        }
//        testArray = Tools.toLog(testArray);
//        for (int i = 0; i < 3; i++){
//            System.out.println(testArray[i]);
//        }
//        // works well

        /**
         * Test Tools.computeScalingParams:
         * Is checked together with DCcountScalingLaw
         */


        /**
         * Test RealizedVolatilitySeasonality with GBM:
         */
//        int nYears = 8;
//        long nGenrations = (nYears * 60 * 60 * 24 * 365L); // one tick each second
//        int nDecimals = 14;
//        GBM gbm = new GBM(1.0f, 0.2f, nYears, nGenrations, 0.0f);
//        RealizedVolatilitySeasonality realizedVolatilitySeasonality = new RealizedVolatilitySeasonality(0.001, 600000L);
//        for (long i = 0; i < nGenrations; i++){
//            long generatedValue = (long)(gbm.generateNextValue() * Math.pow(10, nDecimals));
//            Price aPrice = new Price(generatedValue, generatedValue, i * 1000L, nDecimals);
//            realizedVolatilitySeasonality.run(aPrice);
//        }
//        double[] volActivity = realizedVolatilitySeasonality.finish();
//        for (int i = 0; i < volActivity.length; i++){
//            System.out.println(i + ",  " + volActivity[i]);
//        }
//        // looks like works well

        /**
         * Test RealizedVolatilitySeasonality with real data:
         */
//        RealizedVolatilitySeasonality realizedVolatilitySeasonality = new RealizedVolatilitySeasonality(0.0001, 600000L);
//        int nDecimals = 4;
//
//        String FILE_PATH = "/Users/vladimir/Documents/Data/";
//        String fileName = "Forex/EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
//
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            long i = 0L;
//            while ((priceLine = bufferedReader.readLine()) != null){
//                String[] tickInfo = priceLine.split(",").clone();
//                long ask = (long)(Double.parseDouble(tickInfo[1]) * Math.pow(10, nDecimals));
//                long bid = (long)(Double.parseDouble(tickInfo[2]) * Math.pow(10, nDecimals));
//                long time = Tools.stringToDate(tickInfo[0], dateFormat).getTime();
//                Price aPrice = new Price(bid, ask, time, nDecimals);
//                realizedVolatilitySeasonality.run(aPrice);
//                if (i % 100000 == 0){
//                    System.out.println(tickInfo[0]);
//                }
//                i++;
//            }
//            double[] volActivity = realizedVolatilitySeasonality.finish();
//            for (int index = 0; index < volActivity.length; index++){
//                System.out.println(index + ",  " + volActivity[index]);
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well

        /**
         * Test InstantaneousVolatilitySeasonality with real data:
         */
//        InstantaneousVolatilitySeasonality instantaneousVolatilitySeasonality = new InstantaneousVolatilitySeasonality(0.001, 600000L);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            long i = 0L;
//            while ((priceLine = bufferedReader.readLine()) != null){
//                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                instantaneousVolatilitySeasonality.run(aPrice);
//                if (i % 100000 == 0){
//                    System.out.println(new Date(aPrice.getTime()));
//                }
//                i++;
//            }
//            double[] volSeasonality = instantaneousVolatilitySeasonality.finish();
//            for (int index = 0; index < volSeasonality.length; index++){
//                System.out.println(index + ",  " + volSeasonality[index]);
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well



        /**
         * Test stringToDate:
         */
//        String stringDate = "1992.12.01 13:23:54.012";
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        Date convertedDate = Tools.stringToDate(stringDate, dateFormat);
//        System.out.println(convertedDate);
//        // works well

        /**
         * Test priceLineToPrice:
         */
//        Price aPrice = Tools.priceLineToPrice("1.23,1.24,12213", ",", 2, "", 1, 0, 2);
//        System.out.println(aPrice.getBid() + " " + aPrice.getAsk() + " " + aPrice.getTime());
//        aPrice = Tools.priceLineToPrice("1.23,1.24,2010.12.24 18:56:17.988", ",", 2, "yyyy.MM.dd HH:mm:ss.SSS", 1, 0, 2);
//        System.out.println(aPrice.getBid() + " " + aPrice.getAsk() + " " + aPrice.getTime());
//        // works well

        /**
         * Test SpreadInfo:
         */
//        SpreadInfo spreadInfo = new SpreadInfo();
//        Price price1 = new Price(9, 11, 0, 0);
//        Price price2 = new Price(14, 17, 0, 0);
//        Price price3 = new Price(14, 16, 0, 0);
//        Price price4 = new Price(10, 11, 0, 0);
//        Price price5 = new Price(2, 4, 0, 0);
//        spreadInfo.run(price1);
//        spreadInfo.run(price2);
//        spreadInfo.run(price3);
//        spreadInfo.run(price4);
//        spreadInfo.run(price5);
//        spreadInfo.finish();
//        spreadInfo.printReport();
//        // works well

        /**
         * Test tools/TraditionalVolatility on GBM:
         */
//        tools.TraditionalVolatility traditionalVolatility = new tools.TraditionalVolatility(86400000L);
//        long nGenrations = 47304000; // number of seconds in 1.5 years
//        int nDecimals = 4;
//        GBM gbm = new GBM(10f, 0.25f, 1.5f, nGenrations, 0);
//        for (long i = 0; i < nGenrations; i++){
//            double generatedValue = gbm.generateNextValue();
//            long longValue = (long) (generatedValue * Math.pow(10, nDecimals));
//            Price price = new Price(longValue, longValue, i * 1000L, nDecimals);
//            traditionalVolatility.run(price);
//        }
//        traditionalVolatility.finish();
//        System.out.println("Annual volatility: " + traditionalVolatility.getYearlyVolatility());
//        System.out.println("Local volatility: " + traditionalVolatility.getLocalVolatility());
//        System.out.println("Total volatility: " + traditionalVolatility.getTotalVolatility());
//        // works well

        /**
         * Test tools/TraditionalVolatility on real data:
         */
//        TraditionalVolatility traditionalVolatility = new TraditionalVolatility(86400000L);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
////        String fileName = "EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
////        String fileName = "EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 3;
////        String fileName = "EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
////        String fileName = "/Bitcoin/krakenUSD.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
////                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, "", 1, 1, 0);
//                traditionalVolatility.run(price);
//            }
//            traditionalVolatility.finish();
//            System.out.println("Yearly volatility: " + traditionalVolatility.getAnnualVolatility());
//            System.out.println("Local volatility: " + traditionalVolatility.getLocalVolatility());
//            System.out.println("Total volatility: " + traditionalVolatility.getTotalVolatility());
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well!


        /**
         * Test BM:
         */
//        long nGenrations = 47304000L;
//        int nDecimals = 5;
//        tools.TraditionalVolatility traditionalVolatility = new tools.TraditionalVolatility(0L, 86400000L);
//        BM bm = new BM(1.0f, 0.150f, 1.5f, nGenrations, 0);
//        for (int i = 0; i < nGenrations; i++){
//            double generatedValue = bm.generateNextValue();
//            long longValue = (long) (generatedValue * Math.pow(10, nDecimals));
//            Price price = new Price(longValue, longValue, i * 1000L, nDecimals);
//            traditionalVolatility.run(price);
//        }
//        traditionalVolatility.finish();
//        System.out.println("Yearly volatility: " + traditionalVolatility.getYearlyVolatility());
//        System.out.println("Local volatility: " + traditionalVolatility.getLocalVolatility());
//        System.out.println("Total volatility: " + traditionalVolatility.getTotalVolatility());
//        // works well

        /**
         * Test ievents/RealizedVolatility with GBM:
         */
//        for (int k = 0; k < 10; k++){
//            RealizedVolatility realizedVolatility = new RealizedVolatility(0.001);
//            double nYears = 2.0;
//            long nGenrations = (long) (nYears * 60 * 60 * 24 * 365L);
//            int nDecimals = 5;
//            GBM gbm = new GBM(1f, 0.25f, (float) nYears, nGenrations, 0);
//            for (long i = 0; i < nGenrations; i++){
//                double generatedValue = gbm.generateNextValue();
//                long longValue = (long) (generatedValue * Math.pow(10, nDecimals));
//                Price price = new Price(longValue, longValue, i * 1000L, nDecimals);
//                realizedVolatility.run(price);
//            }
//            realizedVolatility.finish();
//            System.out.println("Total volatility: " + realizedVolatility.getTotalVolatility());
//            realizedVolatility.normalizeVolatility(realizedVolatility.getTotalVolatility());
//            System.out.println("Annual volatility: " + realizedVolatility.getNormalizedVolatility());
//        }
//        // works well


        /**
         * Test ievents/RealizedVolatility on real data:
         */
//        RealizedVolatility realizedVolatility = new RealizedVolatility(0.004585);
//        String FILE_PATH = "D:/Data/";
////        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//
////        String fileName = "EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
////        String fileName = "EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 3;
//        String fileName = "EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
////        String fileName = "/Bitcoin/krakenUSD.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
////                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, "", 1, 1, 0);
//                realizedVolatility.run(price);
//            }
//            realizedVolatility.finish();
//            System.out.println("Total volatility: " + realizedVolatility.getTotalVolatility());
//            realizedVolatility.normalizeVolatility(realizedVolatility.getTotalVolatility());
//            System.out.println("Annual volatility: " + realizedVolatility.getNormalizedVolatility());
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well


        /**
         * Test ievents/InstantaneousVolatility with GBM:
         */
//        InstantaneousVolatility instantaneousVolatility = new InstantaneousVolatility(0.005);
//        double nYears = 2.0;
//        long nGenrations = (long) (nYears * 60 * 60 * 24 * 365L); // one tick each second
//        int nDecimals = 5;
//        GBM gbm = new GBM(1f, 0.25f, (float) nYears, nGenrations, 0);
//        for (long i = 0; i < nGenrations; i++){
//            double generatedValue = gbm.generateNextValue();
//            long longValue = (long) (generatedValue * Math.pow(10, nDecimals));
//            Price price = new Price(longValue, longValue, i * 1000L, nDecimals);
//            instantaneousVolatility.run(price);
//        }
//        instantaneousVolatility.finish();
//        System.out.println("Total volatility: " + instantaneousVolatility.getTotalVolat());
//        System.out.println("Annual volatility: " + instantaneousVolatility.getAnnualVolat());
//        // works well


        /**
         * Test ievents/InstantaneousVolatility on real data:
         */
//        InstantaneousVolatility instantaneousVolatility = new InstantaneousVolatility(0.005);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                instantaneousVolatility.run(price);
//            }
//            instantaneousVolatility.finish();
//            System.out.println("Total volatility: " + instantaneousVolatility.getTotalVolat());
//            System.out.println("Annual volatility: " + instantaneousVolatility.getAnnualVolat());
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well


        /**
         * Test ievents/RealizedVolatility with BM: // TODO: what is this?
         */
//        int nDecimals = 7;
//        RealizedVolatility realizedVolatility1 = new RealizedVolatility(0.001 * Math.pow(10, nDecimals));
//        double nYears = 1.0;
//        long nGenrations = (long) nYears * 60 * 60 * 24 * 365L;
//        BM bm = new BM(-1f, 0.2f, (float) nYears, nGenrations, 0);
//        for (long i = 0; i < nGenrations; i++){
//            double generatedValue = bm.generateNextValue();
//            long longValue = (long) (generatedValue * Math.pow(10, nDecimals));
//            Price price = new Price(longValue, longValue, i * 1000L, nDecimals);
//            realizedVolatility1.run(price);
//        }
//        realizedVolatility1.finish();
//        System.out.println("Total volatility: " + realizedVolatility1.getTotalVolatility());
//        realizedVolatility1.normalizeVolatility(realizedVolatility1.getTotalVolatility());
//        System.out.println("Normalized volatility: " + realizedVolatility1.getNormalizedVolatility());


        /**
         * Test Tools.CumNorm:
         */
//        System.out.println(Tools.CumNorm(0));
//        System.out.println(Tools.CumNorm(-1));
//        System.out.println(Tools.CumNorm(1));
//        // works well

        /**
         * Test IntrinsicNetwork:
         */
//        IntrinsicNetwork intrinsicNetwork = new IntrinsicNetwork(12, 0.00025, 1);
//        long nGenrations = 86400;
//        int nDecimals = 4;
//        GBM gbm = new GBM(10f, 0.2f, 1.0f/365, nGenrations, 0);
//        for (long i = 0; i < nGenrations; i++) {
//            double generatedValue = gbm.generateNextValue();
//            long longValue = (long) (generatedValue * Math.pow(10, nDecimals));
//            Price price = new Price(longValue, longValue, i * 1000L, nDecimals);
//            System.out.println(i + " sec, surprise " + intrinsicNetwork.run(price));
//        }
//        // it's difficult to judge...

        /**
         * Test LiquidityIndicator on GBM:
         */
//        LiquidityIndicator liquidityIndicator = new LiquidityIndicator(0.00025, 12, 86400000L);
//        long nGenrations = 2592000;
//        int nDecimals = 4;
//        GBM gbm = new GBM(10f, 0.2f, 1.0f/12.0f, nGenrations, 0);
//        for (long i = 0; i < nGenrations; i++) {
//            double generatedValue = gbm.generateNextValue();
//            long longValue = (long) (generatedValue * Math.pow(10, nDecimals));
//            Price price = new Price(longValue, longValue, i * 1000L, nDecimals);
//            double liquidity = liquidityIndicator.run(price);
//            if (i % 1000 == 0){
//                System.out.println(i + " sec, liquidity " + liquidity);
//            }
//        }
//        // works well

        /**
         * Test LiquidityIndicator on real data:
         */
//        LiquidityIndicator liquidityIndicator = new LiquidityIndicator(86400000L);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "USDJPY_UTC_Ticks_Bid_2007-01-01_2007-12-31.csv";
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            long i = 0L;
//            double highestLiquidity = 0;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", 3, dateFormat, 2, 1, 0);
//                double liquidity = liquidityIndicator.run(price);
//                if (liquidity > highestLiquidity){
//                    highestLiquidity = liquidity;
//                    System.out.println(new Date(price.getTime()) + " sec, liquidity " + liquidity);
//                }
//                i++;
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well

        /**
         * Test MovingWindowAnalysis:
         */
//        long binLength = 3600000L;
//        long movingWindowLen = 86400000L;
//        double scalingParams[] = new double[2];
//        scalingParams[0] = 0.10776;
//        scalingParams[1] = -1.8636;
//        MovingWindowAnalysis movingWindowAnalysis = new MovingWindowAnalysis(movingWindowLen, binLength, scalingParams);
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv";
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
//                Price price = Tools.priceLineToPrice(priceLine, ",", 5, dateFormat, 1, 2, 0);
//                movingWindowAnalysis.run(price);
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well


        /**
         * Test ThetaTime on real distribution of activity:
         */
//        double[] weeklyActivitySeasonality = new double[1008];
//        try{
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("SeasonalityEURUSD.csv"));
//            String line;
//            int i = 0;
//            while ((line = bufferedReader.readLine()) != null){
//                weeklyActivitySeasonality[i] = Double.parseDouble(line);
//                i += 1;
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        ThetaTime thetaTime = new ThetaTime();
//        long[] thetaTimestamps = thetaTime.thetaTimestampsFromSeasonalityArray(weeklyActivitySeasonality, 20);
//        for (long thetaTimestamp : thetaTimestamps){
//            System.out.println(thetaTimestamp);
//        }
//        // works well









        /**
         * Test RealizedVolatilityActivity_Analisys on real data:
         */
//        RealizedVolatilityActivity_Analisys volatilityActivity_Analisys = new RealizedVolatilityActivity_Analisys(
//                    "D:/Data/EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv",
//                    "yyyy.MM.dd HH:mm:ss.SSS", 5, 3600000L, 1,
//                    ",", 1, 2, 0, "median");
//        volatilityActivity_Analisys.go();
//        volatilityActivity_Analisys.printResults();
//        // works well


        /**
         * Test NumDCperPeriod with real data in physical time:
         */
//        NumDCperPeriod numDCperPeriod = new NumDCperPeriod(0.0001, 1008, false);
//
//        String FILE_PATH = "D:/Data/";
//        String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            long i = 0;
//            while ((priceLine = bufferedReader.readLine()) != null){
//                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                numDCperPeriod.run(aPrice);
//                if (i % 100000 == 0){
//                    System.out.println(new Date(aPrice.getTime()));
//                }
//                i++;
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // Write results into a file:
//        ArrayList<Integer> binIndexesArray = numDCperPeriod.getBinIndexesArray();
//        ArrayList<Integer> numDCsPerBinArray = numDCperPeriod.getNumDCsPerBinArray();
//        try{
//            PrintWriter writer = new PrintWriter("Results/numDCperBinTEST.csv", "UTF-8");
//            for (int i = 0; i < binIndexesArray.size(); i++){
//                writer.println(binIndexesArray.get(i) + "," + numDCsPerBinArray.get(i));
//            }
//            writer.close();
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well



        /**
         * Test NumDCperPeriod with real data in theta time:
         */
        // Upload volatility seasonality:

        double[] weeklyActivitySeasonality = new double[1008];
        try{
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("SeasonalityEURUSD.csv"));
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("SeasonalityEURJPY.csv"));
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("SeasonalityEURGBP.csv"));
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("SeasonalityBTCUSD.csv"));
            BufferedReader bufferedReader = new BufferedReader(new FileReader("ZERONIGHTS_SeasonalitySPX500.csv"));

            String line;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null){
                weeklyActivitySeasonality[i] = Double.parseDouble(line);
                i += 1;
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        //
        NumDCperPeriod numDCperPeriod = new NumDCperPeriod(0.0001, 1008, true);
        numDCperPeriod.uploadWeeklyActivitySeasonality(weeklyActivitySeasonality);

        String FILE_PATH = "/Users/vladimir/Documents/Data/";
//        String fileName = "Forex/EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
//        String fileName = "Forex/EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 3;
//        String fileName = "Forex/EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
//        String fileName = "/Crypto/krakenUSD.csv"; int nDecimals = 5;
        String fileName = "Stocks/SPX500/USA500IDXUSD_Ticks_2012.01.16_2017.01.01.csv"; int nDecimals = 3;



        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
            bufferedReader.readLine().split(",").clone();
            String priceLine;
            long i = 0;
            while ((priceLine = bufferedReader.readLine()) != null){
                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, "", 1, 1, 0);
                numDCperPeriod.run(aPrice);
                if (i % 100000 == 0){
                    System.out.println(new Date(aPrice.getTime()));
                }
                i++;
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        // Write results into a file:
        ArrayList<Integer> binIndexesArray = numDCperPeriod.getBinIndexesArray();
        ArrayList<Integer> numDCsPerBinArray = numDCperPeriod.getNumDCsPerBinArray();
        try{
            PrintWriter writer = new PrintWriter("Results/numDCperBinThetaSPX500_TEST.csv", "UTF-8");
            for (int i = 0; i < binIndexesArray.size(); i++){
                writer.println(binIndexesArray.get(i) + "," + numDCsPerBinArray.get(i));
            }
            writer.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        // works well


        /**
         * Test ClassicVolatilitySeasonality with real data:
         */
//        ClassicVolatilitySeasonality classicVolatilitySeasonality = new ClassicVolatilitySeasonality(600000L);
//
//        String FILE_PATH = "D:/Data/";
////        String fileName = "EURUSD_UTC_1 Min_Bid_2010.12.31_2016.01.01.csv"; int nDecimals = 5;
////        String fileName = "EURJPY_UTC_1 Min_Bid_2010.12.31_2016.01.01.csv"; int nDecimals = 3;
////        String fileName = "EURGBP_UTC_1 Min_Bid_2010.12.31_2016.01.01.csv"; int nDecimals = 5;
//        String fileName = "/Bitcoin/1minBTCBitstamp20162018.csv"; int nDecimals = 2;
//
//
//        String dateFormat = "yyyy.MM.dd HH:mm:ss";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine();
//            String priceLine;
//            long i = 0L;
//            while ((priceLine = bufferedReader.readLine()) != null){
////                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 4, 1, 0);
//                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, "yyyy-MM-dd HH:mm:ss", 5, 2, 1);
//                classicVolatilitySeasonality.run(aPrice);
//                if (i % 100000 == 0){
//                    System.out.println(new Date(aPrice.getTime()));
//                }
//                i++;
//            }
//            double[] volSeasonality = classicVolatilitySeasonality.finish();
//            for (int index = 0; index < volSeasonality.length; index++){
//                System.out.println(index + ",  " + volSeasonality[index]);
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well







    }
}
