/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */


package logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static logic.TodoListTest.dateFormatter;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationStateSerializerTest
{
    private final Path currentPath = Paths.get(System.getProperty("user.dir"));
    static ArrayList<ListItem> testListItems = new ArrayList<>();

    @BeforeAll
    static void generateTestLists()
    {
        testListItems.add(new ListItem(false, "i1", LocalDate.parse("2021-11-07", dateFormatter)));
        testListItems.add(new ListItem(true, "i2", LocalDate.parse("2021-11-06", dateFormatter)));
        testListItems.add(new ListItem(false, "i3", LocalDate.parse("2021-11-05", dateFormatter)));
        testListItems.add(new ListItem(true, "i4", LocalDate.parse("2021-11-04", dateFormatter)));
        testListItems.add(new ListItem(false, "i5", LocalDate.parse("2021-11-03", dateFormatter)));
    }

    @Test
    void saveListsToFile()
    {
        String[] expected = """
                [ ] i1 (Due Date: 2021-11-07)
                [x] i2 (Due Date: 2021-11-06)
                [ ] i3 (Due Date: 2021-11-05)
                [x] i4 (Due Date: 2021-11-04)
                [ ] i5 (Due Date: 2021-11-03)
                """.split("\n");

        ApplicationStateSerializer serializer = new ApplicationStateSerializer();

        // Add test data to list
        TodoList list = new TodoList(new ArrayList<>(testListItems));

        // Save TodoLists to a file
        try
        {
            serializer.saveListToFile(list, new File(String.valueOf(getPathFromFileName("saveListTest.txt"))));
        }
        catch(FileNotFoundException e)
        {
            Assertions.fail();
        }

        // Check file's contents line by line to make sure they match the expected contents
        try(Scanner fromFile = new Scanner(new File(String.valueOf(getPathFromFileName("saveListTest.txt")))))
        {
            for(String line : expected)
            {
                assertEquals(line, fromFile.nextLine());
            }
        }
        catch(FileNotFoundException e)
        {
            Assertions.fail();
        }
    }

    @Test
    void loadListsFromFile()
    {
        TodoList list = new TodoList();
        ApplicationStateSerializer serializer = new ApplicationStateSerializer();

        // Load TodoLists from file
        try
        {
            list = serializer.loadListFromFile(
                    new File(String.valueOf(getPathFromFileName("loadListsFromFileTestList.txt"))));
        }
        catch(FileNotFoundException e)
        {
            Assertions.fail();
        }

        ArrayList<ListItem> loadedItems = (ArrayList<ListItem>) list.getAllListItems();

        // Assert that all items in all TodoLists from file are accessible
        for(int i = 0; i < list.getAllListItems().size(); i++)
        {
            assertEquals(testListItems.get(i).isItemCompleted(), loadedItems.get(i).isItemCompleted());
            assertEquals(testListItems.get(i).getDescription(), loadedItems.get(i).getDescription());
            assertEquals(testListItems.get(i).getDueDate(), loadedItems.get(i).getDueDate());
        }
    }

    private Path getPathFromFileName(String fileName)
    {
        return Paths.get(currentPath.toString(), "src", "test", "resources", "logic", fileName);
    }
}