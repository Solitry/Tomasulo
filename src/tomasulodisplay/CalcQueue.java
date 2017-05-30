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

public class CalcQueue extends TableView<Calc> {

	private final ObservableList<Calc> data = FXCollections.observableArrayList();
	private int Max_item;
	
	static private String[] items = new String[] { "Name", "Busy", "Ins", "Time", "Val1", "Val2"};
	private TableColumn[] cols = null;

	public CalcQueue(String AddnameString, int Max_Item) {
		super();
		this.Max_item = Max_Item;
		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.setPrefHeight(TomasuloDisplay.allheight / 5);

		for (int i = 0; i < Max_Item; ++i)
			data.add(new Calc());

		cols = new TableColumn[items.length];
		for (int i = 0; i < cols.length; ++i) {
			cols[i] = new TableColumn(items[i]);
			cols[i].setCellValueFactory(new PropertyValueFactory(items[i]));
			cols[i].setSortable(false);
		}
		this.setItems(data);
		this.getColumns().addAll(cols);
	}
	
	public void setData(String[][] datas){
		for(int i = 0; i < Max_item; ++i)
			data.get(i).setData(datas[i]);
	}
}
