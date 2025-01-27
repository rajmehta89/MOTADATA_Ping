package org.example;

import java.net.InetAddress;

public class PingHost {
    public static void main(String[] args) {
        // Specify the host to check
        String host = "google.com";
        int timeout = 5000; // Timeout in milliseconds

        try {
            // Get the InetAddress of the host
            InetAddress inetAddress = InetAddress.getByName(host);

            System.out.println("Pinging host: " + host);

            // Check if the host is reachable
            if (inetAddress.isReachable(timeout)) {
                System.out.println("Host is reachable!");
            } else {
                System.out.println("Host is NOT reachable.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while checking host: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
