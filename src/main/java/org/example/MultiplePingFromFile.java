package org.example;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/// -->> Using redirect input and fping
public class MultiplePingFromFile {

    public static void main(String[] args) {

        Pattern pattern2 = Pattern.compile("3/3");
        String csvFile = "/home/raj/RAJ_MEHTA/LEARNING/PingProgramming/src/main/resources/hosts.csv";
        ProcessBuilder pb2 = new ProcessBuilder("fping", "-s", "-c", "3");
        pb2.redirectErrorStream(true);
        pb2.redirectInput(new File(csvFile)); // Redirect file input
        try {
            Process p2 = pb2.start();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            String line;
            while ((line = br2.readLine()) != null) {
                //System.out.println(line);

                Matcher m = pattern2.matcher(line);
                if (line.contains("3/3"))
                    System.out.println("host ip: " + line.substring(0, 12) + " is " + " reachable");
                else if (line.matches(".*3/[012].*"))
                    System.out.println("host ip: " + line.substring(0, 12) + " is unreachable");
            }
            br2.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }


}