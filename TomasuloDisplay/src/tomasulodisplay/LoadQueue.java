/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tomasulodisplay;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author meepo
 */

public class LoadQueue extends TableView<Load> {
    private final ObservableList<Load> data =  
        FXCollections.observableArrayList(  
             );  
    private int Max_item;
    private int pc = 0;
    public LoadQueue(int Max_Item) {
        super();
        this.Max_item = Max_item;   
        for (int i = 0; i < Max_Item; ++ i) {
            data.add(new Load(i));
        }
        
        TableColumn Id = new TableColumn("Id");  
        Id.setMinWidth(50);  
        Id.setCellValueFactory(  
                new PropertyValueFactory<>("Id")); 
        TableColumn Busy = new TableColumn("Busy");  
        Busy.setMinWidth(50);  
        Busy.setCellValueFactory(  
                new PropertyValueFactory<>("Busy")); 
        TableColumn Address = new TableColumn("Address");  
        Address.setMinWidth(50);  
        Address.setCellValueFactory(  
                new PropertyValueFactory<>("Address")); 
        
        TableColumn Cache = new TableColumn("Cache");  
        Cache.setMinWidth(50);  
        Cache.setCellValueFactory(  
                new PropertyValueFactory<>("Cache")); 
      
        
        this.setItems(data);  
        this.getColumns().addAll(Id, Busy, Address, Cache);

        
    }
    
}