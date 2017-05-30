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
    private int pc = 0;
    public InstQueue(int Max_Item) {
        super();
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setPrefHeight(TomasuloDisplay.allheight/5);
        
        //this.setPrefWidth(TomasuloDisplay.allwidth/5);

        this.Max_item = Max_item;   
        for (int i = 0; i < Max_Item; ++ i) {
            data.add(new Inst());
        }
        TableColumn Select = new TableColumn("");  
        Select.setSortable(false);
        //elect.setMinWidth(5);  
        Select.setCellValueFactory(  
                new PropertyValueFactory<>("Select"));  
        this.setEditable(true);
        
        TableColumn Name = new TableColumn("Name");  
        //Name.setMinWidth(50);  
        Name.setCellValueFactory(  
                new PropertyValueFactory<>("Name"));  
        TableColumn Id = new TableColumn("ID");  
        //Id.setMinWidth(50);  
        Id.setCellValueFactory(  
                new PropertyValueFactory<>("Id")); 
        Id.setSortable(false);

        TableColumn Ex = new TableColumn("EX");  
        //Ex.setMinWidth(50);  
        Ex.setCellValueFactory(  
                new PropertyValueFactory<>("Ex")); 
        Ex.setSortable(false);
        
        TableColumn Wb = new TableColumn("WB");  
        //Wb.setMinWidth(50);  
        Wb.setCellValueFactory(  
                new PropertyValueFactory<>("Wb")); 
        Wb.setSortable(false);
        
        Name.setCellFactory(TextFieldTableCell.<Inst>forTableColumn()); 
        Id.setCellFactory(TextFieldTableCell.<Inst>forTableColumn()); 
        Ex.setCellFactory(TextFieldTableCell.<Inst>forTableColumn());  
        Wb.setCellFactory(TextFieldTableCell.<Inst>forTableColumn());  
        Name.setEditable(true);
        Name.setOnEditCommit(  
           new EventHandler<TableColumn.CellEditEvent<Inst, String> >() {
            @Override
            public void handle(TableColumn.CellEditEvent<Inst, String> t) {
                t.getRowValue().setName(t.getNewValue());
            }
        }); 
        
        Id.setEditable(true);
        Id.setOnEditCommit(  
           new EventHandler<TableColumn.CellEditEvent<Inst, String> >() {
            @Override
            public void handle(TableColumn.CellEditEvent<Inst, String> t) {
                t.getRowValue().setId(t.getNewValue());
            }
        }); 
        Ex.setEditable(true);
        Ex.setOnEditCommit(  
           new EventHandler<TableColumn.CellEditEvent<Inst, String> >() {
            @Override
            public void handle(TableColumn.CellEditEvent<Inst, String> t) {
                t.getRowValue().setEx(t.getNewValue());
            }
        }); 
        Wb.setEditable(true);
        Wb.setOnEditCommit(  
           new EventHandler<TableColumn.CellEditEvent<Inst, String> >() {
            @Override
            public void handle(TableColumn.CellEditEvent<Inst, String> t) {
                t.getRowValue().setWb(t.getNewValue());
            }
        });  
   
      
        
        this.setItems(data);  
        data.get(pc).SetSelect("->");

        this.getColumns().addAll(Select, Name, Id, Ex, Wb);

        
    }
    
    public void setPc() {
        
        if (pc>=Max_item) return;
        data.get(pc).SetSelect("");
        pc += 1;
        if (pc>=Max_item) return;
        data.get(pc).SetSelect("->");

    }
}
