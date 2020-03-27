package com.cs474;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

// The method to visit all classes and methods in the CompilationUnit
// The way to use it is :
// --------------------------------------------------------------------
// || File f = new File(path);
// || CompilationUnit cu = new JavaParser().parse(f).getResult().get();
// || cu.accept(new MethodVisitor(), null);
// --------------------------------------------------------------------
public class MethodVisitor extends VoidVisitorAdapter<Void> {
    // Loggers
    static final Logger logger = LoggerFactory.getLogger("testLog");

    // HashSet to store the existing class name;
    private Set<String> setForClassOrInterface = new HashSet<>();
    private Set<String> setForMethod = new HashSet<>();

    // visit all methods in the file
    @Override
    public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
        setForMethod.add(n.getNameAsString());
        logger.debug("method:"+n.getName());
        super.visit(n, arg);
    }

    // visit all class & interface in the file
    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        setForClassOrInterface.add(n.getNameAsString());

        logger.debug("class:"+n.getName());
        logger.debug("extends:"+n.getExtendedTypes());
        logger.debug("implements:"+n.getImplementedTypes());

        super.visit(n, arg);
    }

    // get the hash set for all methods
    public Set<String> getSetForMethod() {
        return setForMethod;
    }

    // get the hash set for all class & interface
    public Set<String> getSetForClassOrInterface() {
        return  setForClassOrInterface;
    }
}