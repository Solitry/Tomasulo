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
public class Reg {
	static int MAXNUM = 6;
	public SimpleStringProperty[] items = null;
	
    public Reg(int id) {
    	items = new SimpleStringProperty[MAXNUM];
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i] = new SimpleStringProperty("");
    	items[0].set("F" + id);
    	items[1].set("0");
    }
    
    //　一定要有get方法
    public String getName() {
        return items[0].get();
    }
    
    public String getValue() {
        return items[1].get();
    }
    
    public void setValue(String[] data) {
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i].set(data[i]);
    }
}
