package com.cs474;

import java.io.File;
import java.io.PrintWriter;
import com.cs474.Configs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateFile {
    // logger for log
    static File newFile;
    static final Logger logger = LoggerFactory.getLogger("testLog");

    // The Generate Code method
    public void generateCode(String path, String name, String content) {
        // Get the path and create an empty file in that path
        newFile = new File(path,name+".java");

        // try it and catch exception if can't write to file
        try (PrintWriter out = new PrintWriter(newFile)) {
            //File Content Based on generated Design Pattern
            out.print(content);
            logger.debug(path);
            logger.info(name);
        }
        catch (Exception e){
            //Write the error log
            logger.error("File cannot be written");
        }
    }
}
