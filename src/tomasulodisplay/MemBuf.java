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
public class MemBuf {
	static int MAXNUM = 6;
	public SimpleStringProperty[] items = null;
	
    public MemBuf() {
    	items = new SimpleStringProperty[MAXNUM];
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i] = new SimpleStringProperty("");
    }
    
    public String getName() {
        return items[0].get();
    }
    
    public SimpleStringProperty NameProperty() {
        return items[0];
    }
    
    public String getBusy() {
        return items[1].get();
    }
    
    public SimpleStringProperty BusyProperty() {
        return items[1];
    }
    
    public String getIns() {
        return items[2].get();
    }   
    
    public SimpleStringProperty InsProperty() {
        return items[2];
    }
    
    public String getTime() {
        return items[3].get();
    }
    
    public SimpleStringProperty TimeProperty() {
        return items[3];
    }
    
    public String getReg() {
        return items[4].get();
    }  
    
    public SimpleStringProperty RegProperty() {
        return items[5];
    }
    
    public String getAddr() {
        return items[5].get();
    }  
    
    public void setData(String[] data) {
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i].set(data[i]);
    }
}
