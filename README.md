# Disk-Usage-Analyzer
## Introduction
This Java program utilizes recursion to explore directory trees on a user's computer. It calculates the total size of all files and sub-directories within a given directory and displays a list of the largest files.

## Features
- [✅] **Total Size Calculation**: Computes the total size of a specified directory, including all its files and sub-directories.
- [✅] **Largest Files Listing**: Lists the top n largest files in the specified directory, showing their sizes and paths.
- [✅] **Custom File Handling**: Uses the FileOnDisk class, an extension of Java's File class, for enhanced file operations.
- [✅] **Flexible Output**:  Users can specify the number of largest files to display, with a default value set to 20.

# Usage

## Prerequisites
* Java Runtime Environment (JRE) or Java Development Kit (JDK).

## Running the Program
* Compile the Java files in the `project4` package.
* Run the `DiskUsage` class, providing the directory to explore as the first command line argument, and optionally, the number of largest files to list as the second argument.

### Example:
```bash
java project4.DiskUsage /path/to/directory 7
```

## Command Line Arguments
* `args[0]`: Path to the directory to explore.
* `args[1]`: (Optional) Number of largest files to display (default is 20).

## Error Handling
* The program terminates with an error message if no directory is specified or if the specified directory does not exist.
* If the second argument is not a valid positive number, it defaults to 20.

# Structure

## `DiskUsage` Class
The main class of the program, handling command line arguments and orchestrating the directory exploration.

## `FileOnDisk` Class: 
* Inherits from the Java File class.
* Stores additional information like canonical path and file size.
* Capable of calculating total sizes and identifying largest files within a directory.
* Overrides `toString` for formatted output of file sizes and paths.

## `FileOnDiskComparatorBySize` Class
Implements `Comparator<FileOnDisk>` for sorting files based on size and path names.

## Output
* Total size of the specified directory.
* List of the largest files within the directory, including their sizes and canonical paths.
