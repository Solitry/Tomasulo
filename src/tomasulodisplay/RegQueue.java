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

public class RegQueue extends TableView<Reg> {
    private final ObservableList<Reg> data =  
        FXCollections.observableArrayList(  
             );  
    private int Max_item;
    private int pc = 0;
    public RegQueue(int Max_Item) {
        super();
        this.Max_item = Max_item;
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setPrefHeight(TomasuloDisplay.allheight/3);

        for (int i = 0; i < Max_Item; ++ i) {
            data.add(new Reg(i));
        }
        
        TableColumn Name = new TableColumn("Name");  
        //Name.setMinWidth(50);  
        Name.setCellValueFactory(  
                new PropertyValueFactory<>("Name")); 
        Name.setSortable(false);
        TableColumn Value = new TableColumn("Value");  
        //Value.setMinWidth(50);  
        Value.setCellValueFactory(  
            new PropertyValueFactory<>("Value")); 
        Value.setSortable(false);

      
        this.setItems(data);  
        this.getColumns().addAll(Name, Value);

        
    }
    
}