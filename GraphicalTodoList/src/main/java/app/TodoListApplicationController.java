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
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import logic.ApplicationStateSerializer;
import logic.TodoList;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// TODO: Remove all debugging print statements

public class TodoListApplicationController
{
    private enum listItemFilterOption {ALL, INCOMPLETE_ONLY, COMPLETE_ONLY}

    private listItemFilterOption selectedFilterOption;

    private TodoList todoList;
    private final ArrayList<TextField> textFields = new ArrayList<>();
    private final ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private final ArrayList<DatePicker> datePickers = new ArrayList<>();

    @FXML
    private Button addItemButton;

    @FXML
    private TextField currentListTitleTextField;

    @FXML
    private ToggleGroup listViewModeToggleGroup;

    @FXML
    private MenuItem loadListsMenuItem;

    @FXML
    private MenuItem saveSelectedListsMenuItem;

    @FXML
    public MenuItem clearListMenuItem;

    @FXML
    private ToggleGroup sortModeToggleGroup;

    @FXML
    private RadioMenuItem viewAllItemsRadioMenuItem;

    @FXML
    private RadioMenuItem viewCompletedItemsOnlyRadioMenuItem;

    @FXML
    private RadioMenuItem viewIncompleteItemsOnlyRadioMenuItem;

    @FXML
    private  VBox listContainerVBox;

    @FXML
    public void addNewItemButtonPressed(ActionEvent actionEvent)
    {
        // Add new item to this TodoList
        todoList.addItemToList();
    }

    @FXML
    public void viewAllListItemsRadioMenuItemSelected(ActionEvent actionEvent)
    {
        selectedFilterOption = listItemFilterOption.ALL;

        // TODO: Make enum observable to avoid manual calls to updateDisplayedList
        updateDisplayedList();

        // Get all listItems from currently displayed list

        // Generate GridPane from list items

        // Attach GridPane to scene graph
    }

    @FXML
    public void viewIncompleteItemsOnlyRadioMenuItemSelected(ActionEvent actionEvent)
    {
        selectedFilterOption = listItemFilterOption.INCOMPLETE_ONLY;

        // TODO: Make enum observable to avoid manual calls to updateDisplayedList
        updateDisplayedList();
        // Get all incomplete listItems from currently displayed list

        // Generate GridPane from list items

        // Attach GridPane to scene graph
    }

    @FXML
    public void viewCompletedItemsOnlyRadioMenuItemSelected(ActionEvent actionEvent)
    {
        selectedFilterOption = listItemFilterOption.COMPLETE_ONLY;

        // TODO: Make enum observable to avoid manual calls to updateDisplayedList
        updateDisplayedList();

        // Get all completed listItems from currently displayed list

        // Generate GridPane from list items

        // Attach GridPane to scene graph
    }

    @FXML
    public void saveSelectedListsMenuItemSelected(ActionEvent actionEvent)
    {
        // Get list of currently selected items in ListView

        // Open a FileChooser so the user can specify where the selected TodoLists should be saved

        // Save these TodoList objects to that file

        System.out.println(todoList);
    }

    @FXML
    public void loadListsMenuItemSelected(ActionEvent actionEvent)
    {
        ApplicationStateSerializer serializer = new ApplicationStateSerializer();

        // Open a FileChooser so the user can specify from where the TodoLists should be loaded
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File chosenFile = fileChooser.showOpenDialog(loadListsMenuItem.getParentPopup().getScene().getWindow());

        // User closed FileChooser without selecting a file
        if(chosenFile == null)
        {
            return;
        }

        // Load list from file
        todoList = serializer.loadListFromFile(chosenFile);

        // Add new listener to list size
//        todoList.getListSize().addListener((observable, oldValue, newValue) -> System.out.println(todoList.getListSize()));
        todoList.getListSize().addListener((observable, oldValue, newValue) -> updateDisplayedList());

        clearGeneratedControls();

        updateDisplayedList();
    }

    private void clearGeneratedControls()
    {
        checkBoxes.clear();
        textFields.clear();
        datePickers.clear();
    }

