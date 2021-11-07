/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationStateSerializer
{
    public void saveListToFile(TodoList list, File file)
    {
        // Item due date ---------------------------|
        // Item description ----------|             |
        // Item completed --------\/  \/            \/
        String itemLineFormat = "[%c] %s (Due Date: %s)%n";

        // Attempt to create new file at filePath
        try(Formatter output = new Formatter(file))
        {
            // Write list title to file
            output.format("Title: %s%n", list.getTitle());

            // For each item in list
            for(ListItem item : list.getAllListItems(false))
            {
                // Generate completed checkbox ("[x]") or incomplete checkbox ("[ ]")
                char itemCompleted = (item.isItemCompleted()) ? 'x' : ' ';

                // Write item description
                String description = item.getDescription();

                // If item has due date, write due date formatted as "(Due Date: yyyy-MM-dd)"
                String dueDate = (item.getDueDate() != null) ? item.getDueDate().toString()

                // Else, write "(Due Date: none)"
                : "none";

                output.format(itemLineFormat, itemCompleted, description, dueDate);
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Unable to create new file at " + file.getAbsolutePath());
        }
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

    private ListItem convertStringToListItem(String line)
    {
        boolean hasDate;
        int endOfDescription;

        boolean completed;
        String description;
        LocalDate dueDate = null;

        // Check if line starts with "[ ]"
        if(line.startsWith("[ ] "))
        {
            // If so, completed = false
            completed = false;
        }
        // If not, check if line starts with "[x]"
        else if(line.startsWith("[x] "))
        {
            // If so, completed = true
            completed = true;
        }
        else
        {
            // If not, throw IllegalArgumentException and display problematic line
            throw new IllegalArgumentException("Line does not contain valid ListItem: " + line);
        }

        // Search for "(Due Date: yyyy-MM-dd)" anchored to end of line (Regex: "\(Due Date: \d\d\d\d-\d\d-\d\d\)$")
        if(match(line,"\\(Due Date: \\d\\d\\d\\d-\\d\\d-\\d\\d\\)$"))
        {
            // create new LocalDate object from the yyyy-MM-dd formatted date
            dueDate = LocalDate.parse(line.substring(line.lastIndexOf('(') + 11, line.length() - 1),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Set hasDate flag to true
            hasDate = true;

            // Set end of description index to line.length - 23
            endOfDescription = line.length() - 23;
        }
        // If not found, search for "(Due Date: none)" anchored to end of line (Regex: "\(Due Date: none\)$"
        else if(match(line,"\\(Due Date: none\\)$"))
        {
            // set hasDate flag to false
            hasDate = false;

            // Set end of description index to line.length - 17
            endOfDescription = line.length() - 17;
        }
        else
        {
            // If not found, throw IllegalArgumentException and display problematic line
            throw new IllegalArgumentException("Line does not contain valid ListItem: " + line);
        }

        // Extract description from line using end of description index
        description = line.substring(4, endOfDescription);

        // If hasDate flag is true, return new ListItem with completed, description, and dueDate
        if(hasDate)
        {
            return new ListItem(completed, description, dueDate);
        }
        // Else, return new ListItem with completed and description
        else
        {
            return new ListItem(completed, description);
        }
    }

    private boolean match(String input, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }
}
