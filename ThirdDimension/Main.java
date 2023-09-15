package ThirdDimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import geometry.PolygonObject;
import geometry.ObjectTransformation;

import java.awt.Color;
import java.awt.Graphics;

public class Main extends JPanel
    implements KeyListener {

  static final int WIDTH = 1400;
  static final int HEIGHT = 800;
  public double u = 0, v = 0, n = 0;
  public double cameraTy = 0, cameraTx = 0;

  Graphics g;

  PolygonObject po;
  PolygonObject axis;
  PolygonObject floor;

  public Main() {
    setFocusable(true);
    requestFocusInWindow();
    addKeyListener(this);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    this.g = g;
    axis.transformObject(u, v, n, cameraTx, cameraTy);
    axis.projectObject();
    axis.drawColor(g);

    g.setColor(Color.BLACK);
    po.transformObject(u, v, n, cameraTx, cameraTy);
    po.projectObject();
    po.draw();

  }

  public void drawOneLine(int x1, int y1, int x2, int y2) {
    x1 = x1 + WIDTH / 2;
    y1 = HEIGHT / 2 - y1;
    x2 = x2 + WIDTH / 2;
    y2 = HEIGHT / 2 - y2;
    g.drawLine(x1, y1, x2, y2);
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    switch (key) {
      case KeyEvent.VK_D:
        po.ot.dx += ObjectTransformation.DELTA_TRANSL;
        break;
      case KeyEvent.VK_A:
        po.ot.dx -= ObjectTransformation.DELTA_TRANSL;
        break;
      case KeyEvent.VK_W:
        po.ot.dy += ObjectTransformation.DELTA_TRANSL;
        break;
      case KeyEvent.VK_S:
        po.ot.dy -= ObjectTransformation.DELTA_TRANSL;
        break;
      case KeyEvent.VK_Q:
        po.ot.sx += ObjectTransformation.DELTA_SCAL;
        po.ot.sy += ObjectTransformation.DELTA_SCAL;
        po.ot.sz += ObjectTransformation.DELTA_SCAL;
        break;
      case KeyEvent.VK_E:
        po.ot.sx -= ObjectTransformation.DELTA_SCAL;
        po.ot.sy -= ObjectTransformation.DELTA_SCAL;
        po.ot.sz -= ObjectTransformation.DELTA_SCAL;
        break;
      case KeyEvent.VK_R:
        po.ot.thetaX += ObjectTransformation.DELTA_ROT;
        break;
      case KeyEvent.VK_F:
        po.ot.thetaX -= ObjectTransformation.DELTA_ROT;
        break;
      case KeyEvent.VK_T:
        po.ot.thetaY += ObjectTransformation.DELTA_ROT;
        break;
      case KeyEvent.VK_G:
        po.ot.thetaY -= ObjectTransformation.DELTA_ROT;
        break;
      case KeyEvent.VK_Y:
        po.ot.thetaZ += ObjectTransformation.DELTA_ROT;
        break;
      case KeyEvent.VK_H:
        po.ot.thetaZ -= ObjectTransformation.DELTA_ROT;
        break;
      case KeyEvent.VK_UP:
        n += ObjectTransformation.DELTA_TRANSL;
        break;
      case KeyEvent.VK_DOWN:
        n -= ObjectTransformation.DELTA_TRANSL;
        break;
      case KeyEvent.VK_LEFT:
        u += ObjectTransformation.DELTA_TRANSL;
        break;
      case KeyEvent.VK_RIGHT:
        u -= ObjectTransformation.DELTA_TRANSL;
        break;
      case KeyEvent.VK_CONTROL:
        v += ObjectTransformation.DELTA_TRANSL;
        break;
      case KeyEvent.VK_SPACE:
        v -= ObjectTransformation.DELTA_TRANSL;
        break;
      case KeyEvent.VK_1:
        cameraTx += ObjectTransformation.DELTA_ROT;
        break;
      case KeyEvent.VK_2:
        cameraTx -= ObjectTransformation.DELTA_ROT;
        break;
      case KeyEvent.VK_3:
        cameraTy += ObjectTransformation.DELTA_ROT;
        u -= ObjectTransformation.DELTA_TRANSL;
        n += ObjectTransformation.DELTA_TRANSL;
        break;
      case KeyEvent.VK_4:
        cameraTy -= ObjectTransformation.DELTA_ROT;
        u += ObjectTransformation.DELTA_TRANSL;
        n -= ObjectTransformation.DELTA_TRANSL;
        break;
      case KeyEvent.VK_Z:
        po.resetVertices();
        break;
    }

    repaint();

  }// Camera es un punto de fuga, todo se mueve para que 0.0.0 este en ese punto

  @Override
  public void keyReleased(KeyEvent e) {
  }

  public static void main(String[] args) {
    // Crear un nuevo Frame (Ventana)
    JFrame frame = new JFrame("Transformaciones 3D");
    // Al cerrar el frame, termina la ejecución de este programa
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Agregar un JPanel que se llama Main (esta clase)
    Main main = new Main();
    // Create a PolygonObject
    // Reading takes a long time. Read the file before adding the
    // JPanel to the JFrame.
    main.po = new PolygonObject();
    main.po.readObject("casita3D.txt");
    main.po.setCanvas(main);

    main.axis = new PolygonObject();
    main.axis.readObject("axis3D.txt");
    main.axis.setCanvas(main);
    // En true para que el objeto rote y se escale en torno a sí mismo
    // math.TranslScalRot4x4.CENTER_TRANFORMS = true;
    // Agregar el JPanel al frame
    frame.add(main);
    // Asignarle tamaño
    frame.setSize(WIDTH, HEIGHT);
    // Poner el frame en el centro de la pantalla
    frame.setLocationRelativeTo(null);
    // Mostrar el frame
    frame.setVisible(true);

  }
}
