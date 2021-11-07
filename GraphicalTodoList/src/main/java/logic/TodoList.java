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
//    private String title;
    private final ArrayList<ListItem> listItemsData;
    private final ObservableList<ListItem> listItems;
    private final SimpleIntegerProperty listSize = new SimpleIntegerProperty();

    public TodoList()
    {
//        title = "";
        listItemsData = new ArrayList<>();
        listItems = FXCollections.observableList(listItemsData);

        // Add listener to list size
        listItems.addListener((ListChangeListener<ListItem>) c -> listSize.set(listItems.size()));

        // Add empty first item to list
        addItemToList();
    }

    public TodoList(List<ListItem> listItemsData)
    {
//        this.title = title;
        this.listItemsData = (ArrayList<ListItem>) listItemsData;
        listItems = FXCollections.observableList(listItemsData);

        listSize.set(listItems.size());

        // Add listener to list size
        listItems.addListener((ListChangeListener<ListItem>) c -> listSize.set(listItems.size()));
    }

//    public void setTitle(String title)
//    {
//        this.title = title;
//    }
//
//    public String getTitle()
//    {
//        return title;
//    }

    public void addItemToList()
    {
        // Create new ListItem object with default parameters and add it to listItems
        listItems.add(new ListItem());
    }

    // Return boolean to indicate success?
    public void removeListItem(ListItem item)
    {
        // Remove listItem at index, if it exists
        listItems.remove(item);
    }

    public SimpleIntegerProperty getListSize()
    {
        return listSize;
    }

    public List<ListItem> getAllListItems(boolean sorted)
    {
        // Even though no items are filtered in this case, listItems is still
        // wrapped to prevent its reference from being leaked.
        // Also ensures consistency of return types among other getXListItems methods.
        ArrayList<ListItem> allItems = new ArrayList<>(listItems);

        if(sorted)
        {
            sortTodoList(allItems);
        }

        // return all listItems
        return allItems;
    }

    public List<ListItem> getCompletedItems(boolean sorted)
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

        if(sorted)
        {
            sortTodoList(completed);
        }

        // Return this list
        return completed;
    }

    public List<ListItem> getIncompleteItems(boolean sorted)
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

        if(sorted)
        {
            sortTodoList(incomplete);
        }

        // Return this list
        return incomplete;
    }

    // Sorts list of ListItems by due date
    private static void sortTodoList(List<ListItem> items)
    {
        items.sort((o1, o2) ->
        {
            // Null dates are equal
            if(o1.getDueDate() == null && o2.getDueDate() == null)
            {
                return 0;
            }
            // First date is null, second is non-null
            // Null dates are "less" than non-null dates
            else if(o1.getDueDate() == null)
            {
                return -1;
            }
            // First date is non-null, second is null
            else if(o2.getDueDate() == null)
            {
                return 1;
            }

            return o1.getDueDate().compareTo(o2.getDueDate());
        });
    }

    public void clear()
    {
        listItems.clear();
    }

    public String toString()
    {
        StringBuilder buffer = new StringBuilder();

//        buffer.append("Title: ");
//        buffer.append(getTitle());
//        buffer.append('\n');

        for(ListItem item : listItems)
        {
            buffer.append(item.toString());
            buffer.append('\n');
        }

        return buffer.toString();
    }
}
