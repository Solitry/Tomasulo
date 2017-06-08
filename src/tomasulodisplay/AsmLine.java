package tomasulodisplay;

import javafx.beans.property.SimpleStringProperty;
import type.Instruction;

public class AsmLine {
	static int MAXNUM = 6;
	public SimpleStringProperty[] items = null;
	public SimpleStringProperty wb = null;
	int num = 0;
	
    public AsmLine(int num) {
    	this.num = num;
    	items = new SimpleStringProperty[MAXNUM + 1];
    	for(int i = 0; i <= MAXNUM; ++i)
    		items[i] = new SimpleStringProperty("");
    	wb = new SimpleStringProperty("");
    }
    
    public SimpleStringProperty RecvProperty() {
        return items[0];
    }
    
    public SimpleStringProperty P1Property() {
        return items[1];
    }
    
    public SimpleStringProperty P2Property() {
        return items[2];
    }
    
    public SimpleStringProperty P3Property() {
        return items[3];
    }
    
    public SimpleStringProperty P4Property() {
        return items[4];
    }
    
    public SimpleStringProperty P5Property() {
        return items[5];
    }

    public SimpleStringProperty P6Property() {
        return items[6];
    }
    
    public SimpleStringProperty WbProperty(){
    	return wb;
    }
    
    public void setData(String[][] datas) {
    	for(int i = 0; i <= num; ++i)
    		items[i].set("");
    	for(int i = 0; i < datas.length; ++i)
    		if(datas[i][3].equals("wait"))
    			wb.set(datas[i][0]);
    		else if(!datas[i][3].equals("")){
    			int t = Integer.parseInt(datas[i][3]);
    			try {
					Instruction ins = new Instruction(datas[i][2], 0);
					System.out.println(datas[i][2] + " " + ins.opLabel + " " + t);
	    			if(((ins.opLabel == Instruction.INSTR_ADD_ID || ins.opLabel == Instruction.INSTR_SUB_ID) && t == 2)
	    					|| (ins.opLabel == Instruction.INSTR_MUL_ID && t == 10) || t == 40){
	    				items[0].set(datas[i][0]);
	    				continue;
	    			}
				} catch (Exception e) {
					e.printStackTrace();
				}
	    		items[num - t % num].set(datas[i][0]);
    		}
    }
}
