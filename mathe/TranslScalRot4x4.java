package math;

public class TranslScalRot4x4 extends Matrix4x4 {

    double dx = 0;
    double dy = 0;
    double dz = 0;
    double sx = 1;
    double sy = 1;
    double sz = 1;
    double thetaX = 0;
    double thetaY = 0;
    double thetaZ = 0;
    double centerX = 0;
    double centerY = 0;
    double centerZ = 0;

    public TranslScalRot4x4() {
        super();
    }

    public TranslScalRot4x4(double dx, double dy, double dz,
            double sx, double sy, double sz,
            double thetaX, double thetaY, double thetaZ,
            double centerX, double centerY, double centerZ) {
        super();
        // Regresar al centro
        matrix = Matrix4x4.times(this, new Translation4x4(centerX + dx, centerY + dy, centerZ + dz)).matrix;
        // Scaling
        matrix = Matrix4x4.times(this, new Scaling4x4(sx, sy, sz)).matrix;
        // Rotaciones
        matrix = Matrix4x4.times(this, new RotationX4x4(thetaX)).matrix;
        matrix = Matrix4x4.times(this, new RotationY4x4(thetaY)).matrix;
        matrix = Matrix4x4.times(this, new RotationZ4x4(thetaZ)).matrix;
        // Traslacion
        matrix = Matrix4x4.times(this, new Translation4x4(-centerX, -centerY, -centerZ)).matrix;
    }

}