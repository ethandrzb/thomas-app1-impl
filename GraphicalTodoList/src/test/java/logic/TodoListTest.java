/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoListTest
{
    @BeforeEach
    void init()
    {
        // Reset TodoList object to default state between tests
    }

    @Test
    void setTitle()
    {
        // Create TodoList with title "test title"

        // Assert that the title matches "test title"

        // Set title to "different text"

        // Assert that the title matches "different text"
    }

    @Test
    void addItemToList()
    {
        // Assert that TodoList is empty

        // Add blank item to list

        // Assert that TodoList contains 1 ListItem
    }

    @Test
    void removeListItem()
    {
        // Make ArrayList of ListItems to use as test data

        // Make a new TodoList with this ArrayList

        // Assert that all test items are accessible in this TodoList

        // Remove item in middle of ListItems

        // Assert that this item is no longer present in ListItems
    }

    @Test
    void getListItems()
    {
        // Make ArrayList of ListItems to use as test data

        // Make a new TodoList with this ArrayList

        // Assert that all test items are accessible in this TodoList
    }

    @Test
    void getCompletedItems()
    {
        // Make ArrayList of ListItems to use as test data, some complete and some incomplete

        // Make a new TodoList with this ArrayList

        // Get completed ListItems

        // Assert that all ListItems returned are marked as complete

        // Assert incomplete ListItems from test data are not present in list returned by getCompletedItems
    }

    @Test
    void getIncompleteItems()
    {
        // Make ArrayList of ListItems to use as test data, some complete and some incomplete

        // Make a new TodoList with this ArrayList

        // Get incomplete ListItems

        // Assert that all ListItems returned are marked as incomplete

        // Assert complete ListItems from test data are not present in list returned by getIncompleteItems
    }

    @Test
    void canStoreAtLeast256Items()
    {
        // Algorithmically generate 260 ListItems

        // Add all ListItems to TodoList

        // Assert that all expected items are present and accessible
    }
}