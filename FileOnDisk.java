package project4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is inherit from the File class
 * for calculating the total size of a file or directory (including all
 * subdirectories and files).
 * It offers methods to retrieve the total size of the file system entity
 * and a list of the largest files within a directory.
 * 
 * 
 * 
 * @author Olivia Yu
 *
 */
public class FileOnDisk extends File {

    private List<FileOnDisk> fileList = new ArrayList<>();
    public List<FileOnDisk> dirList = new ArrayList<>();
    String pathname;
    long size;

    /**
     * Constructs a new FileOnDisk instance with specified pathname and size. This
     * constructor is specificly
     * used for storing calculated sizes.
     * 
     * @param pathname the canonical path of the provided file
     * @param size     this file's size
     * 
     * @throws NullPointerException     if the given pathname is null
     * @throws IllegalArgumentException if the given size is less than 0
     */
    private FileOnDisk(String pathname, long size) throws NullPointerException {
        super(pathname);
        if (pathname == null)
            throw new NullPointerException("pathname can't be null");

        if (size < 0)
            throw new IllegalArgumentException("size can not be a negative number");
        this.pathname = pathname;
        this.size = size;
    }

    /**
     * Constructs a new FileOnDisk object with specified pathname.
     * 
     * @param pathname the canonical path of the provided file
     * 
     * @throws NullPointerException if the given pathname is null
     */
    public FileOnDisk(String pathname) throws NullPointerException {
        super(pathname);
        if (pathname == null)
            throw new NullPointerException("pathname can't be null");
        this.pathname = pathname;
    }

    /**
     * Returns the size of a stored fileOnDisk object.
     * 
     * @return size of the fileOnDisk object
     */
    public long getStoredSize() {
        return size;
    }

    /**
     * calculates and returns the total size of the fileOnDisk object.
     * It also adds the calculated size to an arraylist for further reference
     * 
     * @throws IOException if there are issues accessing the file
     * @return size of the fileOnDisk object
     */
    public long getTotalSize() throws IOException {
        size = exploreDir(pathname);
        FileOnDisk storeSize = new FileOnDisk(pathname, size);
        dirList.add(storeSize);
        return size;
    }

    /**
     * Calculates the total size of all the files and subdirectories stored in a
     * given FIleOnDisk
     * 
     * @param pathname the canonical path of the provided file
     * 
     * @throws IOException if there are issues accessing the file
     * @return total size of the fileOnDisk object
     */
    private long exploreDir(String pathname) throws IOException {
        File currentFile = new File(pathname);

        // checks the validity of the given pathname
        if (!currentFile.exists()) {
            throw new IllegalArgumentException(this.getCanonicalPath() + "does not exist");
        }
        // check whether the size of this FileOnDisk is already calculated. If it is,
        // return the stored size.
        for (FileOnDisk f : dirList) {

            if (f.getCanonicalPath().equals(currentFile.getCanonicalPath())) {
                return f.getStoredSize();
            }

        }

        // calculates the total size of the FileOnDisk recursively
        long totalSize = 0;

        try {
            // base case
            if (currentFile.isFile()) {
                // store the size of the file in fileList
                FileOnDisk fileRecord = new FileOnDisk(currentFile.getCanonicalPath(), currentFile.length());
                fileList.add(fileRecord);
                return totalSize += currentFile.length();
            }

            // recursive case
            else if (currentFile.isDirectory()) {

                File[] list = currentFile.listFiles();
                if (list != null && list.length != 0) {
                    for (File fi : list)
                        totalSize += exploreDir(fi.getCanonicalPath());

                }
                // store the size of the calculated directory in dirList
                FileOnDisk storeDir = new FileOnDisk(currentFile.getCanonicalPath(), totalSize);
                dirList.add(storeDir);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalSize;

    }

    /**
     * it uses comparator and sorts the lists in a specified order
     * 
     */
    private void sortFileList() {
        FileOnDiskComparatorBySize comparator = new FileOnDiskComparatorBySize();
        fileList.sort(comparator);
    }

    /**
     * sorts all files in the FileOnDisk object and return a given number of largest
     * files in a list
     * 
     * @param numOfFiles the number of the largest files wants to return
     * 
     * @throws IOException              if there are issues accessing the file
     * @throws IllegalArgumentException if the given parameter is negative
     * @return a given number of largest files in a list, or null if this FileOnDisk
     *         object is a file
     */
    public List<FileOnDisk> getLargestFiles(int numOfFiles) throws IOException {
        // creates a new arraylist to store the returned largest files
        List<FileOnDisk> sortedFileList = new ArrayList<FileOnDisk>();

        if (numOfFiles < 0)
            throw new IllegalArgumentException("number of files can not be negative");

        exploreDir(this.getCanonicalPath());

        if (this.isFile())
            return null;

        else if (this.isDirectory()) {

            sortFileList();
            // add the returned files in the new arraylist
            if (numOfFiles >= fileList.size()) {
                sortedFileList.addAll(fileList);
            } else {
                sortedFileList.addAll(fileList.subList(0, numOfFiles));
            }
        }
        return sortedFileList;

    }

    /**
     * overriding the original toString to format FileOnDisk object so its size and
     * canonical path will be printed in a prefered format
     * 
     * 
     * @return a string that gives a proper format for the FileOnDisl
     */
    @Override
    public String toString() {
        String finalString = null;
        long totalSize;
        double calculatesSize;

        try {
            totalSize = getTotalSize();
            if (totalSize <= 1024) {
                calculatesSize = totalSize;
                finalString = String.format("%8.2f bytes  %s", calculatesSize, this.getCanonicalPath());
            }

            else if (totalSize > 1024 && totalSize < (1024 * 1024)) {
                calculatesSize = totalSize / 1024.0;
                finalString = String.format("%8.2f KB     %s", calculatesSize, this.getCanonicalPath());
            }

            else if (totalSize >= (1024 * 1024) && totalSize < (1024.0 * 1024.0 * 1024.0)) {
                calculatesSize = totalSize / 1024.0 / 1024.0;
                finalString = String.format("%8.2f MB     %s", calculatesSize, this.getCanonicalPath());
            }

            else if (totalSize >= (1024.0 * 1024.0 * 1024.0)) {
                calculatesSize = totalSize / 1024.0 / 1024.0 / 1024.0;
                finalString = String.format("%8.2f GB     %s", calculatesSize, this.getCanonicalPath());
            }
            // catches any issues of accessing the file
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalString;
    }
}
