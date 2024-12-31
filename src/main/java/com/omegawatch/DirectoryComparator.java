package com.omegawatch;

import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.util.*;

public class DirectoryComparator {
    public static void compare(Map<String, FileAttributes> previousState, Map<String, FileAttributes> currentState) {
        Set<String> previousFiles = previousState.keySet();
        Set<String> currentFiles = currentState.keySet();

        // Detect added files
        currentFiles.stream()
                .filter(file -> !previousFiles.contains(file))
                .forEach(file -> {
                    OmegaFileUtils.printOmegaChange(StandardWatchEventKinds.ENTRY_CREATE, Paths.get(file));
                });

        // Detect deleted files
        previousFiles.stream()
                .filter(file -> !currentFiles.contains(file))
                .forEach(file -> {
                    OmegaFileUtils.printOmegaChange(StandardWatchEventKinds.ENTRY_DELETE, Paths.get(file));
                });

        // Detect modified files
        previousFiles.stream()
                .filter(currentFiles::contains)
                .filter(file -> !previousState.get(file).equals(currentState.get(file)))
                .forEach(file -> {
                    OmegaFileUtils.printOmegaChange(StandardWatchEventKinds.ENTRY_MODIFY, Paths.get(file));
                });
    }
}
