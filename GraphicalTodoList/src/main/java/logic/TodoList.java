/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.*;

public class TodoList
{
    private String title;
    private final ArrayList<ListItem> listItemsData;
    private final ObservableList<ListItem> listItems;
    private final SimpleIntegerProperty listSize = new SimpleIntegerProperty();

    public TodoList()
    {
        title = "";
        listItemsData = new ArrayList<>();
        listItems = FXCollections.observableList(listItemsData);

        // Add listener to list size
        listItems.addListener((ListChangeListener<ListItem>) c -> listSize.set(listItems.size()));

        // Add empty first item to list
        addItemToList();
    }

    public TodoList(String title, List<ListItem> listItemsData)
    {
        this.title = title;
        this.listItemsData = (ArrayList<ListItem>) listItemsData;
        listItems = FXCollections.observableList(listItemsData);

        listSize.set(listItems.size());

        // Add listener to list size
        listItems.addListener((ListChangeListener<ListItem>) c -> listSize.set(listItems.size()));
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
    }

    // Return boolean to indicate success?
    public void removeListItem(int index)
    {
        // Remove listItem at index, if it exists
//        listItemsData.remove(index);
        listItems.remove(index);
    }

    public SimpleIntegerProperty getListSize()
    {
        return listSize;
    }

    public ListItem getListItem(int index)
    {
        return listItemsData.get(index);
    }

    public List<ListItem> getAllListItems()
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

    public void clear()
    {
        listItems.clear();
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
