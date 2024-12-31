package com.omegawatch;

import java.util.*;

public class DirectoryComparator {
    public static void compare(Map<String, FileAttributes> previousState, Map<String, FileAttributes> currentState) {
        Set<String> previousFiles = previousState.keySet();
        Set<String> currentFiles = currentState.keySet();

        // Detect added files
        currentFiles.stream()
                .filter(file -> !previousFiles.contains(file))
                .forEach(file -> System.out.println("Added: " + file));

        // Detect deleted files
        previousFiles.stream()
                .filter(file -> !currentFiles.contains(file))
                .forEach(file -> System.out.println("Deleted: " + file));

        // Detect modified files
        previousFiles.stream()
                .filter(currentFiles::contains)
                .filter(file -> !previousState.get(file).equals(currentState.get(file)))
                .forEach(file -> System.out.println("Modified: " + file));
    }
}
