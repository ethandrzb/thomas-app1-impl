/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package app;

import javafx.collections.ObservableList;
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
import java.util.Collections;

// TODO: Remove all debugging print statements
// TODO: Display error dialog box when save/load fails

public class TodoListApplicationController
{
    private enum listItemFilterOption {ALL, INCOMPLETE_ONLY, COMPLETE_ONLY}
    private listItemFilterOption selectedFilterOption;

    private final Alert emptyItemDescriptionExistsAlert = new Alert(Alert.AlertType.ERROR);
    private static final String TEXT_FIELD_ERROR_BORDER_STYLE_NAME = "error";

    private TodoList todoList;
    private final ArrayList<TextField> textFields = new ArrayList<>();
    private final ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private final ArrayList<DatePicker> datePickers = new ArrayList<>();
    private final ArrayList<Button> removeButtons = new ArrayList<>();

    @FXML
    private Button addItemButton;

    @FXML
    private TextField currentListTitleTextField;

    @FXML
    private ToggleGroup listFilterOptionToggleGroup;

    @FXML
    private MenuItem loadListsMenuItem;

    @FXML
    private MenuItem saveSelectedListsMenuItem;

    @FXML
    public MenuItem clearListMenuItem;

    @FXML
    private ToggleGroup sortOptionToggleGroup;

    @FXML
    private RadioMenuItem viewAllItemsRadioMenuItem;

    @FXML
    private RadioMenuItem viewCompletedItemsOnlyRadioMenuItem;

    @FXML
    private RadioMenuItem viewIncompleteItemsOnlyRadioMenuItem;

    @FXML
    private VBox listContainerVBox;

    @FXML
    public void addNewItemButtonPressed(ActionEvent actionEvent)
    {
        // TODO: Display dialog box to alert user that there exists an item with an empty description.
        // Check if most recently added item has a non-empty description
        if(validateAllItemDescriptionsNonEmpty())
        {
            // Add new item to this TodoList
            todoList.addItemToList();
        }
        else
        {
            emptyItemDescriptionExistsAlert.show();
        }
    }

    // Returns false if todoList contains an item with an empty description field.
    // Otherwise, returns true.
    private boolean validateAllItemDescriptionsNonEmpty()
    {
        boolean allItemDescriptionsNonEmpty = true;

        for(int i = 0; i < todoList.getListSize().get(); i++)
        {
            if(todoList.getAllListItems().get(i).getDescription().isEmpty())
            {
                // Apply error border to empty textfield
                applyTextFieldErrorBorder(textFields.get(i));

                allItemDescriptionsNonEmpty = false;
            }
        }

        return allItemDescriptionsNonEmpty;
    }

    @FXML
    public void saveSelectedListsMenuItemSelected(ActionEvent actionEvent)
    {
        // Get list of currently selected items in ListView

        // Open a FileChooser so the user can specify where the selected TodoLists should be saved

        // Save these TodoList objects to that file
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
        // Previously created listener was invalidated by load operation
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

    // TODO: Add sort list parameter
    private GridPane todoListToGridPane()
    {
        GridPane table = new GridPane();

        for(int i = 0; i < todoList.getAllListItems().size(); i++)
        {
            // Make new control objects, if necessary
            if(checkBoxes.size() <= i)
            {
                addCheckBox(i);
            }

            if(textFields.size() <= i)
            {
                addTextField(i);
            }

            if(datePickers.size() <= i)
            {
                addDatePicker(i);
            }

            if(removeButtons.size() <= i)
            {
                // TODO: Move body of this if statement to a separate method to match the other controls in the GridPane
//                addRemoveButton(i);

                removeButtons.add(new Button("Remove"));

                // TODO: Add remove button functionality
                int finalI = i;
//                removeButtons.get(i).pressedProperty().addListener((observable, oldValue, newValue) -> removeItem(finalI));

            }

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
                table.add(removeButtons.get(i), 3, i);

                // Set margins between controls
                GridPane.setMargin(checkBoxes.get(i), new Insets(5));
                GridPane.setMargin(textFields.get(i), new Insets(5));
                GridPane.setMargin(datePickers.get(i), new Insets(5));
                GridPane.setMargin(removeButtons.get(i), new Insets(5));
            }
        }
        table.setAlignment(Pos.CENTER);

        return table;
    }

//    // Removes the item at index from both the GUI and todoList object
//    private void removeItem(int index)
//    {
//        checkBoxes.remove(index);
//        textFields.remove(index);
//        datePickers.remove(index);
//        removeButtons.remove(index);
//
//        todoList.removeListItem(index);
//
//        updateDisplayedList();
//    }

