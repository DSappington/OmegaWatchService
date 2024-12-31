package com.omegawatch;

import java.io.IOException;
import java.nio.file.*;

public class DirectoryMonitor {
    private final Path directory;

    public DirectoryMonitor(Path directory) {
        this.directory = directory;
    }

    public void startMonitoring() throws IOException, InterruptedException {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        directory.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

        System.out.println("Monitoring directory: " + directory);

        while (true) {
            WatchKey key = watcher.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                Path path = (Path) event.context();
                System.out.println("Event: " + kind + " on file: " + directory.resolve(path));
            }

            if (!key.reset()) {
                break;
            }
        }
    }
}
