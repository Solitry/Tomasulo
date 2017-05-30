/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tomasulodisplay;

import javafx.beans.property.SimpleStringProperty; 
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
	private int Max_item;

	static private String[] items = new String[] { "St", "Ins", "ID", "EX", "MEM", "WB" };
	private TableColumn[] cols = null;

	public InstQueue() {
		super();
		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.setPrefHeight(TomasuloDisplay.allheight / 5);
		// this.setPrefWidth(TomasuloDisplay.allwidth/5);

		cols = new TableColumn[items.length];
		for (int i = 0; i < cols.length; ++i) {
			cols[i] = new TableColumn(items[i]);
			cols[i].setCellValueFactory(new PropertyValueFactory(items[i]));
			cols[i].setSortable(false);
		}

		this.setItems(data);
		this.getColumns().addAll(cols);

	}

	public void setData(String[][] datas) {
		for (int i = data.size(); i < datas.length; ++i)
			data.add(new Inst());
		for (int i = 0; i < data.size(); ++i)
			data.get(i).setData(datas[i]);
	}
}
