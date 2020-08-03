package utils;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

public class Logger {
    private static final String FILENAME = "logger.txt";

    public Logger() {}

    public static void log (String username, boolean success) {
        try
             (FileWriter fileWriter = new FileWriter(FILENAME, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.println(ZonedDateTime.now() + " " + username + (success ? " Success" : " Failed"));
        } catch (IOException e) {
            System.out.println("Logger Error: " + e.getMessage());
        }
    }
}
