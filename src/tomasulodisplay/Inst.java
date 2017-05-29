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
    public SimpleStringProperty Desti;  
    public SimpleStringProperty Sourcej;
    public SimpleStringProperty Sourcek;
    public Inst() {
        Select = new SimpleStringProperty("");
        Name = new SimpleStringProperty("1");
        Desti = new SimpleStringProperty("2");
        Sourcej = new SimpleStringProperty("3");
        Sourcek = new SimpleStringProperty("4");
        
    }
    
     public String getSelect() {
        return Select.get();
    }
    
     
    //　一定要有get方法
    public String getName() {
        return Name.get();
    }
    
    public String getDesti() {
        return Desti.get();
    }   
    
    public String getSourcej() {
        return Sourcej.get();
    }
    
    public String getSourcek() {
        return Sourcek.get();
    }
    
    public void setName(String fName) {
        Name.set(fName);
    }
    
    public void setDesti(String fDesti) {
        Desti.set(fDesti);
    }
    
    public void setSourcej(String fSourcej) {
        Sourcej.set(fSourcej);
    }
    
    public void setSourcek(String fSourcek) {
        Sourcek.set(fSourcek);
    }
    
    public void SetSelect(String fSelect) {
        Select.set(fSelect);
    }

}