    // TODO: Add list selection parameter
    // TODO: Add sort list parameter
    private GridPane todoListToGridPane()
    {
        GridPane table = new GridPane();

        // TODO: Remove this
        // Debug statement used to track differences between size of list in todoList and SimpleIntegerProperty bound to it
//        System.out.println(todoList.getListItems().size() + ", " + todoList.getListSize().getValue());

        for(int i = 0; i<todoList.getAllListItems().size(); i++)
        {
            // TODO: Move body of this if statement to separate method
            if(checkBoxes.size() <= i)
            {
                checkBoxes.add(new CheckBox());
                checkBoxes.get(i).setTextFill(Color.WHITE);
                checkBoxes.get(i).setAlignment(Pos.CENTER);
                int finalI1 = i;

                // Attach change listener to current checkbox to update the current ListItem's completed field.
                checkBoxes.get(i).selectedProperty().addListener((observable, oldValue, newValue) ->
                {
                    todoList.getListItem(finalI1).setItemCompleted(newValue);

//                    System.out.println("Checkbox " + finalI1 + ": " + newValue);
                });

                // Load completion of current ListItem in TodoList to associated CheckBox
                checkBoxes.get(i).setSelected(todoList.getListItem(i).isItemCompleted());
            }

            // TODO: Move body of this if statement to separate method
            if(textFields.size() <= i)
            {
                textFields.add(new TextField());
                textFields.get(i).setAlignment(Pos.CENTER);

                int finalI2 = i;
//                textFields.get(i).textProperty().addListener((observable, oldValue, newValue) ->
//                        System.out.println("TextField " + finalI2 + ": " + newValue));

                // Attach listener to current TextField
                textFields.get(i).textProperty().addListener(new ChangeListener<String>()
                {
                    // TODO: Change maxLength to 256
                    private int maxLength = 10;
                    private int newLength;
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
                    {
                        // Truncate input string to fit in length limit
                        newLength = Math.min(newValue.length(), maxLength);
                        textFields.get(finalI2).setText(textFields.get(finalI2).getText().substring(0,newLength));

                        // Update description of current ListItem
                        todoList.getListItem(finalI2).setDescription(textFields.get(finalI2).getText());

//                        System.out.println("TextField " + finalI2 + ": " + newValue);
                    }
                });

                // Load description of current ListItem in TodoList to associated TextField
                textFields.get(i).setText(todoList.getListItem(i).getDescription());
            }

            // TODO: Move body of this if statement to separate method
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

                // Attach listener to current DatePicker
                datePickers.get(i).valueProperty().addListener((observable, oldValue, newValue) ->
                        {
                            // Update due date of current ListItem
                            todoList.getListItem(finalI3).setDueDate(datePickers.get(finalI3).getValue());

//                            System.out.println("DatePicker " + finalI3 + ": " + newValue);
                        });

                // Load date of current ListItem in TodoList to associated DatePicker
                datePickers.get(i).valueProperty().set(todoList.getListItem(i).getDueDate());
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

            // Apply filter option
            boolean addListItemToGridPane = switch(selectedFilterOption)
                    {
                        case ALL -> true;
                        case INCOMPLETE_ONLY -> !todoList.getListItem(i).isItemCompleted();
                        case COMPLETE_ONLY -> todoList.getListItem(i).isItemCompleted();
                    };

            // Only add current ListItem if permitted by filter
            if(addListItemToGridPane)
            {
                // Add controls to GridPane
                table.add(checkBoxes.get(i), 0, i);
                table.add(textFields.get(i), 1, i);
                table.add(datePickers.get(i), 2, i);

                // Set margins between controls
                GridPane.setMargin(checkBoxes.get(i), new Insets(5));
                GridPane.setMargin(textFields.get(i), new Insets(5));
                GridPane.setMargin(datePickers.get(i), new Insets(5));
            }
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

    private void updateDisplayedList()
    {
        GridPane gp = todoListToGridPane();

        currentListTitleTextField.setText(todoList.getTitle());

        // Check if there's already something in the Vbox and remove it if it exists
        if(!listContainerVBox.getChildren().isEmpty())
        {
            listContainerVBox.getChildren().clear();
        }

        listContainerVBox.getChildren().add(gp);
    }

    @FXML
    public void initialize()
    {
        todoList = new TodoList();

        // Add listener to todoList to monitor for changes in size
        // Will be overwritten if the user loads a list.
//        todoList.getListSize().addListener((observable, oldValue, newValue) -> System.out.println(todoList.getListSize()));
        todoList.getListSize().addListener((observable, oldValue, newValue) -> updateDisplayedList());

        // Display all items by default
        selectedFilterOption = listItemFilterOption.ALL;

        // Display new list
        updateDisplayedList();

        // Add listener for title change to update title of todoList
        currentListTitleTextField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            todoList.setTitle(newValue);

//            System.out.println("List title = " + newValue);
        });

        // Add listener to listViewMode
        listViewModeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
                                                                        updateDisplayedList());
    }

    @FXML
    public void clearListMenuItemSelected(ActionEvent actionEvent)
    {
        clearGeneratedControls();
        todoList.clear(); 
        updateDisplayedList();
    }
}
