import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ReadTextFile {
    public static void readFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            int numPoints = scanner.nextInt();
            for(int i = 1; i <= numPoints; i++ ) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                System.out.println("Point " + i + ": (" + x + ", " + y + ")");
            }
            int numLines = scanner.nextInt();
            for(int i = 1; i <= numLines; i++ ) {
                int indice1 = scanner.nextInt();
                int indice2 = scanner.nextInt();
                System.out.println("Desde: " + indice1 + " hasta: " + indice2);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args) {
        readFile("archivo.txt");
    }

}