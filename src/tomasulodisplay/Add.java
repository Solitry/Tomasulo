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
public class Add {
   public SimpleStringProperty Id;
    public SimpleStringProperty Busy;  
    public SimpleStringProperty Address;
    public SimpleStringProperty Cache;
    public SimpleStringProperty Value5;
    public SimpleStringProperty Value6;

    public Add(String AddnameString, int id) {
        Id = new SimpleStringProperty(AddnameString+id);
        Busy = new SimpleStringProperty("1");
        Address = new SimpleStringProperty("2");
        Cache = new SimpleStringProperty("3");
        Value5 = new SimpleStringProperty("3");
        Value6 = new SimpleStringProperty("3");
        
    }
    
    //　一定要有get方法
    public String getId() {
        return Id.get();
    }
    
    public String getBusy() {
        return Busy.get();
    }
    
    public String getAddress() {
        return Address.get();
    }   
    
    public String getCache() {
        return Cache.get();
    }
    
    public String getValue5() {
        return Value5.get();
    }
    
    public String getValue6() {
        return Value6.get();
    }
        
    public void setBusy(String fBusy) {
        Busy.set(fBusy);
    }
    
    public void setAddress(String fAddress) {
        Address.set(fAddress);
    }
    
    public void setCache(String fCache) {
        Cache.set(fCache);
    }
    
    
    public void setValue5(String fValue5) {
        Value5.set(fValue5);
    }
    
    public void setValue6(String fValue6) {
        Value6.set(fValue6);
    }
  
}
