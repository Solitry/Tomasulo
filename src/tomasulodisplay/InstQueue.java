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
        this.Max_item = Max_item;   
        for (int i = 0; i < Max_Item; ++ i) {
            data.add(new Inst());
        }
        TableColumn Select = new TableColumn("");  
        Select.setMinWidth(5);  
        Select.setCellValueFactory(  
                new PropertyValueFactory<>("Select"));  
        this.setEditable(true);
        
        TableColumn Name = new TableColumn("Name");  
        Name.setMinWidth(50);  
        Name.setCellValueFactory(  
                new PropertyValueFactory<>("Name"));  
        TableColumn Desti = new TableColumn("Desti");  
        Desti.setMinWidth(50);  
        Desti.setCellValueFactory(  
                new PropertyValueFactory<>("Desti")); 
        TableColumn Sourcej = new TableColumn("Sourcej");  
        Sourcej.setMinWidth(50);  
        Sourcej.setCellValueFactory(  
                new PropertyValueFactory<>("Sourcej")); 
        
        TableColumn Sourcek = new TableColumn("Sourcek");  
        Sourcek.setMinWidth(50);  
        Sourcek.setCellValueFactory(  
                new PropertyValueFactory<>("Sourcek")); 
        
        Name.setCellFactory(TextFieldTableCell.<Inst>forTableColumn()); 
        Desti.setCellFactory(TextFieldTableCell.<Inst>forTableColumn()); 
        Sourcej.setCellFactory(TextFieldTableCell.<Inst>forTableColumn());  
        Sourcek.setCellFactory(TextFieldTableCell.<Inst>forTableColumn());  
        Name.setEditable(true);
        Name.setOnEditCommit(  
           new EventHandler<TableColumn.CellEditEvent<Inst, String> >() {
            @Override
            public void handle(TableColumn.CellEditEvent<Inst, String> t) {
                t.getRowValue().setName(t.getNewValue());
            }
        }); 
        
        Desti.setEditable(true);
        Desti.setOnEditCommit(  
           new EventHandler<TableColumn.CellEditEvent<Inst, String> >() {
            @Override
            public void handle(TableColumn.CellEditEvent<Inst, String> t) {
                t.getRowValue().setDesti(t.getNewValue());
            }
        }); 
        Sourcej.setEditable(true);
        Sourcej.setOnEditCommit(  
           new EventHandler<TableColumn.CellEditEvent<Inst, String> >() {
            @Override
            public void handle(TableColumn.CellEditEvent<Inst, String> t) {
                t.getRowValue().setSourcej(t.getNewValue());
            }
        }); 
        Sourcek.setEditable(true);
        Sourcek.setOnEditCommit(  
           new EventHandler<TableColumn.CellEditEvent<Inst, String> >() {
            @Override
            public void handle(TableColumn.CellEditEvent<Inst, String> t) {
                t.getRowValue().setSourcek(t.getNewValue());
            }
        });  
   
      
        
        this.setItems(data);  
        data.get(pc).SetSelect("->");

        this.getColumns().addAll(Select, Name, Desti, Sourcej, Sourcek);

        
    }
    
    public void setPc() {
        
        if (pc>=Max_item) return;
        data.get(pc).SetSelect("");
        pc += 1;
        if (pc>=Max_item) return;
        data.get(pc).SetSelect("->");

    }
}
