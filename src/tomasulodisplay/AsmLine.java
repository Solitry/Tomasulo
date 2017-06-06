package tomasulodisplay;

import javafx.beans.property.SimpleStringProperty;

public class AsmLine {
	static int MAXNUM = 6;
	public SimpleStringProperty[] items = null;
	public SimpleStringProperty wb = null;
	int num = 0;
	
    public AsmLine(int num) {
    	this.num = num;
    	items = new SimpleStringProperty[MAXNUM];
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i] = new SimpleStringProperty("");
    }
    
    public SimpleStringProperty P0Property() {
        return items[0];
    }
    
    public SimpleStringProperty P1Property() {
        return items[1];
    }
    
    public SimpleStringProperty P2Property() {
        return items[2];
    }
    
    public SimpleStringProperty P3Property() {
        return items[3];
    }
    
    public SimpleStringProperty P4Property() {
        return items[4];
    }
    
    public SimpleStringProperty P5Property() {
        return items[5];
    }
    
    public SimpleStringProperty WbProperty(){
    	return wb;
    }
    
    public void setData(String[][] datas) {
    	for(int i = 0; i < num; ++i)
    		items[i].set("");
    	for(int i = 0; i < datas.length; ++i)
    		if(datas[i][3].equals("wait"))
    			wb.set(datas[i][0]);
    		else if(!datas[i][3].equals("")){
    			int t = Integer.parseInt(datas[i][3]);
    			items[num - 1 - t % num].set(datas[i][0]);
    		}
    }
}
