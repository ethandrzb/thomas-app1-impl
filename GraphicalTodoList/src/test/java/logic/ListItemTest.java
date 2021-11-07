/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ListItemTest
{
    ListItem item;

    @BeforeEach
    void init()
    {
        // Reset ListItem object to default state between tests
        item = new ListItem();
    }

    @Test
    void defaultConstructor()
    {
        // Assert default parameters
        assertFalse(item.isItemCompleted());
        assertEquals("Description", item.getDescription());
        assertNull(item.getDueDate());
    }

    @Test
    void parameterizedConstructorWithNullDate()
    {
        // Make new ListItem with parameterized constructor
        item = new ListItem(true, "test description", null);

        // Test object state
        assertTrue(item.isItemCompleted());
        assertEquals("test description", item.getDescription());
        assertNull(item.getDueDate());
    }

    @Test
    void parameterizedConstructorWithValidDate()
    {
        LocalDate date = LocalDate.now();

        // Make new ListItem with parameterized constructor
        item = new ListItem(true, "test description", date);

        // Test object state
        assertTrue(item.isItemCompleted());
        assertEquals("test description", item.getDescription());
        assertEquals(0, date.compareTo(item.getDueDate()));
    }

    @Test
    void setItemCompletedAndIsItemCompleted()
    {
        // Assert that item is incomplete by default
        assertFalse(item.isItemCompleted());

        // Set item to completed
        item.setItemCompleted(true);

        // Assert item is now complete
        assertTrue(item.isItemCompleted());
    }

    // Description length limit handled by TodoListApplicationController, not ListItem.
    @Test
    void setDescriptionAndGetDescription()
    {
        // Assert that description is "Description" by default
        assertEquals("Description", item.getDescription());

        // Set description to "test description"
        item.setDescription("test description");

        // Assert that the new description matches "test description"
        assertEquals("test description", item.getDescription());
    }

    @Test
    void setDueDateAndGetDueDate()
    {
        // Expected date = today's date
        LocalDate date = LocalDate.now();

        // Assert that dueDate is null by default
        assertNull(item.getDueDate());

        // Set dueDate to expected date
        item.setDueDate(date);

        // Assert ListItem's dueDate matches expected date
        assertEquals(0, date.compareTo(item.getDueDate()));
    }


    @Test
    void testToStringValidDate()
    {
        LocalDate date = LocalDate.now();

        String expected = "Description: toString method test description\n" +
                "Completed?: Yes\n" +
                "Due Date: " + date + '\n';

        // Generate normal item
        item = new ListItem(true, "toString method test description", date);

        assertEquals(expected, item.toString());
    }

    @Test
    void testToStringNullDate()
    {
        String expected = """
                Description: toString method test description
                Completed?: Yes
                Due Date: none
                """;

        // Generate normal item
        item = new ListItem(true, "toString method test description", null);

        assertEquals(expected, item.toString());
    }
}