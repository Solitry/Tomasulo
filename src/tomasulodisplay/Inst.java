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
    
     
    //　一定要有get方法
    public String getSt() {
        return items[0].get();
    }
    
    public String getIns() {
        return items[1].get();
    }
    
    public String getID() {
        return items[2].get();
    }   
    
    public String getEX() {
        return items[3].get();
    }
    
    public String getMEM() {
        return items[4].get();
    }
    
    public String getWB() {
        return items[5].get();
    }
    
    public void setValue(String[] data) {
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i].set(data[i]);
    }
}
