/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import java.time.LocalDate;

public class ListItem
{
    private boolean itemCompleted;
    private String description;
    private LocalDate dueDate;

    public ListItem()
    {
        itemCompleted = false;
        description = "Description";
        dueDate = null;
    }

    // Due date provided
    public ListItem(boolean itemCompleted, String description, LocalDate dueDate)
    {
        this.itemCompleted = itemCompleted;
        this.description = description;
        this.dueDate = dueDate;
    }

    // No due date provided
    public ListItem(boolean itemCompleted, String description)
    {
        this.itemCompleted = itemCompleted;
        this.description = description;
        this.dueDate = null;
    }

    public void setItemCompleted(boolean itemCompleted)
    {
        this.itemCompleted = itemCompleted;
    }

    public boolean isItemCompleted()
    {
        return itemCompleted;
    }

    public void setDescription(String description)
    {
        this.description =  description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDueDate(LocalDate dueDate)
    {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate()
    {
        return dueDate;
    }

    public String toString()
    {
        return "Description: " + description + "\n"
                + "Completed?: " + (isItemCompleted() ? "Yes" : "No") + "\n"
                + "Due Date: " + (dueDate != null ? getDueDate().toString() : "none") + "\n";
    }
}
