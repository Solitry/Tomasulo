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
public class InstQueue extends TableView<Inst> {
	private final ObservableList<Inst> data = FXCollections.observableArrayList();
	static private String[] items = new String[] { "St", "Ins", "ID", "EN", "EX", "WB" };
	@SuppressWarnings("rawtypes")
	private TableColumn[] cols = null;

	@SuppressWarnings("unchecked")
	public InstQueue() {
		super();
		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.setPrefHeight(TomasuloDisplay.allheight / 5);

                //this.setPrefWidth(TomasuloDisplay.allwidth/5);

                
		cols = new TableColumn[items.length];
		for (int i = 0; i < cols.length; ++i) {
			cols[i] = new TableColumn<Object, Object>(items[i]);
			cols[i].setCellValueFactory(new PropertyValueFactory<Object, Object>(items[i]));
			cols[i].setSortable(false);
		}

		this.setItems(data);
		this.getColumns().addAll(cols);
                //this.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);  
                  
                
        }

	public void setData(String[][] datas) {
		data.clear();
		for (int i = 0; i < datas.length; ++i){
			Inst ins = new Inst();
			ins.setData(datas[i]);
			data.add(ins);
		}
		this.setItems(data);
        for (int i = 0; i < datas.length; ++i){
    		if (datas[i][0]=="->") {
                this.getSelectionModel().select(i);
                if  (i%4==0)
                this.scrollTo(i);

            }
        }
	}
}
