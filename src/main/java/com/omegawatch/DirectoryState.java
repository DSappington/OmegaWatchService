package com.omegawatch;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class DirectoryState {
    private final Path directory;
    private final Path stateFile;

    public DirectoryState(Path directory, Path stateFile) {
        this.directory = directory;
        this.stateFile = stateFile;
    }

    // Load saved state from a file
    public Map<String, FileAttributes> loadState() throws IOException {
        if (!Files.exists(stateFile)) {
            return new HashMap<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(stateFile))) {
            return (Map<String, FileAttributes>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Failed to read state file.", e);
        }
    }

    // Save current directory state to a file
    public void saveState() throws IOException {
        Map<String, FileAttributes> currentState = scanDirectory();
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(stateFile))) {
            oos.writeObject(currentState);
        }
    }

    // Scan the current state of the directory
    public Map<String, FileAttributes> scanDirectory() throws IOException {
        return Files.walk(directory)
                .filter(Files::isRegularFile)
                .collect(Collectors.toMap(
                        path -> directory.relativize(path).toString(),
                        path -> {
                            try {
                                return new FileAttributes(path);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ));
    }
}
