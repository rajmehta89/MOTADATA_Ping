package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


///  --->>> Using Thread pool executor. Submit one by one ip from file to executor
public class MultiplePing {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(5));

        String csvFile = "/home/raj/RAJ_MEHTA/LEARNING/PingProgramming/src/main/resources/hosts.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String ip;
            while ((ip = br.readLine()) != null) {
                ip = ip.trim();
                if (!ip.isEmpty()) {
                    String finalIp = ip;
                    executorService.submit(() -> pingIP(finalIp));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }


    }
    public static void pingIP(String ip) {
        try {
            ProcessBuilder pb = new ProcessBuilder("fping", "-c", "3", ip);
            pb.redirectErrorStream(true);

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(("3/3"))) {
                    System.out.println("Host " + ip + " is reachable");
                } else if (line.matches(".*3/[012].*")) {
                    System.out.println("Host " + ip + " is not reachable");
                }
            }

            process.waitFor();
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
