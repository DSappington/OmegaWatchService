# Omega Watch Service

Omega Watch Service is a modular and persistent file monitoring service for Java. It uses the `WatchService` API to detect file changes in real-time and persists directory states across application restarts to detect changes even when the application isn't running.

## Features
- **Real-Time Monitoring**: Tracks file creation, modification, and deletion events in a specified directory.
- **Persistent State**: Saves the directory's state to disk, enabling detection of changes across restarts.
- **Modular Design**: Cleanly organized code for easy customization and extension.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/OmegaWatchService.git
   cd OmegaWatchService
