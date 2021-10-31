/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import javafx.beans.property.IntegerProperty;

import java.util.ArrayList;
import java.util.List;

public class TodoList
{
    private String title;
    private final ArrayList<ListItem> listItems;
    IntegerProperty listSize;

    public TodoList()
    {
        title = "";
        listItems = new ArrayList<>();

        // Add empty first item to list
        addItemToList();
        listSize.set(1);
    }

    public TodoList(String title, List<ListItem> listItems)
    {
        this.title = title;
        this.listItems = (ArrayList<ListItem>) listItems;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void addItemToList()
    {
        // Create new ListItem object with default parameters and add it to listItems
        listItems.add(new ListItem());

        listSize.add(1);
    }

    // Return boolean to indicate success?
    public void removeListItem(int index)
    {
        // Remove listItem at index, if it exists
        listItems.remove(index);

        listSize.subtract(1);
    }

    public IntegerProperty getListSize()
    {
        return listSize;
    }

    public List<ListItem> getListItems()
    {
        // return all listItems
        return listItems;
    }

    public List<ListItem> getCompletedItems()
    {
        // Create output ArrayList
        ArrayList<ListItem> completed = new ArrayList<>();

        // Add all completed listItems to it
        for(ListItem item : listItems)
        {
            if(item.isItemCompleted())
            {
                completed.add(item);
            }
        }

        // Return this list
        return completed;
    }

    public List<ListItem> getIncompleteItems()
    {
        // Create output ArrayList
        ArrayList<ListItem> incomplete = new ArrayList<>();

        // Add all incomplete listItems to it
        for(ListItem item : listItems)
        {
            if(!item.isItemCompleted())
            {
                incomplete.add(item);
            }
        }

        // Return this list
        return incomplete;
    }

    public String toString()
    {
        StringBuilder buffer = new StringBuilder();

        buffer.append("Title: ");
        buffer.append(getTitle());
        buffer.append('\n');

        for(ListItem item : listItems)
        {
            buffer.append(item.toString());
            buffer.append('\n');
        }

        return buffer.toString();
    }
}
