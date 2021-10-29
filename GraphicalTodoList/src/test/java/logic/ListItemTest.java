/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListItemTest
{

    @BeforeEach
    void init()
    {
        // Reset ListItem object to default state between tests
    }

    @Test
    void setItemCompletedAndIsItemCompleted()
    {
        // Assert that item is incomplete by default

        // Set item to completed

        // Assert item is now complete
    }

    @Test
    void setDescriptionAndGetDescription()
    {
        // Assert that description is empty string by default

        // Set description to "test description"

        // Assert that the new description matches "test description"
    }

    @Test
    void setDueDate_fromNull()
    {
        // Assert that dueDate is null

        // Expected date = today's date

        // Set dueDate to expected date

        // Assert ListItem's dueDate matches expected date
    }

    @Test
    void setDueDate_initialized()
    {
        // Create listItem object with dueDate initialized to some date that isn't today's date (expected value TBD)

        // Assert that dueDate is expected date

        // Expected date = today's date

        // Set dueDate to expected date

        // Assert ListItem's dueDate matches expected date after modification
    }
}