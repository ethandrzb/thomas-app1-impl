/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApplicationStateSerializer
{
    public void saveListToFile(List<TodoList> lists, Path filePath)
    {
        // Attempt to create new file at filePath

        // For each TodoList in lists
            // Write list title to file
            // For each item in current TodoList
                // Write completed checkbox ("[x]") or incomplete checkbox ("[ ]")
                // Write item description
                // If item has due date, write due date formatted as "(Due Date: yyyy-MM-dd)"
                // Else, write "(Due Date: none)"
    }

    public TodoList loadListFromFile(File file)
    {
        // Create buffer to store ListItems
        ArrayList<ListItem> buffer = new ArrayList<>();

        String title = "";

        // Attempt to load file at filePath
        try(Scanner fromFile = new Scanner(file))
        {
            // Get list title
            title = fromFile.nextLine();
            if(title.startsWith("Title: "))
            {
                // Remove "Title: " from title
                title = title.substring(7);
            }
            else
            {
                System.err.println("No TodoList found in " + file.getAbsolutePath());
            }

            // While there are still lines to be read
            while(fromFile.hasNext())
            {
                // Read current line as ListItem and add to ListItem buffer
                buffer.add(convertStringToListItem(fromFile.nextLine()));
            }
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Unable to open file at " + file.getAbsolutePath());
        }

        return new TodoList(title, buffer);
    }

    // TODO: Implement this function
    private ListItem convertStringToListItem(String line)
    {
        // Check if line starts with "[ ]"
            // If so, completed = false
            // If not, check if line starts with "[x]"
                // If so, completed = true
                // If not, throw IllegalArgumentException and display problematic line

        // Search for "(Due Date: yyyy-MM-dd)" anchored to end of line
        // If found
            // create new LocalDate object from the yyyy-MM-dd formatted date
            // Set hasDate flag to true
            // Set end of description index to line.length - 24
        // If not found, search for "(Due Date: none)" anchored to end of line
            // If found
                // set hasDate flag to false
                // Set end of description index to line.length - 17
            // If not found, throw IllegalArgumentException and display problematic line

        // Extract description from line using end of description index

        // If hasDate flag is true, return new ListItem with completed, description, and dueDate
        // Else, return new ListItem with completed and description


        return null;
    }
}
