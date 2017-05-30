/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tomasulodisplay;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author meepo
 */

public class AddQueue extends TableView<Add> {
    private final ObservableList<Add> data =  
        FXCollections.observableArrayList(  
             );  
    private int Max_item;
    private int pc = 0;
    public AddQueue(String AddnameString, int Max_Item) {
        super();
        this.Max_item = Max_item;
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setPrefHeight(TomasuloDisplay.allheight/5);

        for (int i = 0; i < Max_Item; ++ i) {
            
            data.add(new Add(AddnameString, i));
        }
        
        TableColumn Id = new TableColumn("Id");  
        //Id.setMinWidth(50);  
        Id.setCellValueFactory(  
                new PropertyValueFactory<>("Id")); 
        Id.setSortable(false);
        TableColumn Busy = new TableColumn("Busy");  
        //Busy.setMinWidth(50);  
        Busy.setCellValueFactory(  
            new PropertyValueFactory<>("Busy")); 
        Busy.setSortable(false);

        TableColumn Address = new TableColumn("Address");  
        //Address.setMinWidth(50);  
        Address.setCellValueFactory(  
                new PropertyValueFactory<>("Address")); 
        Address.setSortable(false);
        TableColumn Cache = new TableColumn("Cache");  
        Cache.setCellValueFactory(  
                new PropertyValueFactory<>("Cache")); 
        Cache.setSortable(false);

        TableColumn Value5 = new TableColumn("Value5");  
        Cache.setCellValueFactory(  
                new PropertyValueFactory<>("Value5")); 
        Cache.setSortable(false);
        
        TableColumn Value6 = new TableColumn("Value6");  
        Cache.setCellValueFactory(  
                new PropertyValueFactory<>("Value6")); 
        Cache.setSortable(false);
        this.setItems(data);  
        this.getColumns().addAll(Id, Busy, Address, Cache,Value5,Value6);

        
    }
    
}