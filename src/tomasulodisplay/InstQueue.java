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
public class InstQueue extends TableView<Inst> {
    private final ObservableList<Inst> data =  
        FXCollections.observableArrayList(  
             );  
    private int Max_item;
    
	static private String[] items = new String[] {"St", "Ins", "ID", "EX", "MEM", "WB"};
	private TableColumn[] cols = null;
    
    public InstQueue(int Max_Item) {
        super();
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setPrefHeight(TomasuloDisplay.allheight/5);
        //this.setPrefWidth(TomasuloDisplay.allwidth/5);

        this.Max_item = Max_item;   
        for (int i = 0; i < Max_Item; ++ i) 
            data.add(new Inst());
        
		cols = new TableColumn[items.length];
		for (int i = 0; i < cols.length; ++i) {
			cols[i] = new TableColumn(items[i]);
			cols[i].setCellValueFactory(new PropertyValueFactory<>(items[i]));
			cols[i].setSortable(false);
		}
        
        this.setItems(data);
        this.getColumns().addAll(cols);

    }
}
