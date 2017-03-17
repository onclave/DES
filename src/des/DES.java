/*
 * This code file is free to be used and modified as required
 */
package des;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Debabrata Acharya
 */
public class DES {
    
    private static final String FILE_NAME = "blob_file.txt";
    private static final String EMPTY_STRING = "";
    private static final String BINARY_8_BIT_FORMAT = "%08d";
    private static final int SEQUENCE_LENGTH = 8;
    private static int FILE_CONTENT_LENGTH;
    
    private static final int[][] PERMUTATION_COMBINATION_ONE = new int[][]{
        {57, 49, 41, 33, 25, 17, 9},
        {1, 58, 50, 42, 34, 26, 18},
        {10, 2, 59, 51, 43, 35, 27},
        {19, 11, 3, 60, 52, 44, 36},
        {63, 55, 47, 39, 31, 23, 15},
        {7, 62, 54, 46, 38, 30, 22},
        {14, 6, 61, 53, 45, 37, 29},
        {21, 13, 5, 28, 20, 12, 4},
    };

    public static void main(String[] args) throws IOException {
        
        String KEY56bit = generate56BitKey(generateSequence(readFile(DES.FILE_NAME), generateBitPosition()));
        System.out.println(KEY56bit);
    }
    
    private static String readFile(final String fileName) throws FileNotFoundException, IOException {
        
        String fileContent = DES.EMPTY_STRING;
        String readLine;
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        while((readLine = bufferedReader.readLine()) != null) {
            fileContent += readLine;
        }
        
        setFileContentLength(fileContent.length());
        
        return fileContent;
    }
    
    private static int getFileContentLength() {
        return DES.FILE_CONTENT_LENGTH;
    }
    
    private static void setFileContentLength(final int value) {
        DES.FILE_CONTENT_LENGTH = value;
    }
    
    private static int generateBitPosition() {
        return ThreadLocalRandom.current().nextInt(0, (getFileContentLength() - DES.SEQUENCE_LENGTH));
    }
    
    private static String generateSequence(final String string, final int startPosition) {
        return string.substring(startPosition, (startPosition + DES.SEQUENCE_LENGTH));
    }
    
    private static String spliceLastCharacter(String string) {
        return string.substring(0, string.length() - 1);
    }
    
    private static String convertTo8bitString(final char character) {
        return String.format(DES.BINARY_8_BIT_FORMAT, Integer.valueOf(Integer.toBinaryString(character)));
    }
    
    private static String generate56BitKey(final String string) {
        
        String KEY64bit = DES.EMPTY_STRING;
        String KEYplus56bit = DES.EMPTY_STRING;
        char[] KEYplus56bitArray = new char[56];
        int position = 0;
        
        for(int i = 0; i < string.length(); i++) {
            KEY64bit += convertTo8bitString(string.charAt(i));
        }
        
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 7; j++) {
                KEYplus56bitArray[position++] = KEY64bit.charAt(DES.PERMUTATION_COMBINATION_ONE[i][j]);
            }
        }
        
        for(char value : KEYplus56bitArray) {
            KEYplus56bit += value;
        }
        
        return KEYplus56bit;
    }
}