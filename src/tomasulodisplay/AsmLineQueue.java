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
		cols = new TableColumn[num + 2];
		
		cols[0] = new TableColumn<Object, Object>("Recv");
		cols[0].setCellValueFactory(new PropertyValueFactory<Object, Object>("Recv"));
		cols[0].setSortable(false);
		for (int i = 1; i <= num; ++i) {
			cols[i] = new TableColumn<Object, Object>("P" + i);
			cols[i].setCellValueFactory(new PropertyValueFactory<Object, Object>("P" + i));
			cols[i].setSortable(false);
		}
		cols[num + 1] = new TableColumn<Object, Object>("Wb");
		cols[num + 1].setCellValueFactory(new PropertyValueFactory<Object, Object>("Wb"));
		cols[num + 1].setSortable(false);
		
		this.setItems(data);
		this.getColumns().addAll(cols);
                this.getSelectionModel().setCellSelectionEnabled(false);	
        }
	
	public void setData(String[][] datas){
		data.get(0).setData(datas);
	}
}
