package com.omegawatch;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;

public class OmegaWatchService {
    public static void main(String[] args) {
        Path directory = Paths.get("src/main/resources/omega-monitor");
        Path stateFile = Paths.get("src/main/resources/omega-state/omega-state.dat");

        DirectoryState directoryState = new DirectoryState(directory, stateFile);

        try {
            // Load previous state
            Map<String, FileAttributes> previousState = directoryState.loadState();

            // Scan current state
            Map<String, FileAttributes> currentState = directoryState.scanDirectory();

            // Compare states
            System.out.println("Comparing states...");
            DirectoryComparator.compare(previousState, currentState);

            // Start real-time monitoring
            DirectoryMonitor monitor = new DirectoryMonitor(directory);
            new Thread(() -> {
                try {
                    monitor.startMonitoring();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            // Save state on shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println("Saving state...");
                    directoryState.saveState();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
