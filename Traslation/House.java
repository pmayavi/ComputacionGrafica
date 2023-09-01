package Traslation;

import Math.Point3;
import Math.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;

public class House extends JPanel implements KeyListener {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    private static final int SPEED = 10;
    private static final double INCREASE = 1.1;
    private static final int FRAMES = 1;
    private static final double ROTATION_ANGLE = Math.toRadians(5);
    Ellipse2D.Double house;
    List<Integer> lineas;
    Point3 points[];
    Point3 base_points[];
    int X_MOVMENT=0;
    int Y_MOVMENT=0;

    public House() {
        points = new Point3[0];
        lineas = new ArrayList<>();
        house = new Ellipse2D.Double(10, 10, 50, 50);
        // El panel, por defecto no es "focusable".
        // Hay que incluir estas líneas para que el panel pueda
        // agregarse como KeyListsener.
        ejercicio("DrawFile/archivo.txt");
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        int tx = 0, ty = 0;
        double sx=0, sy=0;
        double angle = 0;
        boolean rotation_happen=false, scaling=false;
        // System.out.println("Key pressed");
        if (tecla == KeyEvent.VK_W){
            ty += SPEED;
            Y_MOVMENT+=SPEED;
        }
        if (tecla == KeyEvent.VK_S){
            ty -= SPEED;
            Y_MOVMENT-=SPEED;
        }
        if (tecla == KeyEvent.VK_D){
            tx += SPEED;
            X_MOVMENT+=SPEED;
        }
        if (tecla == KeyEvent.VK_A){
            tx -= SPEED;
            X_MOVMENT-=SPEED;
        }

        for (int i = 0; i < points.length; i++) {
            points[i] = traslation(points[i], tx, ty);
        }
        if (tecla == KeyEvent.VK_Q){
            angle += ROTATION_ANGLE;
            rotation_happen=true;
        }
        if (tecla == KeyEvent.VK_E){
            angle -= ROTATION_ANGLE;
            rotation_happen=true;
        }
        for (int i = 0; i < points.length; i++) {
            base_points[i] = rotation(base_points[i], angle);
        }
        if (rotation_happen){
        for(int i = 0; i < points.length; i++){
            points[i] = traslation(base_points[i], X_MOVMENT, Y_MOVMENT);
            }
        }
        if (tecla == KeyEvent.VK_M){
            sx += INCREASE;
            sy += INCREASE;
            scaling=true;

        }
        if (tecla == KeyEvent.VK_N){
            sx += (double)1/INCREASE;
            sy += (double)1/INCREASE;
            scaling=true;
        }
        if (scaling){
            for (int i = 0; i < points.length; i++) {
                base_points[i] = scaling(base_points[i], sx, sy);
            }
        for(int i = 0; i < points.length; i++){
            points[i] = traslation(base_points[i], X_MOVMENT, Y_MOVMENT);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxis(g);
        g.setColor(Color.blue);
        for (int i = 0; i < lineas.size(); i += 2) {
            myDrawLine(g, points[lineas.get(i)].x, points[lineas.get(i)].y,
                    points[lineas.get(i + 1)].x, points[lineas.get(i + 1)].y);
        }
    }

    public void drawAxis(Graphics g) {
        g.setColor(Color.red);
        myDrawLine(g, -1000, 0, 1000, 0);
        g.setColor(Color.green);
        myDrawLine(g, 0, -1000, 0, 1000);
    }

    public void myDrawPoint(Graphics g, int x, int y) {
        int xj = x + WIDTH / 2;
        int yj = HEIGHT / 2 - y;
        g.drawLine(xj, yj, xj, yj);
    }

    public void myDrawLine(Graphics g, double x1, double y1, double x2, double y2) {
        int xj1 = (int) x1 + WIDTH / 2;
        int yj1 = HEIGHT / 2 - (int) y1;
        int xj2 = (int) x2 + WIDTH / 2;
        int yj2 = HEIGHT / 2 - (int) y2;
        g.drawLine(xj1, yj1, xj2, yj2);
    }
    
    public Point3 rotation(Point3 point, double angle) {
        Matrix mat = new Matrix(3);
        mat.matrix[0][0] = Math.cos(angle);
        mat.matrix[0][1] = -Math.sin(angle);
        mat.matrix[1][0] = Math.sin(angle);
        mat.matrix[1][1] = Math.cos(angle);
        mat.matrix[2][2] = 1;

        return mat.times(mat, point);
    }

    public Point3 traslation(Point3 point, int tx, int ty) {
        Matrix mat = new Matrix(3);
        mat.matrix[0][0] = 1;
        mat.matrix[0][2] = tx;
        mat.matrix[1][1] = 1;
        mat.matrix[1][2] = ty;
        mat.matrix[2][2] = 1;

        return mat.times(mat, point);
    }

    public Point3 scaling(Point3 point, double sx, double sy) {
        Matrix mat = new Matrix(3);
        mat.matrix[0][0] = sx;
        mat.matrix[1][1] = sy;
        mat.matrix[2][2] = 1;

        return mat.times(mat, point);
    }

    public void ejercicio(String fileName) {
        try {
            Scanner scanner = new Scanner(new File("DrawFile/archivo.txt"));
            int numPoints = scanner.nextInt();
            points = new Point3[numPoints];
            base_points = new Point3[numPoints];
            for (int i = 0; i < numPoints; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();

                Point3 point = new Point3(x, y, 1);
                Point3 base_point = new Point3(x, y, 1);
                points[i] = point;
                base_points[i] = base_point;
            }
            int numLines = scanner.nextInt();
            for (int i = 1; i <= numLines; i++) {
                int indice1 = scanner.nextInt();
                int indice2 = scanner.nextInt();
                lineas.add(indice1);
                lineas.add(indice2);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Puntos");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new House());
        // Asignarle tamaño
        frame.setSize(WIDTH, HEIGHT);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);

        Thread repaintThread = new Thread(() -> {
            while (true) {
                frame.repaint();
                try {
                    Thread.sleep(FRAMES);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        repaintThread.start();
    }

}
