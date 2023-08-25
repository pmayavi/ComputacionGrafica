import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Draw extends JPanel {
  public static final int WIDTH = 640;
  public static final int HEIGHT = 480;

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.blue);
    drawAxis(g);
    ejercicio("archivo.txt", g);
  }

  public void drawAxis(Graphics g) {
    g.setColor(Color.red);
    myDrawLine(g, -100, 0, 100, 0);
    g.setColor(Color.green);
    myDrawLine(g, 0, -100, 0, 100);
  }

  public void myDrawPoint(Graphics g, int x, int y) {
    int xj = x + WIDTH / 2;
    int yj = HEIGHT / 2 - y;
    g.drawLine(xj, yj, xj, yj);
  }

  public void myDrawLine(Graphics g, int x1, int y1, int x2, int y2) {
    int xj1 = x1 + WIDTH / 2;
    int yj1 = HEIGHT / 2 - y1;
    int xj2 = x2 + WIDTH / 2;
    int yj2 = HEIGHT / 2 - y2;
    g.drawLine(xj1, yj1, xj2, yj2);
  }

  public void ejercicio(String fileName, Graphics g) {
    g.setColor(Color.blue);
    List<Integer> coordenadas = new ArrayList<>();
    List<Integer> lineas = new ArrayList<>();
    try {
      Scanner scanner = new Scanner(new File("archivo.txt"));
      int numPoints = scanner.nextInt();
      for (int i = 1; i <= numPoints; i++) {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        coordenadas.add(x);
        coordenadas.add(y);
      }
      int numLines = scanner.nextInt();
      for (int i = 1; i <= numLines; i++) {
        int indice1 = scanner.nextInt();
        int indice2 = scanner.nextInt();
        lineas.add(indice1 * 2);
        lineas.add(indice2 * 2);
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    for (int i = 0; i < lineas.size(); i += 2) {
      myDrawLine(g, coordenadas.get(lineas.get(i)), coordenadas.get(lineas.get(i) + 1),
          coordenadas.get(lineas.get(i + 1)), coordenadas.get(lineas.get(i + 1) + 1));
    }
  }

  public static void main(String[] args) {
    // Crear un nuevo Frame
    JFrame frame = new JFrame("Puntos");
    // Al cerrar el frame, termina la ejecución de este programa
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Agregar un JPanel que se llama Points (esta clase)
    frame.add(new Draw());
    // Asignarle tamaño
    frame.setSize(WIDTH, HEIGHT);
    // Poner el frame en el centro de la pantalla
    frame.setLocationRelativeTo(null);
    // Mostrar el frame
    frame.setVisible(true);
  }

}
