/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoList
{
    private String title;
    private final ArrayList<ListItem> listItems;

    public TodoList()
    {
        title = "";
        listItems = new ArrayList<>();

        // Add empty first item to list?
    }

    public TodoList(String title, List<ListItem> listItems)
    {
        this.title = title;
        this.listItems = (ArrayList<ListItem>) listItems;
    }

    public void setTitle(String title)
    {
        // update list title
    }

    public String getTitle()
    {
//        return title
        return "";
    }

    public void addItemToList()
    {
        // Create new ListItem object with default parameters

        // Add this object to listItems
    }

    // Return boolean to indicate success?
    public void removeListItem(int index)
    {
        // Remove listItem at index, if it exists
    }

    public List<ListItem> getListItems()
    {
        // return all listItems
        return Collections.emptyList();
    }

    public List<ListItem> getCompletedItems()
    {
        // Create output ArrayList

        // Add all completed listItems to it

        // Return this list

        return Collections.emptyList();
    }

    public List<ListItem> getIncompleteItems()
    {
        // Create output ArrayList

        // Add all incomplete listItems to it

        // Return this list

        return Collections.emptyList();
    }
}
