package tomasulodisplay;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AsmLineQueue extends TableView<AsmLine>{
	private final ObservableList<AsmLine> data = FXCollections.observableArrayList();
	@SuppressWarnings("rawtypes")
	private TableColumn[] cols = null;
	
	@SuppressWarnings("unchecked")
	public AsmLineQueue(int num){
		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.setPrefHeight(TomasuloDisplay.allheight / 5);
		data.add(new AsmLine(num));
		cols = new TableColumn[num + 1];
		for (int i = 0; i < num; ++i) {
			cols[i] = new TableColumn<Object, Object>("P" + i);
			cols[i].setCellValueFactory(new PropertyValueFactory<Object, Object>("P" + i));
			cols[i].setSortable(false);
		}
		cols[num] = new TableColumn<Object, Object>("Wb");
		cols[num].setCellValueFactory(new PropertyValueFactory<Object, Object>("Wb"));
		cols[num].setSortable(false);
		
		this.setItems(data);
		this.getColumns().addAll(cols);
                this.getSelectionModel().setCellSelectionEnabled(false);	
        }
	
	public void setData(String[][] datas){
		data.get(0).setData(datas);
	}
}
