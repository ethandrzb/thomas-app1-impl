/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TodoListTest
{
    TodoList list;

    static ArrayList<ListItem> testListItems = new ArrayList<>();
    static ArrayList<ListItem> testCompletedListItems;
    static ArrayList<ListItem> testIncompleteListItems;

    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeAll
    static void generateTestLists()
    {
        // All items
        testListItems.add(new ListItem(false, "i1", LocalDate.parse("2021-11-07", dateFormatter)));
        testListItems.add(new ListItem(true, "i2", LocalDate.parse("2021-11-06", dateFormatter)));
        testListItems.add(new ListItem(false, "i3", LocalDate.parse("2021-11-05", dateFormatter)));
        testListItems.add(new ListItem(true, "i4", LocalDate.parse("2021-11-04", dateFormatter)));
        testListItems.add(new ListItem(false, "i5", LocalDate.parse("2021-11-03", dateFormatter)));

        // Completed only
        testCompletedListItems = new ArrayList<>(testListItems);
        testCompletedListItems.remove(4);
        testCompletedListItems.remove(2);
        testCompletedListItems.remove(0);

        // Incomplete only
        testIncompleteListItems = new ArrayList<>(testListItems);
        testIncompleteListItems.remove(3);
        testIncompleteListItems.remove(1);
    }

    @BeforeEach
    void init()
    {
        // Reset TodoList object to default state between tests
        list = new TodoList();
    }

    @Test
    void testDefaultConstructor()
    {
        // Will only pass if internal lists are correctly generated,
        // the listener is working properly, and a single blank item was successfully added to the list.
        assertEquals(1, list.getListSize().get());
    }

    @Test
    void testParameterizedConstructor()
    {
        list = new TodoList(testListItems);

        // Will only pass if internal lists are correctly generated,
        // the listener is working properly, and all items were added to the list.
        // Contents of items added to list will be verified in getAllListItems
        assertEquals(5, list.getListSize().get());
    }

    @Test
    void addItemToList()
    {
        // Assert that only contains a single blank item.
        // Item contents tested in testDefaultConstructor
        assertEquals(1, list.getListSize().get());

        // Add blank item to list
        list.addItemToList();

        // Assert that TodoList contains 2 ListItems
        assertEquals(2, list.getListSize().get());

        // Assert that this item was created using ListItem's default constructor
        assertFalse(list.getAllListItems().get(1).isItemCompleted());
        assertEquals("Description", list.getAllListItems().get(1).getDescription());
        assertNull(list.getAllListItems().get(1).getDueDate());
    }

    @Test
    void removeListItem()
    {
        ArrayList<ListItem> expected = new ArrayList<>(testListItems);

        // Implied to work correctly if testParameterizedConstructor passes
        list = new TodoList(new ArrayList<>(testListItems));

        // Remove item in middle of ListItems
        list.removeListItem(expected.get(1));
        expected.remove(1);

        // Assert that this item is no longer present in ListItems
        assertEquals(4, list.getListSize().get());

        // Check list contents
        compareListItemArray(expected, false, 0);
    }

    @Test
    void getAllListItems()
    {
        ArrayList<ListItem> expected = new ArrayList<>(testListItems);

        list = new TodoList(new ArrayList<>(testListItems));

        compareListItemArray(expected, false, 0);
    }

    @Test
    void sortTodoList()
    {
        ArrayList<ListItem> expected = new ArrayList<>();

        list = new TodoList(new ArrayList<>(testListItems));

        // Generate expected test data
        expected.add(new ListItem(false, "i5", LocalDate.parse("2021-11-03", dateFormatter)));
        expected.add(new ListItem(true, "i4", LocalDate.parse("2021-11-04", dateFormatter)));
        expected.add(new ListItem(false, "i3", LocalDate.parse("2021-11-05", dateFormatter)));
        expected.add(new ListItem(true, "i2", LocalDate.parse("2021-11-06", dateFormatter)));
        expected.add(new ListItem(false, "i1", LocalDate.parse("2021-11-07", dateFormatter)));

        // Assert sorted list matches expected order
        compareListItemArray(expected, true, 0);
    }

    @Test
    void getCompletedItems()
    {
        ArrayList<ListItem> expected = new ArrayList<>(testCompletedListItems);

        list = new TodoList(new ArrayList<>(testCompletedListItems));

        compareListItemArray(expected, false, 1);
    }

    @Test
    void getIncompleteItems()
    {
        ArrayList<ListItem> expected = new ArrayList<>(testIncompleteListItems);

        list = new TodoList(new ArrayList<>(testIncompleteListItems));

        compareListItemArray(expected, false, 2);
    }

    @Test
    void canStoreAtLeast256Items()
    {
        ArrayList<ListItem> expected = new ArrayList<>();
        Random rand = new Random();

        // Algorithmically generate 260 ListItems
        for(int i = 0; i < 260; i++)
        {
            expected.add(new ListItem(rand.nextBoolean(), "item #" + i, null));
        }

        // Add all ListItems to TodoList
        list = new TodoList(new ArrayList<>(expected));

        // Assert that all expected items are present and accessible
        compareListItemArray(expected, false, 0);
    }

    @Test
    void clear()
    {
        // Add list of 5 items to list
        list = new TodoList(testListItems);

        // Clear list
        list.clear();

        // Assert that list is empty
        assertTrue(list.getAllListItems().isEmpty());
    }

    @Test
    void testToString()
    {
        list = new TodoList(testListItems);

        String expected = """
                Description: i1
                Completed?: No
                Due Date: 2021-11-07
                                
                Description: i2
                Completed?: Yes
                Due Date: 2021-11-06
                                
                Description: i3
                Completed?: No
                Due Date: 2021-11-05
                                
                Description: i4
                Completed?: Yes
                Due Date: 2021-11-04
                                
                Description: i5
                Completed?: No
                Due Date: 2021-11-03
                
                """;

        assertEquals(expected, list.toString());
    }

    // Helper method for tests
    // Mode:
        // 0 = all items
        // 1 = complete items only
        // 2 = incomplete items only
    void compareListItemArray(List<ListItem> expected, boolean sorted, int mode)
    {
        ArrayList<ListItem> selectedList = (ArrayList<ListItem>) switch(mode)
                {
                    case 0 -> list.getAllListItems();
                    case 1 -> list.getCompletedItems();
                    case 2 -> list.getIncompleteItems();

                    default -> throw new IllegalStateException("Unexpected value: " + mode);
                };

        if(sorted)
        {
            TodoList.sortTodoList(selectedList);
        }

        for(int i = 0; i < selectedList.size(); i++)
        {
            assertEquals(expected.get(i).isItemCompleted(), selectedList.get(i).isItemCompleted());
            assertEquals(expected.get(i).getDescription(), selectedList.get(i).getDescription());
            assertEquals(expected.get(i).getDueDate(), selectedList.get(i).getDueDate());
        }
    }
}