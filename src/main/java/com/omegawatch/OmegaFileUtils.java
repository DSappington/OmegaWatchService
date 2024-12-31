package com.omegawatch;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class OmegaFileUtils {
    static void printOmegaChange(WatchEvent.Kind<?> kind, Path file){
        System.out.println("Event: " + kind + " on file: " + file);
    }
}
