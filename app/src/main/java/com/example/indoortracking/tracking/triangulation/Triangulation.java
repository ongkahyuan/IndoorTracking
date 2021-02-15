package com.example.indoortracking.tracking.triangulation;

import android.os.Bundle;

import com.example.indoortracking.R;

import java.util.ArrayList;

public class Triangulation {

    public double[] getPoint(double[][] points, double[] distances) throws RuntimeException {

        // Check if less than 3 points
        if (distances.length < 3) {
            throw new RuntimeException("Need at least 3 points to triangulate!");
        }

        double[][] A = new double[points.length - 1][2];
        double[][] B = new double[points.length - 1][1];

        // x1 value
        double x1 = points[0][0];
        double y1 = points[0][1];
        double r1 = distances[0];

        // Iterate from index 1 onwards
        for (int i = 1; i < distances.length; i++) {

            // Add a row to A matrix
            double xi = points[i][0];
            double yi = points[i][1];
            double ri = distances[i];

            A[i-1][0] = 2 * (xi - x1);
            A[i-1][1] = 2 * (yi - y1);

            // Add a row to B matrix
            B[i-1][0] = Math.pow(r1, 2) - Math.pow(ri, 2) +  Math.pow(xi, 2) + Math.pow(yi, 2) - Math.pow(x1, 2) - Math.pow(y1, 2);
        }

        Matrix asMatrixA = new Matrix(A);
        Matrix asMatrixB = new Matrix(B);

        Matrix AInverse = asMatrixA.inverse();
        Matrix userMatrix = AInverse.multiply(asMatrixB);
        double[][] userArray = userMatrix.getData();
        double[] userPoint = {userArray[0][0], userArray[1][0]};
        return userPoint;
    }

}