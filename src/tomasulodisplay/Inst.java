/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tomasulodisplay;
import javafx.beans.property.SimpleStringProperty;  
/**
 *
 * @author meepo
 */
public class Inst {
	static int MAXNUM = 6;
	public SimpleStringProperty[] items = null;
	
    public Inst() {
    	items = new SimpleStringProperty[MAXNUM];
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i] = new SimpleStringProperty("");
    }
    
     
    public String getSt() {
        return items[0].get();
    }
    
    public SimpleStringProperty StProperty() {
        return items[0];
    }
    
    public String getIns() {
        return items[1].get();
    }
    
    public SimpleStringProperty InsProperty() {
        return items[1];
    }
    
    public String getID() {
        return items[2].get();
    }   
    
    public SimpleStringProperty IDProperty() {
        return items[2];
    }
    
    public String getEX() {
        return items[3].get();
    }
    
    public SimpleStringProperty EXProperty() {
        return items[3];
    }
    
    public String getMEM() {
        return items[4].get();
    }
    
    public SimpleStringProperty MEMProperty() {
        return items[4];
    }
    
    public String getWB() {
        return items[5].get();
    }
    
    public SimpleStringProperty WBProperty() {
        return items[5];
    }
    
    public void setData(String[] data) {
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i].set(data[i]);
    }
}
