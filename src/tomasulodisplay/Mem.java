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
public class Mem {
	static int MAXNUM = 6;
	public SimpleStringProperty[] items = null;
	
    public Mem(String name, int id) {
    	items = new SimpleStringProperty[MAXNUM];
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i] = new SimpleStringProperty("");
    	
        items[0] = new SimpleStringProperty(name + id);
        items[1] = new SimpleStringProperty("no");
    }
    
    //　一定要有get方法
    public String getName() {
        return items[0].get();
    }
    
    public String getBusy() {
        return items[1].get();
    }
    
    public String getIns() {
        return items[2].get();
    }   
    
    public String getTime() {
        return items[3].get();
    }
    
    public String getReg() {
        return items[4].get();
    }  
    
    public String getAddr() {
        return items[5].get();
    }  
    
    public void setData(String[] data) {
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i].set(data[i]);
    }
}
