package companylist.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Combining companylist1-3 csv files into one file.
 * 
 * @author Dickfoong
 *
 */
public class CompanyListGenerator {

  private static void error(String message) {
    System.out.println("Error: " + message);
  }

  /**
   * Main method for running the generator.
   * @param args The console input.
   */
  public static void main(String[] args) {

    int fileNum = 1;
    int lineNum;
    Scanner inputScanner;
    File inputFile;
    File outputFile = new File("csv/companylist.csv");
    FileWriter writer;
    String inputLine;
    String companyName;
    String[] lineParts;
    List<String> companyNameList = new ArrayList<String>();
    
    try {
      outputFile.createNewFile();
      writer = new FileWriter(outputFile);
      while (fileNum <= 3) {
        inputFile = new File("/Users/Dickfoong/Downloads/companylist-" + fileNum + ".csv");
        inputScanner = new Scanner(inputFile);
        lineNum = 0;
        while (inputScanner.hasNextLine()) {
          if (lineNum > 0) {
            inputLine = inputScanner.nextLine();
            lineParts = inputLine.split(",");
            companyName = "\'" + lineParts[1].replaceAll("\"","") + "\'";
            
            if (!companyNameList.contains(companyName)) {
              companyNameList.add(companyName);
            }
          } else {
            inputScanner.nextLine();
            lineNum++;
          }
          
        }
        inputScanner.close();
        fileNum++;
      }
      
      int i = 0;
      while (i < companyNameList.size()) {
        writer.write(companyNameList.get(i) + "\n");
        i++;
      }
      
      writer.close();
      
    } catch (IOException io) {
      error("Output file cannot be created.");
    }
    
  }
}
