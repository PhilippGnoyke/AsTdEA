package org.astdea.io.input;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Comparator;

public final class FilenameComparator implements Comparator<String>
{
    private static final int EQUAL = 0;
    private static final int A_IS_LATER = 1;
    private static final int B_IS_LATER = -1;

    private static final String TEXT_NUM_SPLITTER = "(?<=\\d)(?=\\D)";

    @Override
    public final int compare(String fileA, String fileB)
    {
        String versionA = extractVersionNumber(fileA);
        String versionB = extractVersionNumber(fileB);

        // Scenario: total equality (should not happen for correct input)
        if (versionA.equals(versionB))
        {
            return EQUAL;
        }

        String[] partsA = versionA.split("\\.");
        String[] partsB = versionB.split("\\.");

        int partCountA = partsA.length;
        int partCountB = partsB.length;

        int maxParts = Math.max(partCountA, partCountB);
        for (int i = 0; i < maxParts; i++)
        {
            // Scenario: one version has more remaining parts and the other none
            // -> assume that more parts imply a later version.
            if (i >= partCountA) {return B_IS_LATER;}
            if (i >= partCountB) {return A_IS_LATER;}

            String[] subpartsA = partsA[i].split(TEXT_NUM_SPLITTER);
            String[] subpartsB = partsB[i].split(TEXT_NUM_SPLITTER);

            String firstSubpartA = subpartsA[0];
            String firstSubpartB = subpartsB[0];

            if (NumberUtils.isParsable(firstSubpartA))
            {
                if (NumberUtils.isParsable(firstSubpartB))
                {
                    // Scenario: one version has a higher number at the current part -> it is a later version.
                    // If both numbers are the same, continue.
                    int numComp = Integer.compare(Integer.parseInt(firstSubpartA), Integer.parseInt(firstSubpartB));
                    if (numComp != 0) {return numComp;}
                }
                // Scenario: one version has a number and the other a letter at the current part
                // -> assume that letters imply an earlier version (e.g. alpha)
                else {return A_IS_LATER;}
            }
            // Scenario: one version has a number and the other a letter at the current part
            // -> assume that letters imply an earlier version (e.g. alpha)
            else if (NumberUtils.isParsable(firstSubpartB))
            {
                return B_IS_LATER;
            }
            else
            {
                // Scenario: both versions only have text -> compare text
                // If both texts are the same, continue.
                int textComp = firstSubpartA.compareTo(firstSubpartB);
                if (textComp != 0) {return textComp;}
            }

            if (partCountA != partCountB)
            {
                // Scenario: both were equal so far, but one has remaining parts and the other not
                // -> assume that more parts imply a later version.
                if (i + 1 >= partCountA) {return B_IS_LATER;}
                if (i + 1 >= partCountB) {return A_IS_LATER;}
            }

            if (subpartsA.length > 1)
            {
                if (subpartsB.length > 1)
                {
                    // Scenario: both versions have the same number plus text -> compare text
                    // If both texts are the same, continue.
                    int textComp = subpartsA[1].compareTo(subpartsB[1]);
                    if (textComp != 0) {return textComp;}
                }
                // Scenario: one version has additional letters at the end and the other not
                // -> assume that letters imply an earlier version (e.g. alpha)
                else {return B_IS_LATER;}
            }
            // Scenario: one version has additional letters at the end and the other not
            // -> assume that letters imply an earlier version (e.g. alpha)
            else if (subpartsB.length > 1) {return A_IS_LATER;}
        }
        return EQUAL;
    }

    /*
     * Can extract version numbers from file names like:
     * project1.jar
     * project1.1.jar
     * project1.1.1.jar (etc.)
     * project1.1a.jar
     * project-1a.jar (hyphen required for version numbers with letters and without dots)
     */
    private static String extractVersionNumber(String input)
    {
        final int NOT_FOUND = -1;
        int lastDotInd = input.lastIndexOf(".");
        int firstIndOfVerNum;
        if (lastDotInd == NOT_FOUND)
        {
            lastDotInd = input.length();
            firstIndOfVerNum = lastDotInd;
        }
        else {firstIndOfVerNum = input.indexOf(".");}

        while (Character.isDigit(input.charAt(firstIndOfVerNum - 1)))
        {
            firstIndOfVerNum--;
        }
        String sub = input.substring(firstIndOfVerNum, lastDotInd);
        if (sub.length() > 0)
        {
            return sub;
        }
        else
        {
            String subOtherPart = input.substring(0, firstIndOfVerNum - 1);
            return input.substring(subOtherPart.lastIndexOf("-") + 1, lastDotInd);
        }
    }
}