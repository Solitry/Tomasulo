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
public class Calc {
	static int MAXNUM = 6;
	public SimpleStringProperty[] items = null;
	
    public Calc(String AddnameString, int id) {
    	items = new SimpleStringProperty[MAXNUM];
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i] = new SimpleStringProperty("");
    	items[0].set(AddnameString + id);
    	items[1].set("no");
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
    
    public String getVal1() {
        return items[4].get();
    }
    
    public String getVal2() {
        return items[5].get();
    }
        
    public void setData(String[] data) {
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i].set(data[i]);
    }
}
