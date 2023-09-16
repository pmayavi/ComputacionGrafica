package math;

public class TranslScalRot4x4 extends Matrix4x4 {
    public TranslScalRot4x4() {
        super();
    }

    public TranslScalRot4x4(double dx, double dy, double dz,
            double sx, double sy, double sz,
            double thetaX, double thetaY, double thetaZ,
            double centerX, double centerY, double centerZ,
            double u, double v, double n,
            double cameraTx, double cameraTy) {
        super();
        // Camara
        matrix = Matrix4x4.times(this, new Translation4x4(u, v, n)).matrix;
        matrix = Matrix4x4.times(this, new RotationX4x4(cameraTx)).matrix;
        matrix = Matrix4x4.times(this, new RotationY4x4(cameraTy)).matrix;

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