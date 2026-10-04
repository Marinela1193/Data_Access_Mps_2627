import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio2 {
    public static void main(String args[]) throws IOException{

        System.out.println("Introduce el nombre del archivo a comprobar: ");
        String fileName;
        try (Scanner sc = new Scanner(System.in)) {
            fileName = sc.nextLine();
        }
        FileInputStream fIn = new FileInputStream(fileName);

        byte[] data = new byte[6];
        fIn.read(data);
        
        try {
            if((data[0] & 0xFF) == 0x42 && (data[1] & 0xFF) == 0x4D){
                System.out.println("El arivo es tipo BMP");
            } else if((data[0] & 0xFF) == 0x47 && (data[1] & 0xFF) == 0x49 && (data[2] & 0xFF) == 0x46 && (data[3] & 0xFF) == 0x38 && (data[4] & 0xFF) == 0x39 && (data[5] & 0xFF) == 0x61){
                System.out.println("El arivo es tipo GIF");
            } else if((data[0] & 0xFF) == 0x47 && (data[1] & 0xFF) == 0x49 && (data[2] & 0xFF) == 0x46 && (data[3] & 0xFF) == 0x38 && (data[4] & 0xFF) == 0x37 && (data[5] & 0xFF) == 0x61){
                System.out.println("El arivo es tipo GIF");
            } else if((data[0] & 0xFF) == 0x00 && (data[1] & 0xFF) == 0x00 && (data[2] & 0xFF) == 0x01 && (data[3] & 0xFF) == 0x00 ){
                System.out.println("El arivo es tipo ICO");
            } else if((data[0] & 0xFF) == 0xFF && (data[1] & 0xFF) == 0xD8 && (data[2] & 0xFF) == 0xFF ){
                System.out.println("El arivo es tipo JPEG");
            } else if((data[0] & 0xFF) == 0x89 && (data[1] & 0xFF) == 0x50 && (data[2] & 0xFF) == 0x4E && (data[3] & 0xFF) == 0x47 ){
                System.out.println("El arivo es tipo PNG");
            } else {
                System.out.println("El arivo no es de tipo compatible");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            fIn.close();
        }
        
    }
}
