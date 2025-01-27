package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PingCommand {
    public static void main(String[] args) {
        String host = "google.com";
        try {
            System.out.println("Pinging host: " + host);

            // Execute the system ping command
            Process process = Runtime.getRuntime().exec("ping -c 4 " + host);

            // Read the output of the ping command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Host is reachable!");
            } else {
                System.out.println("Host is NOT reachable.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
