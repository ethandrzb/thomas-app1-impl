/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package app;

import javafx.collections.ObservableList;
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
import logic.ListItem;
import logic.TodoList;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;

// TODO: Remove all debugging print statements
// TODO: Display error dialog box when save/load fails

public class TodoListApplicationController
{
    private enum listItemFilterOption {ALL, INCOMPLETE_ONLY, COMPLETE_ONLY}
    private listItemFilterOption selectedFilterOption;

    private final Alert emptyItemDescriptionExistsOnAddAlert = new Alert(Alert.AlertType.ERROR);
    private final Alert emptyItemDescriptionExistsOnSaveListAlert = new Alert(Alert.AlertType.ERROR);
    private static final String TEXT_FIELD_ERROR_BORDER_STYLE_NAME = "error";

    private TodoList todoList;
    private final HashMap<ListItem, TextField> textFields = new HashMap<>();
    private final HashMap<ListItem, CheckBox> checkBoxes = new HashMap<>();
    private final HashMap<ListItem, DatePicker> datePickers = new HashMap<>();
    private final HashMap<ListItem, Button> removeButtons = new HashMap<>();

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
    public void addNewItemButtonPressed()
    {
        // Check if most recently added item has a non-empty description
        if(validateAllItemDescriptionsNonEmpty())
        {
            // Add new item to this TodoList
            todoList.addItemToList();
        }
        else
        {
            emptyItemDescriptionExistsOnAddAlert.show();
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
                // Apply error border to empty TextField
                applyTextFieldErrorBorder(textFields.get(todoList.getAllListItems().get(i)));

                allItemDescriptionsNonEmpty = false;
            }
        }

        return allItemDescriptionsNonEmpty;
    }

    @FXML
    public void saveSelectedListsMenuItemSelected()
    {
        // Validate that all item descriptions are non-empty
        if(validateAllItemDescriptionsNonEmpty())
        {
            ApplicationStateSerializer serializer = new ApplicationStateSerializer();

            // Open a FileChooser so the user can specify to where the TodoLists should be saved
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

            File chosenFile = fileChooser.showSaveDialog(saveSelectedListsMenuItem.getParentPopup().getScene().getWindow());

            // User closed FileChooser without specifying a path to a new file
            if(chosenFile == null)
            {
                return;
            }

            // Load list from file
            serializer.saveListToFile(todoList, chosenFile);
        }
        else
        {
            emptyItemDescriptionExistsOnSaveListAlert.show();
        }
    }

    @FXML
    public void loadListsMenuItemSelected()
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
        removeButtons.clear();
    }

    // TODO: Add sort list parameter
    private GridPane todoListToGridPane()
    {
        GridPane table = new GridPane();

        for(int i = 0; i < todoList.getAllListItems().size(); i++)
        {
            ListItem currentItem = todoList.getAllListItems().get(i);

            // Make new control objects, if necessary
            if(checkBoxes.size() <= i)
            {
                addCheckBox(currentItem);
            }

            if(textFields.size() <= i)
            {
                addTextField(currentItem);
            }

            if(datePickers.size() <= i)
            {
                addDatePicker(currentItem);
            }

            if(removeButtons.size() <= i)
            {
                addRemoveButton(currentItem);
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
                table.add(checkBoxes.get(currentItem), 0, i);
                table.add(textFields.get(currentItem), 1, i);
                table.add(datePickers.get(currentItem), 2, i);
                table.add(removeButtons.get(currentItem), 3, i);

                // Set margins between controls
                GridPane.setMargin(checkBoxes.get(currentItem), new Insets(5));
                GridPane.setMargin(textFields.get(currentItem), new Insets(5));
                GridPane.setMargin(datePickers.get(currentItem), new Insets(5));
                GridPane.setMargin(removeButtons.get(currentItem), new Insets(5));
            }
        }
        table.setAlignment(Pos.CENTER);

        return table;
    }

    private void addCheckBox(ListItem item)
    {
        checkBoxes.put(item, new CheckBox());
        checkBoxes.get(item).setTextFill(Color.WHITE);
        checkBoxes.get(item).setAlignment(Pos.CENTER);

        // Attach change listener to current checkbox to update the current ListItem's completed field.
        checkBoxes.get(item).selectedProperty().addListener((observable, oldValue, newValue) ->
                item.setItemCompleted(newValue)
        );

        // Load completion of current ListItem in TodoList to associated CheckBox
        checkBoxes.get(item).setSelected(item.isItemCompleted());
    }

    private void addTextField(ListItem item)
    {
        textFields.put(item, new TextField());
        textFields.get(item).setAlignment(Pos.CENTER);

        // Attach listener to current TextField
        textFields.get(item).textProperty().addListener((observable, oldValue, newValue) ->
        {
            int maxLength = 256;
            int newLength = Math.min(newValue.length(), maxLength);

            // Only change description stored in todoList if description is non-empty
            if (newLength != 0)
            {
                removeTextFieldErrorBorder(textFields.get(item));

                // Truncate input string to fit in length limit
                textFields.get(item).setText(textFields.get(item).getText().substring(0, newLength));
            }
            // Apply red border if description is empty
            else
            {
                applyTextFieldErrorBorder(textFields.get(item));
            }

            // Update description of current ListItem
            item.setDescription(textFields.get(item).getText());
        });

        // Load description of current ListItem in TodoList to associated TextField
        textFields.get(item).setText(item.getDescription());
    }

    private void addDatePicker(ListItem item)
    {
        datePickers.put(item, new DatePicker());
        datePickers.get(item).setConverter(new StringConverter<>()
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
        datePickers.get(item).valueProperty().addListener((observable, oldValue, newValue) ->
            // Update due date of current ListItem
                item.setDueDate(datePickers.get(item).getValue())
        );

        // Load date of current ListItem in TodoList to associated DatePicker
        datePickers.get(item).valueProperty().set(item.getDueDate());
    }

    private void addRemoveButton(ListItem item)
    {
        removeButtons.put(item, new Button("Remove"));

        removeButtons.get(item).pressedProperty().addListener((observable, oldValue, newValue) ->
        {
            // Remove item from todoList
            todoList.removeListItem(item);

            // Remove its associated controls
            checkBoxes.remove(item);
            textFields.remove(item);
            datePickers.remove(item);
            removeButtons.remove(item);
        });
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

        // Set title for empty description alert box on add
        emptyItemDescriptionExistsOnAddAlert.setTitle("Unable to add new item");

        // Set content for empty description alert box
        emptyItemDescriptionExistsOnAddAlert.setContentText("All items must have a non-empty description" +
                                                        " before a new item can be added.");

        // Set title for empty description alert box on save
        emptyItemDescriptionExistsOnSaveListAlert.setTitle("Unable to save list");

        // Set content for empty description alert box
        emptyItemDescriptionExistsOnSaveListAlert.setContentText("All items must have a non-empty description" +
                " before the list can be saved to a file.");
    }

    @FXML
    public void clearListMenuItemSelected()
    {
        // Clear title
        currentListTitleTextField.setText("");

        // Clear list
        clearGeneratedControls();
        todoList.clear(); 
        updateDisplayedList();
    }
}
