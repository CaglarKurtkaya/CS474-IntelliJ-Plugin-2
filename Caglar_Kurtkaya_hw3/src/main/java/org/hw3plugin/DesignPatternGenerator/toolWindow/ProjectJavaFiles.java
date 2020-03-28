package org.hw3plugin.DesignPatternGenerator.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
/*
This class iterates thru project and saves all java file names into an ArrayList
 */
public class ProjectJavaFiles {
    List<String> fileNames = new ArrayList<>();
    List<String> packageNames = new ArrayList<>();
    Project project;

    private static final Logger logger = LoggerFactory.getLogger("ProjectJavaFiles");

    //Constructor
    public ProjectJavaFiles(Project project) {
        this.project = project;
        init();
    }


    public void init(){

        ProjectFileIndex.SERVICE.getInstance(project).iterateContent(new ContentIterator() {
            @Override
            public boolean processFile(@NotNull VirtualFile virtualFile) {
                PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);

                if(psiFile != null){
                    //Check if the file is a Java file
                    if(psiFile instanceof PsiJavaFile) {
                        //add it to the array
                        fileNames.add(psiFile.getVirtualFile().getNameWithoutExtension());
                        if(!packageNames.contains(((PsiJavaFile) psiFile).getPackageName())){
                            packageNames.add(((PsiJavaFile)psiFile).getPackageName());
                            logger.info("package name = {} added to packageNames",((PsiJavaFile)psiFile).getPackageName());
                        }
                    }

                }
                return true;
            }
        });
    }
}
