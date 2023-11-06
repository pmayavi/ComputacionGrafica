package geometry;
import math.Vector4;
import display.Colour;
import display.Material;

public class Solution {
    public Vector4 intersectionPoint;
    public Vector4 normal;
    public Colour colour;
    public Material material;

    // Constructor
    public Solution(Vector4 intersectionPoint, Vector4 normal, Colour colour, Material material) {
        this.intersectionPoint = intersectionPoint;
        this.normal = normal;
        this.colour = colour;
        this.material = material;
    }
}
