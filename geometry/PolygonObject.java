package geometry;

import java.util.ArrayList;
import java.util.Scanner;

import ThirdDimension.Main;
import math.Matrix4x4;
import math.TranslScalRot4x4;
import math.Translation4x4;
import math.Projection4x4;
import math.RotationX4x4;
import math.RotationY4x4;
import math.Vector4;
import java.io.File;
import java.awt.Color;
import java.awt.Graphics;

public class PolygonObject {
    ArrayList<Vector4> vertices;
    public ArrayList<Vector4> transformedVertices; // vertices after transformation
    public ArrayList<Edge> edges;
    Main canvas;
    public ObjectTransformation ot;

    public PolygonObject() {
        vertices = new ArrayList<Vector4>();
        transformedVertices = new ArrayList<Vector4>();
        edges = new ArrayList<Edge>();
        ot = new ObjectTransformation();
    }

    public void setCanvas(Main canvas) {
        this.canvas = canvas;
    }

    public void readObject(String filename) {
        try {
            // read the number of vertices and then the vertices
            Scanner in = new Scanner(new File(filename));
            int n = in.nextInt();
            for (int i = 0; i < n; i++) {
                double x = in.nextDouble();
                double y = in.nextDouble();
                double z = in.nextDouble();
                vertices.add(new Vector4(x, y, z));
            }
            // read the number of edges and then the edge indices
            n = in.nextInt();
            for (int i = 0; i < n; i++) {
                int start = in.nextInt();
                int end = in.nextInt();
                edges.add(new Edge(start, end));
            }
            // read the center of the object
            // rotations and scaling are done with respect to the center
            ot.centerX = in.nextInt();
            ot.centerY = in.nextInt();
            ot.centerZ = in.nextInt();
            // read the Z coordinate of the the projection plane.
            // Since the camera is at the origin looking into the negative
            // z axis, the projection plane is at z = -projectionDistance
            ot.projectionDistance = in.nextInt();
        } catch (Exception e) {
            System.out.println("Error reading file");
        }
        resetVertices();
    }

    public void draw() {
        if (this.canvas != null) {
            for (Edge e : edges) {
                // draw the transformed edges
                Vector4 v1 = transformedVertices.get(e.start);
                Vector4 v2 = transformedVertices.get(e.end);
                int x1 = (int) v1.vector[0];
                int y1 = (int) v1.vector[1];
                int x2 = (int) v2.vector[0];
                int y2 = (int) v2.vector[1];
                canvas.drawOneLine(x1, y1, x2, y2);
            }
        }
    }

    public void drawColor(Graphics g) {
        if (this.canvas != null) {
            g.setColor(Color.GREEN);
            int i = 0;
            for (Edge e : edges) {
                // draw the transformed edges
                Vector4 v1 = transformedVertices.get(e.start);
                Vector4 v2 = transformedVertices.get(e.end);
                int x1 = (int) v1.vector[0];
                int y1 = (int) v1.vector[1];
                int x2 = (int) v2.vector[0];
                int y2 = (int) v2.vector[1];
                canvas.drawOneLine(x1, y1, x2, y2);
                if (++i == 2)
                    g.setColor(Color.BLUE);
                else
                    g.setColor(Color.RED);
            }
        }
    }

    public void resetVertices() {
        ot.reset();
        transformedVertices.clear();
        for (Vector4 v : vertices) {
            Vector4 newVertex = new Vector4(v.vector[0], v.vector[1], v.vector[2]);
            transformedVertices.add(newVertex);
        }
    }

    public void transformObject(double u, double v, double n, double cameraTx, double cameraTy) {
        Matrix4x4 crx = new RotationX4x4(cameraTx);
        Matrix4x4 cry = new RotationY4x4(cameraTy);
        Matrix4x4 ct = new Translation4x4(u, v, n);
        transformedVertices.clear();
        TranslScalRot4x4 tsr = ot.createTransformation();
        for (Vector4 ver : vertices) {
            Vector4 newVertex = ver;
            newVertex = Matrix4x4.times(crx, newVertex);
            newVertex = Matrix4x4.times(cry, newVertex);
            newVertex = Matrix4x4.times(tsr, newVertex);
            newVertex = Matrix4x4.times(ct, newVertex);
            transformedVertices.add(newVertex);
        }
    }

    public void projectObject() {
        ArrayList<Vector4> projectedVertices = new ArrayList<>();
        Projection4x4 p = ot.createProjection();
        for (Vector4 v : transformedVertices) {
            Vector4 newVertex = Matrix4x4.times(p, v);
            newVertex.normalizeW();
            projectedVertices.add(newVertex);
        }
        transformedVertices = projectedVertices;
    }

}