    private void addCheckBox(int index)
    {
        checkBoxes.add(new CheckBox());
        checkBoxes.get(index).setTextFill(Color.WHITE);
        checkBoxes.get(index).setAlignment(Pos.CENTER);

        // Attach change listener to current checkbox to update the current ListItem's completed field.
        checkBoxes.get(index).selectedProperty().addListener((observable, oldValue, newValue) ->
            todoList.getListItem(index).setItemCompleted(newValue)
        );

        // Load completion of current ListItem in TodoList to associated CheckBox
        checkBoxes.get(index).setSelected(todoList.getListItem(index).isItemCompleted());
    }

    private void addTextField(int index)
    {
        textFields.add(new TextField());
        textFields.get(index).setAlignment(Pos.CENTER);

        // Attach listener to current TextField
        textFields.get(index).textProperty().addListener((observable, oldValue, newValue) ->
        {
            int maxLength = 256;
            int newLength = Math.min(newValue.length(), maxLength);

            // Only change description stored in todoList if description is non-empty
            if (newLength != 0)
            {
                removeTextFieldErrorBorder(textFields.get(index));

                // Truncate input string to fit in length limit
                textFields.get(index).setText(textFields.get(index).getText().substring(0, newLength));

                // Update description of current ListItem
                todoList.getListItem(index).setDescription(textFields.get(index).getText());
            }
            // Apply red border if description is empty
            else
            {
                applyTextFieldErrorBorder(textFields.get(index));
            }
        });

        // Load description of current ListItem in TodoList to associated TextField
        textFields.get(index).setText(todoList.getListItem(index).getDescription());
    }

    private void addDatePicker(int index)
    {
        datePickers.add(new DatePicker());
        datePickers.get(index).setConverter(new StringConverter<>()
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

        // Attach listener to current DatePicker
        datePickers.get(index).valueProperty().addListener((observable, oldValue, newValue) ->
            // Update due date of current ListItem
            todoList.getListItem(index).setDueDate(datePickers.get(index).getValue())
        );

        // Load date of current ListItem in TodoList to associated DatePicker
        datePickers.get(index).valueProperty().set(todoList.getListItem(index).getDueDate());
    }

    private void applyTextFieldErrorBorder(TextField tf)
    {
        ObservableList<String> styleClass = tf.getStyleClass();

        if(!styleClass.contains(TEXT_FIELD_ERROR_BORDER_STYLE_NAME))
        {
            styleClass.add(TEXT_FIELD_ERROR_BORDER_STYLE_NAME);
        }
    }

    private void removeTextFieldErrorBorder(TextField tf)
    {
        ObservableList<String> styleClass = tf.getStyleClass();

        styleClass.removeAll(Collections.singleton(TEXT_FIELD_ERROR_BORDER_STYLE_NAME));
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
        todoList.getListSize().addListener((observable, oldValue, newValue) -> updateDisplayedList());

        // Associate each view mode RadioMenuItem with the appropriate enum
        viewAllItemsRadioMenuItem.setUserData(listItemFilterOption.ALL);
        viewIncompleteItemsOnlyRadioMenuItem.setUserData(listItemFilterOption.INCOMPLETE_ONLY);
        viewCompletedItemsOnlyRadioMenuItem.setUserData(listItemFilterOption.COMPLETE_ONLY);

        // Display all items by default
        selectedFilterOption = listItemFilterOption.ALL;

        // Display new list
        updateDisplayedList();

        // Add listener for title change to update title of todoList
        currentListTitleTextField.textProperty().addListener((observable, oldValue, newValue) ->
                todoList.setTitle(newValue));

        // Add listener to listFilterOptionToggleGroup to convert currently selected filter mode menu item to enum
        listFilterOptionToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
                {
                    // Update selectedFilterOption
                    selectedFilterOption = (listItemFilterOption) newValue.getUserData();

                    updateDisplayedList();
                });

        // Set title for empty description alert box
        emptyItemDescriptionExistsAlert.setTitle("Unable to add new item");

        // Set content for empty description alert box
        emptyItemDescriptionExistsAlert.setContentText("All items must have a non-empty description" +
                                                        " before a new item can be added.");
    }

    @FXML
    public void clearListMenuItemSelected(ActionEvent actionEvent)
    {
        clearGeneratedControls();
        todoList.clear(); 
        updateDisplayedList();
    }
}
