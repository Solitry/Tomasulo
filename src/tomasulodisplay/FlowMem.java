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
public class FlowMem {
	static int MAXNUM = 2;
	public SimpleStringProperty[] items = null;
	
    public FlowMem() {
    	items = new SimpleStringProperty[MAXNUM];
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i] = new SimpleStringProperty("");
    }
    
    public String getAddr() {
        return items[0].get();
    }
    
    public SimpleStringProperty AddrProperty() {
        return items[0];
    }
    
    public String getVal() {
        return items[1].get();
    }
    
    public SimpleStringProperty ValProperty() {
        return items[1];
    }
    
    public void setData(String[] data) {
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i].set(data[i]);
    }
}
