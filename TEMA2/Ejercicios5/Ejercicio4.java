package TEMA2.Ejercicios5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*Write a program to search for a string inside a file text. It must print out
every line containing the string, indicating the line number. File existence
must be controlled.*/

public class Ejercicio4 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the string to search for: "); // we ask the word to look for
            String searchString = scanner.nextLine();

            System.out.print("Enter the file path: "); // we ask the file path where to look for the word
            String filePath = scanner.nextLine();

            // we read the file to look for the word
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line = reader.readLine();
                int lineNumber = 1;

                while (line != null) {
                    if (line.contains(searchString)) {
                        System.out.println("Line " + lineNumber + ": " + line);
                    }
                    lineNumber++;
                    line = reader.readLine();
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

    }
}
