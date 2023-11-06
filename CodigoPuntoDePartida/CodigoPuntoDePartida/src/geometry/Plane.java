package geometry;
import math.Vector4;
import display.Scene;
import math.UVN4x4;
import math.Matrix4x4;

public class Plane implements IntersectableObject {
    Vector4 p1;
    Vector4 p2;
    Vector4 p3;

    Vector4 transformedP1;
    Vector4 transformedP2;
    Vector4 transformedP3;

    Vector4 n;
    double d;

    int colorIndex;
    int materialIndex;

    public Plane(Vector4 p1, Vector4 p2, Vector4 p3, int colorIndex, int materialIndex) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.transformedP1 = p1;
        this.transformedP2 = p2;
        this.transformedP3 = p3;
        this.colorIndex = colorIndex;
        this.materialIndex = materialIndex;
    }
    
    public Solution intersect(Ray ray) {
        // Calcula el numerador de la ecuación de la intersección
        double numerator = -(Vector4.dotProduct(ray.origin, n) + d);
        // Calcula el denominador de la ecuación de la intersección
        double denominator = Vector4.dotProduct(ray.direction, n);
    
        // Verifica si el rayo es paralelo al plano (sin intersección)
        if (denominator == 0) {
            return null;
        }
    
        // Calcula la distancia a lo largo del rayo al punto de intersección
        double t = numerator / denominator;
    
        // Verifica si la intersección ocurre detrás del origen del rayo
        if (t < 0) {
            return null;
        }
    
        // Calcula las coordenadas del punto de intersección
        Vector4 intersectionPoint = Vector4.add(ray.origin, Vector4.multiply(ray.direction, t));
    
        // Crea una instancia de Solution para almacenar la información de la intersección
        Solution solution = new Solution(intersectionPoint, n, Scene.colors.get(colorIndex), Scene.materials.get(materialIndex));
    
        return solution;
    }

    public void setCamera() {
        UVN4x4 uvn = Scene.camera.uvn;
        transformedP1 = Matrix4x4.times(uvn, p1);
        transformedP2 = Matrix4x4.times(uvn, p2);
        transformedP3 = Matrix4x4.times(uvn, p3);
        computeNormalAndD();
    }

    public void computeNormalAndD() {
        Vector4 p1p2 = Vector4.subtract(transformedP2, transformedP1);
        Vector4 p1p3 = Vector4.subtract(transformedP3, transformedP1);
        this.n = Vector4.crossProduct(p1p2, p1p3);
        this.n.normalize();
        this.d = - Vector4.dotProduct(transformedP1, n);
    }

    public String toString() {
        String s = "a: " + this.n.vector[0] + " b: " + this.n.vector[1] + 
            " c: " + this.n.vector[2] + " d: " + this.d;
        return s;
    }

    public static void main(String [] args) {
        Vector4 p1 = new Vector4(-100, -100, -1000);
        Vector4 p2 = new Vector4(0, -100, -900);
        Vector4 p3 = new Vector4(100, -100, -1000);
        Plane plane = new Plane(p1, p2, p3, 0, 0);
        plane.computeNormalAndD();
        System.out.println(plane);
    }

}
