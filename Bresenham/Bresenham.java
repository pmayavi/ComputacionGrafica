import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class Bresenham extends JPanel {
    // Variables
    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 820;
    private static final int LINE_LENGTH = 400; // Longitud de lineas
    private static final int NUM_LINES = 1024; // Numero de lineas
    private static final int CIRCLE_FILL = 10; // Grosor del circulo exterior
    private static final float COLOR_HUE = 4.0f / NUM_LINES; // Veces que se repite el espectro del color
    private static final int ROTATION_SPEED = 10; // Velocidad que cambia el color

    // Constantes
    private static final int START_X = FRAME_WIDTH / 2;
    private static final int START_Y = FRAME_HEIGHT / 2;
    private static final int DIAMETER = LINE_LENGTH * 2;
    private static final int CIRCLE_X = (FRAME_WIDTH - DIAMETER) / 2;
    private static final int CIRCLE_Y = (FRAME_HEIGHT - DIAMETER) / 2;

    private float hue = 0;
    private boolean cicle = true;

    private Color getNextColor() {
        if (cicle)
            hue += COLOR_HUE;
        else
            hue -= COLOR_HUE;

        if (hue > 1 || hue <= 0)
            cicle = !cicle;
        return Color.getHSBColor(hue, 1.0f, 1.0f);
    }

    private void drawCircle(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        Ellipse2D.Double ovalo1 = new Ellipse2D.Double(CIRCLE_X - CIRCLE_FILL, CIRCLE_Y - CIRCLE_FILL,
                DIAMETER + CIRCLE_FILL * 2, DIAMETER + CIRCLE_FILL * 2);
        Ellipse2D.Double ovalo2 = new Ellipse2D.Double(CIRCLE_X, CIRCLE_Y, DIAMETER, DIAMETER);
        g2d.draw(ovalo1);
        g2d.fill(ovalo1);
        g2d.draw(ovalo2);
        g2d.setColor(Color.WHITE);
        g2d.fill(ovalo2);
    }

    public void drawLines(Graphics2D g2d) {
        double angle = 0;
        for (int i = 0; i < NUM_LINES; i++) {
            angle = i * (360.0 / NUM_LINES);
            double radians = Math.toRadians(angle);
            int endX = (int) (START_X + LINE_LENGTH * Math.cos(radians));
            int endY = (int) (START_Y + LINE_LENGTH * Math.sin(radians));

            Line2D.Double line = new Line2D.Double(START_X, START_Y, endX, endY);
            g2d.setColor(getNextColor());
            g2d.draw(line);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawCircle(g2d);
        drawLines(g2d);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lineas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Bresenham panel = new Bresenham();
        frame.add(panel);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Thread repaintThread = new Thread(() -> {
            while (true) {
                panel.repaint();
                try {
                    Thread.sleep(ROTATION_SPEED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        repaintThread.start();
    }
}
