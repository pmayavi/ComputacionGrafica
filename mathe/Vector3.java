package math;

public class Vector3 {
    public double x, y, z;

    public Vector3(double x0, double y0, double z0) {
        x = x0;
        y = y0;
        z = z0;
    }

    public Vector3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public static Vector3 crossProduct(Vector3 A, Vector3 B) {
        Vector3 result = new Vector3();

        result.x = A.y * B.z - A.z * B.y;
        result.y = A.z * B.x - A.x * B.z;
        result.z = A.x * B.y - A.y * B.x;

        return result;
    }

    public static double dotProduct(Vector3 A, Vector3 B) {
        double result = A.x * B.x + A.y * B.y + A.z * B.z;
        return result;
    }

    public double Magnitude() {
        double sum = 0;
        sum += x * x;
        sum += y * y;
        sum += z * z;
        return Math.sqrt(sum);
    }

    public void Normalize() {
        double magnitude = Magnitude();

        x /= magnitude;
        y /= magnitude;
        z /= magnitude;
    }
}
