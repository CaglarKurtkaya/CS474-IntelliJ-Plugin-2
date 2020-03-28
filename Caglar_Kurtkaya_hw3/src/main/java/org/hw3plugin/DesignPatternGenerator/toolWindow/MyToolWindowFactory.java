package org.hw3plugin.DesignPatternGenerator.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Tool windows are child windows of the IDE used to display information.
These windows generally have their own toolbars (referred to as tool window bars)
along the outer edges of the main window containing one or more tool window buttons,
which activate panels displayed on the left, bottom and right sides of the main IDE window.
 */
public class MyToolWindowFactory implements ToolWindowFactory {
    public String path;

    private static final Logger logger = LoggerFactory.getLogger("MyToolWindowFactory");
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        path = project.getBasePath();
        path += "/src/main/java";

        MyToolWindow myToolWindow = new MyToolWindow(toolWindow, path, project);

        logger.info("myToolWindow has been created");

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(myToolWindow.getContent(),"", false);
        toolWindow.getContentManager().addContent(content);
        logger.info("Content has been added to toolWindow");

    }
}
