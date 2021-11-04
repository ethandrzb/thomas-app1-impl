/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import logic.ApplicationStateSerializer;
import logic.ListItem;
import logic.TodoList;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TodoListApplicationController
{
    private TodoList todoList;
    private ArrayList<TextField> textFields = new ArrayList<>();
    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private ArrayList<DatePicker> datePickers = new ArrayList<>();

    @FXML
    private Button addItemButton;

    @FXML
    private Label currentListTitleLabel;

    @FXML
    private ToggleGroup listViewModeToggleGroup;

    @FXML
    private MenuItem loadListsMenuItem;

    @FXML
    private MenuItem saveSelectedListsMenuItem;

    @FXML
    private ToggleGroup sortModeToggleGroup;

    @FXML
    private ScrollPane todoListContainerScrollPane;

    @FXML
    private RadioMenuItem viewAllItemsRadioMenuItem;

    @FXML
    private RadioMenuItem viewCompletedItemsOnlyRadioMenuItem;

    @FXML
    private RadioMenuItem viewIncompleteItemsOnlyRadioMenuItem;

    @FXML
    public void addNewItemButtonPressed(ActionEvent actionEvent)
    {
        // Add new item to this TodoList
        todoList.addItemToList();
    }

    public void viewAllListItemsRadioMenuItemSelected(ActionEvent actionEvent)
    {
        // Get all listItems from currently displayed list

        // Generate GridPane from list items

        // Attach GridPane to scene graph
    }

    public void viewIncompleteItemsOnlyRadioMenuItemSelected(ActionEvent actionEvent)
    {
        // Get all incomplete listItems from currently displayed list

        // Generate GridPane from list items

        // Attach GridPane to scene graph
    }

    public void viewCompletedItemsOnlyRadioMenuItemSelected(ActionEvent actionEvent)
    {
        // Get all completed listItems from currently displayed list

        // Generate GridPane from list items

        // Attach GridPane to scene graph
    }

    public void saveSelectedListsMenuItemSelected(ActionEvent actionEvent)
    {
        // Get list of currently selected items in ListView

        // Open a FileChooser so the user can specify where the selected TodoLists should be saved

        // Save these TodoList objects to that file
    }

    public void loadListsMenuItemSelected(ActionEvent actionEvent)
    {
        ApplicationStateSerializer serializer = new ApplicationStateSerializer();

        // Open a FileChooser so the user can specify from where the TodoLists should be loaded
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File chosenFile = fileChooser.showOpenDialog(loadListsMenuItem.getParentPopup().getScene().getWindow());

        // Load list from file
        todoList = serializer.loadListFromFile(chosenFile);

        // Add new listener to list size
        todoList.getListSize().addListener((observable, oldValue, newValue) -> System.out.println(todoList.getListSize()));

        // TODO: Add call to updateSceneGraph here
    }

    private GridPane todoListToGridPane()
    {
        GridPane table = new GridPane();

        for(int i=0; i<todoList.getListItems().size(); i++)
        {

            if(checkBoxes.size() <= i)
            {
                checkBoxes.add(new CheckBox());
                checkBoxes.get(i).setTextFill(Color.WHITE);
                checkBoxes.get(i).setAlignment(Pos.CENTER);
                int finalI1 = i;
                checkBoxes.get(i).selectedProperty().addListener((observable, oldValue, newValue) ->
                        System.out.println("Checkbox " + finalI1 + ": " + newValue));

            }

            if(textFields.size() <= i)
            {
                textFields.add(new TextField());
                textFields.get(i).setAlignment(Pos.CENTER);

                int finalI2 = i;
//                textFields.get(i).textProperty().addListener((observable, oldValue, newValue) ->
//                        System.out.println("TextField " + finalI2 + ": " + newValue));
                textFields.get(i).textProperty().addListener(new ChangeListener<String>()
                {
                    private int maxLength = 10;
                    private int newLength;
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
                    {
                        newLength = Math.min(newValue.length(), maxLength);
                        textFields.get(finalI2).setText(textFields.get(finalI2).getText().substring(0,newLength));
                        System.out.println("TextField " + finalI2 + ": " + newValue);
                    }
                });
            }

            if(datePickers.size() <= i)
            {
                datePickers.add(new DatePicker());
                datePickers.get(i).setConverter(new StringConverter<>()
                {
                    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    @Override
                    public String toString(LocalDate date) {
                        if (date != null) {
                            return dateFormatter.format(date);
                        } else {
                            return "";
                        }
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        if (string != null && !string.isEmpty()) {
                            return LocalDate.parse(string, dateFormatter);
                        } else {
                            return null;
                        }
                    }
                });

                int finalI3 = i;
                datePickers.get(i).valueProperty().addListener((observable, oldValue, newValue) ->
                        System.out.println("DatePicker " + finalI3 + ": " + newValue));
            }
//            TextField textField = new TextField();
//            textField.setAlignment(Pos.CENTER);
//            CheckBox checkBox = new CheckBox("Check Box");
//            checkBox.setTextFill(Color.WHITE);
//            checkBox.setAlignment(Pos.CENTER);
//            DatePicker datePicker = new DatePicker();

//            textFields.get(i).setAlignment(Pos.CENTER);

//            checkBoxes.get(i).setTextFill(Color.WHITE);
//            checkBoxes.get(i).setAlignment(Pos.CENTER);

            //add them to the GridPane
//            table.add(textField, 0, i); //  (child, columnIndex, rowIndex)
//            table.add(checkBox , 1, i);
//            table.add(datePicker,2, i);

            table.add(checkBoxes.get(i), 0, i);
            table.add(textFields.get(i), 1, i);
            table.add(datePickers.get(i), 2, i);

            // margins are up to your preference
//            GridPane.setMargin(textField, new Insets(5));
//            GridPane.setMargin(checkBox, new Insets(5));
//            GridPane.setMargin(datePicker, new Insets(5));

            GridPane.setMargin(checkBoxes.get(i), new Insets(5));
            GridPane.setMargin(textFields.get(i), new Insets(5));
            GridPane.setMargin(datePickers.get(i), new Insets(5));
        }
        table.setAlignment(Pos.CENTER);

        return table;
        // Create new (n + 1) by 4 GridPane where n is the number of listItems in items

        // Add Label with text "Description" to Row 0, Column 1

        // Add Label with text "Due Date" to Row 0, Column 2

        // For each ListItem in items
            // Add Checkbox to Column 0
            // Add description Label to Column 1
            // Add DatePicker to Column 2
            // Add remove Button to Column 3

//        return null;
    }

    private void updateSceneGraph(GridPane gp)
    {
        System.out.println("Called updateSceneGraph");
        // Discard GridPane of currently displayed list
//        todoListContainerScrollPane.getChildrenUnmodifiable().add(gp);

        // Attach new GridPane to scene graph

        // Execute changes/cleanup
    }

    public void initialize()
    {
        todoList = new TodoList();

        // Add listener to todoList to monitor for changes in size
        // Will be overwritten if the user loads a list.
        todoList.getListSize().addListener((observable, oldValue, newValue) -> System.out.println(todoList.getListSize()));
//        todoList.getListSize().addListener((observable, oldValue, newValue) -> updateSceneGraph(todoListToGridPane()));

        // Select viewAllListItemsRadioMenuItem by default

        // Initialize availableListView to empty list

        // Add listener to availableListView to update it whenever the available list changes

        // Add listener to availableListView to change the list shown on the right to match the currently selected list
    }
}
