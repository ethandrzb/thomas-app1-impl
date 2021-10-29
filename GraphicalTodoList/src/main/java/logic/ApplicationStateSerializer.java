/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class ApplicationStateSerializer
{
    public void saveListsToFile(List<TodoList> lists, Path filePath)
    {
        // Attempt to create new file at filePath

        // For each TodoList in lists
            // Write list title to file
            // For each item in current TodoList
                // Write completed checkbox ("[x]") or incomplete checkbox ("[ ]")
                // Write item description
                // If item has due date, write due date formatted as "yyyy-MM-dd"
                // Else, write none
    }

    public List<TodoList> loadListsFromFile(Path filePath)
    {
        // Attempt to load file at filePath

        // Create buffer to store ListItems

        // While there are still lines to be read
            // If current line starts with "Title: " (i.e. new TodoList encountered)
                // If listItem buffer isn't empty
                    // Create new TodoList object with last read title and current ListItem buffer
                // Empty ListItem buffer
                // Read new list title
            // Read current line as ListItem and add to ListItem buffer

        return Collections.emptyList();
    }

    private ListItem convertStringToListItem(String line)
    {
        // Check if line starts with "[ ]"
            // If so, completed = false
            // If not, check if line starts with "[x]"
                // If so, completed = true
                // If not, display error message

        // Search for "Date: yyyy-MM-dd" anchored to end of line
        // If found
            // create new LocalDate object from the yyyy-MM-dd formatted date
            // Set hasDate flag to true
            // Set end of description index to line.length - 18
        // If not found, search for "Date: none" anchored to end of line
            // If found
                // set hasDate flag to false
                // Set end of description index to line.length - 11
            // If not found, display error message

        // Extract description from line using end of description index

        // If hasDate flag is true, return new ListItem with completed, description, and dueDate
        // Else, return new ListItem with completed and description


        return null;
    }
}
