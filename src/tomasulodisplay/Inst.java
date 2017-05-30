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
    public SimpleStringProperty Select;
    public SimpleStringProperty Name;  
    public SimpleStringProperty Id;  
    public SimpleStringProperty Ex;
    public SimpleStringProperty Wb;
    public Inst() {
        Select = new SimpleStringProperty("");
        Name = new SimpleStringProperty("1");
        Id = new SimpleStringProperty("2");
        Ex = new SimpleStringProperty("3");
        Wb = new SimpleStringProperty("4");
        
    }
    
     public String getSelect() {
        return Select.get();
    }
    
     
    //　一定要有get方法
    public String getName() {
        return Name.get();
    }
    
    public String getId() {
        return Id.get();
    }   
    
    public String getEx() {
        return Ex.get();
    }
    
    public String getWb() {
        return Wb.get();
    }
    
    public void setName(String fName) {
        Name.set(fName);
    }
    
    public void setId(String fId) {
        Id.set(fId);
    }
    
    public void setEx(String fEx) {
        Ex.set(fEx);
    }
    
    public void setWb(String fWb) {
        Wb.set(fWb);
    }
    
    public void SetSelect(String fSelect) {
        Select.set(fSelect);
    }

}
