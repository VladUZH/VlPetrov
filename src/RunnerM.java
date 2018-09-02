/**
 * Created by Vladimir Petrov on 05.02.2018.
 */
public class RunnerM {

    public double[] prevExtreme;
    public double[] prevDC;
    public double[] extreme;
    public double delta;
    public double osL;
    public int mode;
    public boolean initialized;
    public boolean firstPrice = true;
    public double[] prevExtDCLine; // is a vector starting at prevExt and going through the DC point. Shifter to start from 0.
    public double[] extSurf; // is a surface orthogonal to the vector prevExtDCLine ang going through a given point


    public RunnerM(double delta){
        this.delta = delta; osL = 0;
        initialized = false;
    }


    public int run(ATickM aTickM){
        double[] aPrice = aTickM.getMid().clone(); // TODO: to change it to the inner price

        if ( !initialized ){

            if (firstPrice){
                extreme = aPrice.clone();
                firstPrice = false;
            }

            if (getDistance(aPrice, extreme) >= delta){
                mode = (aPrice[0] >= extreme[0] ? 1 : -1); // the first element in the array (the first exchange rate) defines the mode
                prevExtreme = extreme.clone();
                prevDC = aPrice.clone();
                extreme = aPrice.clone();
                prevExtDCLine = lineVector(prevExtreme, prevDC);
                extSurf = surfCoef(prevExtDCLine, aPrice);
                initialized = true;
                return mode;
            }

        } else {
            boolean newExtreme = false;
            newExtreme = (diffSides(extSurf, prevExtreme, aPrice));

            if (newExtreme) {
                extSurf = surfCoef(prevExtDCLine, aPrice);
                extreme = aPrice.clone();
                return 0;

            } else {
                boolean newDC = false;
                newDC = (getDistance(aPrice, projPointSurf(extSurf, aPrice)) >= delta);

                if (newDC){
                    for (double coeff: extSurf){ System.out.print(coeff + ", ");} System.out.println("");
                    osL = getDistance(projPointSurf(extSurf, prevDC), prevDC);
//                  prevExtreme = projPointSurf(extSurf, aPrice); // Alternative version.
                    prevExtreme = extreme.clone(); // The main version.
                    extreme = aPrice.clone();
                    prevExtDCLine = lineVector(prevExtreme, aPrice);
                    extSurf = surfCoef(prevExtDCLine, aPrice);

                    prevDC = aPrice.clone();
                    mode = -mode;
                    return mode;
                }
            }
        }

        return 0;
    }


    /**
     * The method computes Euclidean distance of multidimensional arrays.
     * @param array1
     * @param array2
     * @return relative distance.
     */
    public static double getDistance(double array1[], double[] array2){
        double Sum = 0;
        for (int i = 0; i < array1.length; i++){
            Sum += Math.pow((array1[i] - array2[i]) / array2[i], 2);
        }
        return Math.sqrt(Sum);
    }

    /**
     * The method returns a set of coordinates which define a vector of a line going thought two given points (a,b,...,c)
     * @return array of coefficients a, b,..., c
     */
    private double[] lineVector(double[] pointA, double[] pointB){
        double[] vector = new double[pointA.length];
        for (int i = 0; i < pointA.length; i++){
            vector[i] = pointB[i] - pointA[i];
        }
        return vector;
    }

    /**
     * Computes coefficients of a surface defined as Ax+By+...+D=0
     * @param lineVector is the vector to which the surface should be orthogonal
     * @param pointToGoThrough is a point on the vector through which the surface should go
     * @return array of coefficients A,B,C,...,D
     */
    private double[] surfCoef(double[] lineVector, double[] pointToGoThrough){ // double checked, all correct
        double[] surfCoef = new double[lineVector.length + 1];
        double lastCoef = 0;
        for (int i = 0; i < lineVector.length; i++){
            surfCoef[i] = lineVector[i];
            lastCoef += lineVector[i] * pointToGoThrough[i];
        }
        surfCoef[surfCoef.length - 1] = -lastCoef;
        return surfCoef;
    }

    /**
     * Computes distance between a point and its projection on a surface
     * @param surfCoef
     * @param point
     * @return
     */
    private double distSurfPoint(double[] surfCoef, double[] point){
        double[] projPoint = projPointSurf(surfCoef, point);
        return getDistance(projPoint, point);
    }


    /**
     * Tells whether two point A and B are on different sides of a surface.
     * @param surfCoef are coef A,B,C,...,D of a surf Ax+By+...+D=0
     * @param pointA
     * @param pointB
     * @return true of different sides.
     */
    private boolean diffSides(double[] surfCoef, double[] pointA, double[] pointB){
        double valA = 0;
        double valB = 0;
        for (int i = 0; i < pointA.length; i++){
            valA += surfCoef[i] * pointA[i];
            valB += surfCoef[i] * pointB[i];
        }
        valA += surfCoef[surfCoef.length - 1];
        valB += surfCoef[surfCoef.length - 1];
        return (valA * valB < 0);
    }


    /**
     * Computes coordinates of a point W projected on a surface Ax+By+...+D=0.
     * The used equation: W' = W - lambda * |n|
     * @param surfCoef are coef A,B,C,...,D of a surf Ax+By+...+D=0
     * @param point is the point one wants to project
     * @return array of coordinates
     */
    private double[] projPointSurf(double[] surfCoef, double[] point){ // double checked, all correct
        double[] projection = new double[point.length];
        double upPart = 0, botPart = 0;

        for (int i = 0; i < point.length; i++){
            upPart += surfCoef[i] * point[i];
            botPart += Math.pow(surfCoef[i], 2);
        }

        upPart += surfCoef[surfCoef.length - 1];
        double lambda = upPart / botPart; // must be not abs value

        for (int i = 0; i < point.length; i++){
            projection[i] = point[i] - lambda * surfCoef[i];
        }

        return projection;
    }


}
