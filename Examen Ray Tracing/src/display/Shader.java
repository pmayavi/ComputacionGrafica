package display;

import math.Vector4;
import geometry.Ray;
import geometry.Solution;

public class Shader {
    public static Colour computeLocalIllumination(Vector4 position, Vector4 normal, 
        Colour materialColor, Material material) {
        Colour acum = new Colour(0, 0, 0);
        // Compute ambient light
        Colour ambient = Scene.colors.get(Scene.ambientLight);
        ambient = ambient.timesScalar(material.kA);
        ambient = ambient.timesColour(materialColor);
        acum.acumColor(ambient);
        for (Light light : Scene.lights) {
            Vector4 lightVector = Vector4.subtract(light.position, position);
            Vector4 toLight = lightVector.copy();
            toLight.normalize();
            //double lightDistance = lightVector.length();
            //double attenuation = 1 / (lightDistance * lightDistance);
            //double dot = lightDirection.dot(normal);
            double dot = Vector4.dotProduct(toLight, normal);
            if (dot > 0) {
                Colour lightColor = Scene.colors.get(light.colorIndex);
                //Colour materialColor = Scene.colors.get(colorIndex);
                //Material material = Scene.materials.get(materialIndex);
                Colour diffuse = lightColor.timesScalar(dot * material.kD);
                diffuse = diffuse.timesColour(materialColor);
                Colour specular = new Colour(0, 0, 0);
                if (dot > 0) {
                    lightVector = Vector4.subtract(position, light.position);
                    Vector4 lightDirection = lightVector.copy();
                    lightDirection.normalize();
                    double dot3 = Vector4.dotProduct(lightDirection, normal);
                    Vector4 reflection = lightDirection.subtract(normal.timesScalar(2 * dot3));
                    //double dot2 = reflection.dot(Scene.cameraDirection);
                    Vector4 cameraDirection = Vector4.subtract(Scene.camera.cameraPosition, position);
                    cameraDirection.normalize();
                    //double dot2 = Vector4.dotProduct(reflection, Scene.camera.cameraDirection());
                    double dot2 = Vector4.dotProduct(reflection, cameraDirection);
                    if (dot2 > 0) {
                        specular = lightColor.timesScalar(Math.pow(dot2, material.n) * material.kS);
                    }
                }
                // Check if the point is in the shadow of an object
                
                Ray shadowRay = new Ray(position, light.position);
                Solution s = Scene.intersectForShadow(shadowRay);
                
                double distance = 0;
                if(s != null) {
                    distance = Vector4.distance(position, s.intersectionPoint);
                }
                
                if(s == null  || s != null && distance < 0.1) {
                    acum.acumColor(diffuse);
                    acum.acumColor(specular);
                }   
                
            }
        }
        return acum;
    }
}
