# max.storozhuk.encrypt
## Overview
This project focuses on implementing a simple file encryption and decryption system using the Caesar cipher and a brute-force with frequency analysis. The system provides both a command-line interface (CLI) and a graphical user interface (GUI) for user interaction.

## Achievements
- Implemented file encryption and decryption using the Caesar cipher algorithm.
- Integrated a brute-force attack with frequency analysis for decrypting files without a known key.
- Developed a command-line interface (CLI) for users who prefer text-based interactions.
- Designed a graphical user interface (GUI) using JavaFX for a more user-friendly experience.

## Project Features
- **CLI Operations:** Supports encryption, decryption, and brute-force decryption operations through the command line.
- **GUI Interface:** Provides a user-friendly graphical interface for performing file operations.
- **Dynamic UI Elements:** The GUI dynamically adjusts its components based on the selected operation (ENCRYPT, DECRYPT, BRUTE_FORCE).
- **File Browsing:** Users can browse and select files easily using the GUI.

## Noteworthy Implementations
- **Caesar Cipher:** The core encryption and decryption mechanism use the Caesar cipher with customizable key values.
- **Brute-Force Attack:** Implemented a brute-force attack with frequency analysis to decrypt files without a known key.
- **JavaFX GUI:** Utilized JavaFX to create an interactive and responsive graphical user interface.

## Running the Application
*OPERATION:* [ENCRYPT, DECRYPT, BRUTE_FORCE]
- **Option 1:** Command-Line Arguments.
     - **a) Using Edit Configurations:** 
       - Open the project in your preferred IDE.
       - Navigate to the "Edit Configurations" settings.
       - JAR application: max.storozhuk.encrypt-1.0-jar-with-dependencies.jar.
       - Program Arguments: OPERATION "foldername/textFile1.txt" key (for ENCRYPT/DECRYPT) and BRUTE_FORCE "foldername/textFile1.txt" "OPTIONAL/path/to/the/file/for/text/frequency/analysis".
     - **b) Using Terminal:**
       - Open a terminal and navigate to the project's target directory.
       - Run the following command: java -jar "C:\folder\max.storozhuk.encrypt\target\max.storozhuk.encrypt-1.0-jar-with-dependencies.jar" OPERATION "folder name/textFile1.txt" 20
- **Option 2 and 3:** Run "max.storozhuk.encrypt-1.0-jar-with-dependencies.jar" in your preferred IDE
  - Choose Choose the operating mode:
    - 1 for CLI (Command Line Interface)
    - 2 for GUI (Graphical User Interface)
    
For CLI:
Enter the command (ENCRYPT, DECRYPT, BRUTE_FORCE).
Provide the file path and key or path to the file for frequency analysis (if required).

For GUI:
Launch the GUI for a more interactive file operation experience.

***Note:*** **If no file path for frequency analysis is provided, the default letter probabilities in percentages taken from public sources will be used.**
