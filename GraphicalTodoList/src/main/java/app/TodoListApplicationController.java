/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import logic.ListItem;

import java.io.File;
import java.util.List;

public class TodoListApplicationController
{

    @FXML
    private Button addNewListButton;

    //TODO: Add test for 256 TodoLists
    @FXML
    private ListView<?> availableListView;

    @FXML
    private Label currentListTitleLabel;

    @FXML
    private ToggleGroup listViewModeToggleGroup;

    @FXML
    private MenuItem loadListsMenuItem;

    @FXML
    private Button removeSelectedListsButton;

    @FXML
    private MenuItem saveSelectedListsMenuItem;

    @FXML
    private RadioMenuItem viewAllItemsRadioMenuItem;

    @FXML
    private RadioMenuItem viewCompletedItemsOnlyRadioMenuItem;

    @FXML
    private RadioMenuItem viewIncompleteItemsOnlyRadioMenuItem;

    @FXML
    public void addNewItemButtonPressed(ActionEvent actionEvent)
    {
        // Get reference to currently selected TodoList

        // Add new item to this TodoList
    }

    @FXML
    public void addNewListButtonPressed(ActionEvent event)
    {
        // Create blank TodoList and add it to the list of available lists
    }

    @FXML
    public void removeSelectedListsButtonPressed(ActionEvent event)
    {
        // Get list of selected TodoLists in ListView

        // Remove them
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
        // Open a FileChooser so the user can specify from where the TodoLists should be loaded
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File chosenFile = fileChooser.showOpenDialog(loadListsMenuItem.getParentPopup().getScene().getWindow());

        // Load lists from file

        // Add loaded lists to currently loaded lists
    }

    private GridPane todoListToGridPane(List<ListItem> items)
    {
        // Create new (n + 1) by 4 GridPane where n is the number of listItems in items

        // Add Label with text "Description" to Row 0, Column 1

        // Add Label with text "Due Date" to Row 0, Column 2

        // For each ListItem in items
            // Add Checkbox to Column 0
            // Add description Label to Column 1
            // Add DatePicker to Column 2
            // Add remove Button to Column 3

        return null;
    }

    private void updateSceneGraph(GridPane gp)
    {
        // Get current scene graph

        // Discard GridPane of currently displayed list

        // Attach new GridPane to scene graph

        // Execute changes/cleanup
    }

    public void initialize()
    {
        // Select viewAllListItemsRadioMenuItem by default

        // Initialize availableListView to empty list

        // Add listener to availableListView to update it whenever the available list changes

        // Add listener to availableListView to change the list shown on the right to match the currently selected list
    }
}
