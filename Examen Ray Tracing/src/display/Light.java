package display;
//import math.Matrix4x4;
import math.Vector4;

public class Light {
    Vector4 position;
    Vector4 transformedPosition;
    int colorIndex;

    public Light(Vector4 position, int colorIndex) {
        this.position = position;
        this.transformedPosition = new Vector4();
        this.colorIndex = colorIndex;
    }

    //public void setCamera() {
    //    transformedPosition = Matrix4x4.times(Scene.camera.uvn, position);
    //}
}
