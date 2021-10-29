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
        description = "";
        dueDate = null;
    }

    public ListItem(boolean itemCompleted, String description, LocalDate dueDate)
    {
        this.itemCompleted = itemCompleted;
        this.description = description;
        this.dueDate = dueDate;
    }

    public ListItem(boolean itemCompleted, String description)
    {
        this.itemCompleted = itemCompleted;
        this.description = description;
        this.dueDate = null;
    }

    public void setItemCompleted(boolean itemCompleted)
    {
        // update itemCompleted
    }

    public boolean isItemCompleted()
    {
//        return itemCompleted
        return false;
    }

    public void setDescription(String description)
    {
//        update description
    }

    public String getDescription()
    {
//        return description
        return "";
    }

    public void setDueDate(LocalDate dueDate)
    {
        // update due date
    }

    public LocalDate getDueDate()
    {
//        return dueDate
        return null;
    }
}
