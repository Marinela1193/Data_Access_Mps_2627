import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio3 {
     public static void main(String args[]) throws IOException{

        System.out.println("Introduce el nombre de la imágen que quieras saber su información: ");
        String fileName;

        try (Scanner sc = new Scanner(System.in)) {
            fileName = sc.nextLine();
        }
        FileInputStream fIn = new FileInputStream(fileName);

        byte[] data = new byte[30];
        fIn.read(data);
        int size;
        int height;
        int width;
        int bits;
        
        try {
            if((data[0] & 0xFF) != 0x42 || (data[1] & 0xFF) != 0x4D){
                System.out.println("El arivo no es tipo BMP, intentalo de nuevo");
            } else {
                size = (data[2] & 0xFF) | ((data[3] & 0xFF) << 8) | ((data[4] & 0xFF) << 16) | ((data[5] & 0xFF) << 24);
                System.out.println("La imágen tiene un tamaño de: " + size);
                height = (data[22] & 0xFF) | ((data[23] & 0xFF) << 8) | ((data[24] & 0xFF) << 16) | ((data[25] & 0xFF) << 24);
                System.out.println("La altura de la imágen es de: " + height);
                width = (data[18] & 0xFF) | ((data[19] & 0xFF) << 8) | ((data[20] & 0xFF) << 16) | ((data[21] & 0xFF) << 24);
                System.out.println("El ancho de la imágen es de: " + width);
                bits = (data[28] & 0xFF) | ((data[29] & 0xFF) << 8);
                System.out.println("La imágen tiene un número de bits: " + bits + " por pixel");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            fIn.close();
        }
    }
}

