package ievents;

import java.io.File;

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
    public static float[] GenerateLogSpace(float min, float max, int nBins)
    {
        float[] logList = new float[nBins];
        float m = 1.0f / (nBins - 1);
        float quotient =  (float) Math.pow(max / min, m);
        logList[0] = min;
        for (int i = 1; i < nBins; i++){
            logList[i] = logList[i - 1] * quotient;
        }
        return logList;
    }

}
