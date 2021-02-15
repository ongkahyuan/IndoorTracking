package com.example.indoortracking.tracking.triangulation;

public class Matrix {
    public double[][] data;
    public int rows = 0, cols = 0;

    public Matrix(int rows, int cols) {
        data = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public Matrix(double[][] data) {
        this.data = data.clone();
        rows = this.data.length;
        cols = this.data[0].length;
    }

    // Multiply function for 2 matrices
    public Matrix multiply(Matrix second) {
        double[][] product = new double[this.rows][second.cols];

        for (int i = 0; i < this.rows ; i++) {
            for (int j = 0; j < second.cols; j++) {
                product[i][j] = 0;
                for (int k = 0; k < this.cols; k++) {
                    product[i][j] += this.data[i][k] * second.data[k][j];
                }
            }
        }
        return new Matrix(product);
    }

    // Note: exclude_row and exclude_col starts from 1
    public static Matrix subMatrix(Matrix matrix, int exclude_row, int exclude_col) {
        Matrix result = new Matrix(matrix.rows - 1, matrix.cols - 1);

        for (int row = 0, p = 0; row < matrix.rows; ++row) {
            if (row != exclude_row - 1) {
                for (int col = 0, q = 0; col < matrix.cols; ++col) {
                    if (col != exclude_col - 1) {
                        result.data[p][q] = matrix.data[row][col];

                        ++q;
                    }
                }

                ++p;
            }
        }

        return result;
    }

    public double determinant() {
        if (rows != cols) {
            return Double.NaN;
        }
        else {
            return _determinant(this);
        }
    }

    private double _determinant(Matrix matrix) {
        if (matrix.cols == 1) {
            return matrix.data[0][0];
        }
        else if (matrix.cols == 2) {
            return (matrix.data[0][0] * matrix.data[1][1] -
                    matrix.data[0][1] * matrix.data[1][0]);
        }
        else {
            double result = 0.0;

            for (int col = 0; col < matrix.cols; ++col) {
                Matrix sub = subMatrix(matrix, 1, col + 1);

                result += (Math.pow(-1, 1 + col + 1) *
                        matrix.data[0][col] * _determinant(sub));
            }

            return result;
        }
    }

    // Inverse function for matrix
    public Matrix inverse() {
        double det = determinant();

        if (rows != cols || det == 0.0) {
            return null;
        }
        else {
            Matrix result = new Matrix(rows, cols);

            for (int row = 0; row < rows; ++row) {
                for (int col = 0; col < cols; ++col) {
                    Matrix sub = subMatrix(this, row + 1, col + 1);

                    result.data[col][row] = (1.0 / det *
                            Math.pow(-1, row + col) *
                            _determinant(sub));
                }
            }

            return result;
        }
    }

    public double[][] getData() {
        return this.data;
    }

}
