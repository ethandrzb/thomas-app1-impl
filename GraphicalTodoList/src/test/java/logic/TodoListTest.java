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
        assertFalse(list.getAllListItems(false).get(1).isItemCompleted());
        assertEquals("Description", list.getAllListItems(false).get(1).getDescription());
        assertNull(list.getAllListItems(false).get(1).getDueDate());
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
        compareListItemArray(expected, false);
    }

    // TODO: Test both sorted and unsorted cases
    @Test
    void getAllListItemsUnsorted()
    {
        ArrayList<ListItem> expected = new ArrayList<>(testListItems);

        list = new TodoList(new ArrayList<>(testListItems));

        // Unsorted case
        compareListItemArray(expected, false);

        // Sorted case
        expected.clear();

        // Generate expected test data
        expected.add(new ListItem(false, "i5", LocalDate.parse("2021-11-03", dateFormatter)));
        expected.add(new ListItem(true, "i4", LocalDate.parse("2021-11-04", dateFormatter)));
        expected.add(new ListItem(false, "i3", LocalDate.parse("2021-11-05", dateFormatter)));
        expected.add(new ListItem(true, "i2", LocalDate.parse("2021-11-06", dateFormatter)));
        expected.add(new ListItem(false, "i1", LocalDate.parse("2021-11-07", dateFormatter)));

        // Assert sorted list matches expected order
        compareListItemArray(expected, true);
    }

    @Test
    void getAllListItemsSorted()
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
        compareListItemArray(expected, true);
    }

    // TODO: Test both sorted and unsorted cases
    @Test
    void getCompletedItems()
    {
        // Make ArrayList of ListItems to use as test data, some complete and some incomplete

        // Make a new TodoList with this ArrayList

        // Get completed ListItems

        // Assert that all ListItems returned are marked as complete

        // Assert incomplete ListItems from test data are not present in list returned by getCompletedItems
    }

    // TODO: Test both sorted and unsorted cases
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

    @Test
    void clear() {
    }

    @Test
    void testToString() {
    }

    void compareListItemArray(List<ListItem> expected, boolean sorted)
    {
        for(int i = 0; i < list.getListSize().get(); i++)
        {
            assertEquals(expected.get(i).isItemCompleted(), list.getAllListItems(sorted).get(i).isItemCompleted());
            assertEquals(expected.get(i).getDescription(), list.getAllListItems(sorted).get(i).getDescription());
            assertEquals(expected.get(i).getDueDate(), list.getAllListItems(sorted).get(i).getDueDate());
        }
    }
}