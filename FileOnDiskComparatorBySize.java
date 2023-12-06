package project4;

import java.util.Comparator;

public class FileOnDiskComparatorBySize implements Comparator<FileOnDisk> {

    /**
     * override the original compare method to compare based on file size. If two
     * files have the same size, compare by their path names using lexicographic
     * ordering
     * 
     * @param o1 the first FileOnDisk object to be compared
     * @param o2 the second FileOnDisk object to be compared
     * @throws NullPointerException if either o1 or o2 is null
     * 
     * @return a negative integer, zero, or a positive integer as the first argument
     *         is greater than, equal to, or smaller than the second.
     */
    @Override
    public int compare(FileOnDisk o1, FileOnDisk o2) {

        if (o1 == null || o2 == null)
            throw new NullPointerException();

        int val = -1;

        if (o1.getStoredSize() > o2.getStoredSize())
            val = -1;

        else if (o1.getStoredSize() < o2.getStoredSize())
            val = 1;

        else {
            val = o1.compareTo(o2);
        }

        return val;
    }

}
