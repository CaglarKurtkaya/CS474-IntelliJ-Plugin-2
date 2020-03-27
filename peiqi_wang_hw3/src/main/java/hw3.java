import com.cs474.ASTParser;
import com.cs474.Configs;
import com.cs474.CreateFile;
import com.cs474.MethodVisitor;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
//import org.apache.log4j.Logger;

public class hw3 {
    // It will store the log into saveUserLog.log
    static final Logger logger = LoggerFactory.getLogger("saveUserLog");
    static HashSet<String> s = new HashSet<>();

    public static void main(String[] args) throws FileNotFoundException {
        /** example of log output*/
        logger.info("SLF4J From hw2");
        logger.error("ERROR");

        /** Example of using ast parser to get the structure of java file**/
        String path = "src/main/java/com/cs474/";
        ASTParser ast = new ASTParser();
        ast.parseTemplate(path);

        for (String str : ast.getSetForClassOrInterface()) {
            System.out.println(str);
        }

        System.out.println();

        for (String str : ast.getSetForMethod()) {
            System.out.println(str);
        }
    }

}