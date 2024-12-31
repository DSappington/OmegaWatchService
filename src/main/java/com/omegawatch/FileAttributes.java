package com.omegawatch;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class FileAttributes implements Serializable {
    private final long size;
    private final long lastModifiedTime;

    public FileAttributes(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        this.size = attrs.size();
        this.lastModifiedTime = attrs.lastModifiedTime().toMillis();
    }

    public long getSize() {
        return size;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FileAttributes other) {
            return size == other.size && lastModifiedTime == other.lastModifiedTime;
        }
        return false;
    }
}
