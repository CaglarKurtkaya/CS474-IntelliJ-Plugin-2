package com.cs474;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/** AST JAVA PARSER to parse the structure of java file and analyze it**/
public class ASTParser {
    // Loggers
    static final Logger logger = LoggerFactory.getLogger("testLog");

    // Hash Set to store class & method & interface
    Set<String> setForClassOrInterface  = new HashSet<>();
    Set<String> setForMethod  = new HashSet<>();

    // Traverse all files in the directory
    public List<File> traverseAllFiles(String path) {
        File f = new File(path);

        File[] fs = f.listFiles();

        List<File> fileArray = new ArrayList<>();

        for(File file: fs){
            if(!file.isDirectory() && file.getName().endsWith(".java"))
                fileArray.add(file);
                logger.debug(path + " CONTAINS: " + file.getName());
        }

        // return all java file under the directory
        return fileArray;
    }

    // Parse the template of the file
    public void parseTemplate(String path) {
        List<File> fileArray = traverseAllFiles(path);
        for (File f : fileArray) {
            try {
                // CompilationUnit for parsing the file
                CompilationUnit cu = new JavaParser().parse(f).getResult().get();
                MethodVisitor visitor = new MethodVisitor();

                // accept the method visitor
                cu.accept(visitor, null);

                setForClassOrInterface = mergeSet(setForClassOrInterface, visitor.getSetForClassOrInterface());
                setForMethod = mergeSet(setForMethod, visitor.getSetForMethod());
            } catch (FileNotFoundException e) {
                // if not found, log the error and return null
                logger.error("Not Found");
            }
        }
    }

    // get the hash set for all methods
    public Set<String> getSetForMethod() {
        return setForMethod;
    }

    // get the hash set for all class & interface
    public Set<String> getSetForClassOrInterface() {
        return  setForClassOrInterface;
    }

    // Merge two set into one
    public static <T> Set<T> mergeSet(Set<T> a, Set<T> b)
    {
        return new HashSet<T>() {{
            addAll(a);
            addAll(b);
        } };
    }

}
