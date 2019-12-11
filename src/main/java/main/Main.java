package main;

import java.io.IOException;

import checker.CheckerError;
import checker.CheckerException;
import checker.CheckerReturn;
import checker.IChecker;
import checker.MiLoGChecker;
import data.TimeSheet;
import io.FileController;
import io.IGenerator;
import io.LatexGenerator;
import main.UserInput.Request;
import parser.ParseException;
import parser.Parser;

public class Main {
    /**
     * @param args first argument global.json and second argument month.json, just use UTF8
     */
    public static void main(String[] args) {
        
        // Initialize and parse user input
        UserInput userInput = new UserInput(args);
        Request request;
        try {
          request = userInput.parse();
        } catch (org.apache.commons.cli.ParseException e) {
          System.out.println(e.getMessage());
          System.exit(-1);
          return;
        }  
        
        // If requested: Print help and return
        if (request == Request.HELP) {
            userInput.printHelp();
            return;
        }
        
        // Get content of input files 
        String global;
        String month;
        try {
            global = FileController.readFileToString(userInput.getFile(UserInputFile.JSON_GLOBAL));
            month = FileController.readFileToString(userInput.getFile(UserInputFile.JSON_MONTH));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return;
        }
        
        // Initialize time sheet
        TimeSheet timeSheet;
        try {
            timeSheet = Parser.parseTimeSheetJson(global, month);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return;
        }

        // Check time sheet
        IChecker checker = new MiLoGChecker(timeSheet);
        CheckerReturn checkerReturn;
        try {
            checkerReturn = checker.check();
        } catch (CheckerException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return;
        }
        if (checkerReturn == CheckerReturn.INVALID) {
            for (CheckerError error : checker.getErrors()) {
                System.out.println(error.getErrorMessage());
            }
            return;
        }
        
        // Generates and saves output file
        ClassLoader classLoader = Main.class.getClassLoader();
        try {
            String latexTemplate = FileController.readInputStreamToString(classLoader.getResourceAsStream("MiLoG_Template.tex"));
            IGenerator generator = new LatexGenerator(timeSheet, latexTemplate);
            FileController.saveStringToFile(generator.generate(), userInput.getFile(UserInputFile.OUTPUT));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        
    }
}
