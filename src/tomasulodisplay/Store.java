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
public class Store {
   public SimpleStringProperty Id;
    public SimpleStringProperty Busy;  
    public SimpleStringProperty Address;
    public SimpleStringProperty Cache;
    public Store(int id) {
        Id = new SimpleStringProperty("Store"+id);
        Busy = new SimpleStringProperty("1");
        Address = new SimpleStringProperty("2");
        Cache = new SimpleStringProperty("3");
        
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
    
    
    public void setBusy(String fBusy) {
        Busy.set(fBusy);
    }
    
    public void setAddress(String fAddress) {
        Address.set(fAddress);
    }
    
    public void setCache(String fCache) {
        Cache.set(fCache);
    }
  
}
