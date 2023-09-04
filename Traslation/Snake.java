package Traslation;

// Pablo Maya Villegas
import Math.Point3;
import Math.Matrix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.util.Random;

public class Snake extends JPanel implements KeyListener {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 360;
    private static final int BORDER = 20;
    private static final int SIZE = 10;
    private static final int SPEED = 10;
    private static final int FRAMES = 100;
    private static final int STARTER_SIZE = 10;
    Random random;
    boolean running;
    Point3 points[];
    JFrame frame;
    Ellipse2D food;
    int tx;
    int ty;

    public Snake(JFrame f) {
        running = true;
        random = new Random();
        tx = 10;
        ty = 0;
        frame = f;
        points = new Point3[STARTER_SIZE];
        points[0] = new Point3(STARTER_SIZE * SIZE, 50, 1);
        for (int i = 1; i < STARTER_SIZE; i++) {
            points[i] = new Point3(points[i - 1].x - SIZE, 50, 1);
        }
        updateFood();
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addKeyListener(this);

        Thread repaintThread = new Thread(() -> {
            thread();
        });
        repaintThread.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        // System.out.println("Key pressed");
        if (tecla == KeyEvent.VK_W) {
            if (ty <= 0) {
                tx = 0;
                ty = -SPEED;
            }
        } else if (tecla == KeyEvent.VK_S) {
            if (ty >= 0) {
                tx = 0;
                ty = SPEED;
            }
        } else if (tecla == KeyEvent.VK_D) {
            if (tx >= 0) {
                tx = SPEED;
                ty = 0;
            }
        } else if (tecla == KeyEvent.VK_A) {
            if (tx <= 0) {
                tx = -SPEED;
                ty = 0;
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
        g.setColor(Color.red);
        for (int i = 0; i < points.length; i += 1) {
            g.fillRect((int) points[i].x, (int) points[i].y, SIZE, SIZE);
            g.setColor(Color.black);
        }
        g.setColor(Color.blue);
        Graphics2D g2d = (Graphics2D) g;
        g2d.fill(food);
        g2d.draw(food);
        g2d.drawRect(1, 1, WIDTH - BORDER, HEIGHT - BORDER * 2);
    }

    public void thread() {
        while (running) {
            points = fullTraslation(points, tx, ty);
            frame.repaint();
            collision();
            try {
                Thread.sleep(FRAMES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Point3[] fullTraslation(Point3[] points, int tx, int ty) {
        Matrix mat = new Matrix(3);
        mat.matrix[0][0] = 1;
        mat.matrix[0][2] = tx;
        mat.matrix[1][1] = 1;
        mat.matrix[1][2] = ty;
        mat.matrix[2][2] = 1;

        for (int i = points.length - 1; i > 0; i--) {
            points[i] = new Point3(points[i - 1].x, points[i - 1].y, points[i - 1].w);
        }

        points[0] = mat.times(mat, points[0]);
        return points;
    }

    public void collision() {
        for (Point3 point3 : points) {
            int count = 0;
            // Out of bounds
            if (point3.x > WIDTH - BORDER * 1.5 || point3.x < 0 || point3.y > HEIGHT - BORDER * 2.5 || point3.y < 0) {
                running = false;
                break;
            }
            // Itself
            for (int i = 0; i < points.length; i++) {
                if (point3.x == points[i].x && point3.y == points[i].y)
                    count++;
            }
            if (count > 1) {
                running = false;
                break;
            }
            // Food
            if (food.getX() == point3.x && food.getY() == point3.y) {
                updateFood();
                sizeUp();
                break;
            }
        }
    }

    public void updateFood() {
        int randx = random.nextInt((int) (WIDTH - BORDER * 1.5) / SIZE) * SIZE;
        int randy = random.nextInt((int) (HEIGHT - BORDER * 2.5) / SIZE) * SIZE;
        for (int i = 0; i < points.length; i++) {
            if (randx == points[i].x && randy == points[i].y) {
                randx = random.nextInt((int) (WIDTH - BORDER) / SIZE) * SIZE;
                randy = random.nextInt((int) (HEIGHT - BORDER * 2) / SIZE) * SIZE;
                i = 0;
            }
        }
        food = new Ellipse2D.Double(randx, randy, SIZE, SIZE);
    }

    public void sizeUp() {
        points = Arrays.copyOf(points, points.length + 1);
        points[points.length - 1] = new Point3(
                points[points.length - 2].x,
                points[points.length - 2].y,
                points[points.length - 2].w);
    }

    public static void main(String[] args) {
        // Crear un nuevo Frame
        JFrame frame = new JFrame("Snake");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        frame.add(new Snake(frame));
        // Asignarle tamaño
        frame.setSize(WIDTH, HEIGHT);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }

}
