import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ejercicio1 {
    public static void main(String args[]) throws IOException {
        FileInputStream fIn = null;
        FileOutputStream fOut = null;
        try {
            fIn = new FileInputStream("D:\\testin.txt");
            fOut = new FileOutputStream("D:\\testout.txt");
            
            byte[] data = new byte[128];
            int bytesRead;
            while ((bytesRead = fIn.read(data)) != -1) {
                fOut.write(data, 0, bytesRead);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            fIn.close();
            fOut.close();
        }
    }
}
