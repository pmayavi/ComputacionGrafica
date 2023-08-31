package Math;

public class Matrix {
    public double matrix[][];
    public int cols, rows;

    public Matrix(int s) {
        matrix = new double[s][s];
        rows = s;
        cols = s;
    }

    public Matrix(int r, int c) {
        matrix = new double[rows][cols];
        rows = r;
        cols = c;
    }

    public Point3 times(Matrix mat, Point3 p) {
        double result[] = new double[mat.rows];

        for (int i = 0; i < mat.rows; i++) {
            double sum = 0;
            sum += mat.matrix[i][0] * p.x;
            sum += mat.matrix[i][1] * p.y;
            sum += mat.matrix[i][2] * p.w;
            result[i] = sum;
        }

        return new Point3(result);
    }

    public static Matrix times(Matrix A, Matrix B) {
        Matrix result = new Matrix(A.rows, B.cols);

        for (int i = 0; i < A.rows; i++) {
            for (int j = 0; j < B.cols; j++) {
                int sum = 0;
                for (int k = 0; k < A.cols; k++) {
                    sum += A.matrix[i][k] * B.matrix[k][j];
                }
                result.matrix[i][j] = sum;
            }
        }

        return result;
    }
}
