/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author STAR-DUST
 */
public class DES {
    
    private static final String FILE_NAME = "blob_file.txt";
    private static final String EMPTY_STRING = "";
    private static final String BINARY_8_BIT_FORMAT = "%08d";
    private static final int SEQUENCE_LENGTH = 8;
    private static int FILE_CONTENT_LENGTH;

    public static void main(String[] args) throws IOException {
        String key = generate56BitKey(generateSequence(readFile(DES.FILE_NAME), generateBitPosition()));
        System.out.println(key);
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
        
        String binaryString = DES.EMPTY_STRING;
        
        for(int i = 0; i < string.length(); i++) {
            binaryString += spliceLastCharacter(convertTo8bitString(string.charAt(i)));
        }
        
        return binaryString;
    }
    
    private static int getFileContentLength() {
        return DES.FILE_CONTENT_LENGTH;
    }
    
    private static void setFileContentLength(final int value) {
        DES.FILE_CONTENT_LENGTH = value;
    }
}