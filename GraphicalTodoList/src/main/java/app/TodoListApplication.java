/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class TodoListApplication extends javafx.application.Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root =
                FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainGUI_Single_TodoList.fxml")));

        Scene scene = new Scene(root); // attach scene graph to scene
        stage.setTitle("Graphical Todo List"); // displayed in window's title bar

        // Add stylesheet for TextField error borders
        scene.getStylesheets().add(String.valueOf(getClass().getResource("TextFieldErrorBorder.css")));

        stage.setScene(scene); // attach scene to stage
        stage.show(); // display the stage
    }
}
