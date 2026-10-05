package TEMA2.Ejercicios5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Ejercicio2 {
    public static void main(String[] args) {
       try(BufferedReader reader = new BufferedReader(new FileReader("text.txt"));
            BufferedReader reader2 = new BufferedReader(new FileReader("text2.txt"))) {
            
        FileWriter fr = new FileWriter("sorted.txt", false);
        String line = reader.readLine();
        String line2 = reader2.readLine();
        while (line != null && line2 != null) {
            if(line.compareTo(line2) < 0) {
                fr.write(line + "\n");
                line = reader.readLine();
            } else {
                fr.write(line2 + "\n");
                line2 = reader2.readLine();
            }
        }
        while (line != null) {
            fr.write(line + "\n");
            line = reader.readLine();
        }
        while (line2 != null) {
            fr.write(line2 + "\n");
            line2 = reader2.readLine();
        }
        fr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
