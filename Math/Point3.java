package math;

public class Point3 {
    public double x, y, w;

    // Constructor
    public Point3(double x0, double y0, double w0) {
        x = x0;
        y = y0;
        w = w0;
    }

    public Point3() {
        x = 0;
        y = 0;
        w = 0;
    }

    public Point3(double[] arr) {
        x = arr[0];
        y = arr[1];
        w = arr[2];
    }

    public void sum(Point3 p) {
        x += p.x;
        y += p.y;
    }

    public void print() {
        System.out.println("x: " + x + " y: " + y + " w: " + w);
    }
}
