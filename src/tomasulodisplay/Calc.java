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
	
    public Calc() {
    	items = new SimpleStringProperty[MAXNUM];
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i] = new SimpleStringProperty("");
    }
    
    public SimpleStringProperty NameProperty() {
        return items[0];
    }
    
    public SimpleStringProperty BusyProperty() {
        return items[1];
    }
    
    public SimpleStringProperty InsProperty() {
        return items[2];
    }
    
    public SimpleStringProperty TimeProperty() {
        return items[3];
    }
    
    public SimpleStringProperty Val1Property() {
        return items[4];
    }

    public SimpleStringProperty Val2Property() {
        return items[5];
    }
    
    public void setData(String[] data) {
    	for(int i = 0; i < MAXNUM; ++i)
    		items[i].set(data[i]);
    }
}
