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
    private final ArrayList<ListItem> listItems;
    private final ObservableList<ListItem> listItemsObservable;
    private final SimpleIntegerProperty listSize = new SimpleIntegerProperty();

    public TodoList()
    {
        title = "";
        listItems = new ArrayList<>();
        listItemsObservable = FXCollections.observableList(listItems);

        // Add listener to list size
        listItemsObservable.addListener((ListChangeListener<ListItem>) c -> listSize.set(listItemsObservable.size()));

        // Add empty first item to list
        addItemToList();
    }

    public TodoList(String title, List<ListItem> listItems)
    {
        this.title = title;
        this.listItems = (ArrayList<ListItem>) listItems;
        listItemsObservable = FXCollections.observableList(listItems);

        // Add listener to list size
        listItemsObservable.addListener((ListChangeListener<ListItem>) c -> listSize.set(listItemsObservable.size()));
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
        listItemsObservable.add(new ListItem());
    }

    // Return boolean to indicate success?
    public void removeListItem(int index)
    {
        // Remove listItem at index, if it exists
        listItems.remove(index);
        listItemsObservable.remove(index);
    }

    public SimpleIntegerProperty getListSize()
    {
        return listSize;
    }

    public List<ListItem> getListItems()
    {
        // return all listItems
        return listItemsObservable;
    }

    public List<ListItem> getCompletedItems()
    {
        // Create output ArrayList
        ArrayList<ListItem> completed = new ArrayList<>();

        // Add all completed listItems to it
        for(ListItem item : listItemsObservable)
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
        for(ListItem item : listItemsObservable)
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

        for(ListItem item : listItemsObservable)
        {
            buffer.append(item.toString());
            buffer.append('\n');
        }

        return buffer.toString();
    }
}
