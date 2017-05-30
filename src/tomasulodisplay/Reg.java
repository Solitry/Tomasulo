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
   public SimpleStringProperty Name;
    public SimpleStringProperty Value;  
    public SimpleStringProperty Address;
    public SimpleStringProperty Cache;
    public Reg(int id) {
        Name = new SimpleStringProperty("Reg"+id);
        Value = new SimpleStringProperty("1");
    }
    
    //　一定要有get方法
    public String getName() {
        return Name.get();
    }
    
    public String getValue() {
        return Value.get();
    }
    
    
    public void setValue(String fValue) {
        Value.set(fValue);
    }
    
  
}
