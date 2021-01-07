
public class ReservationSystem {
	record[]records;
	RegisterFile regFile;
	record execute() {
		record ret=null;
		for(int i=0;i<records.length;i++) {
			if(records[i].busy) {
				records[i].run();
				if(ret==null && records[i].remCycles==0) {
					System.out.println("lol");
					ret=records[i];
				}
			}
		}
		System.out.println("ret "+ret);
		return ret;
	}
	void handle(String tag) {
		for(int i=0;i<records.length;i++) {
			if(records[i].busy) {
			if(tag.equals(records[i].Qj)) {
				records[i].Qj=null;
				records[i].Vj=15;
			}
			if(tag.equals(records[i].Qk)) {
				records[i].Qk=null;
				records[i].Vk=15;
			}
			}
		}
		
	}
	void print() {
		for(record r:records) {
		System.out.println(r.Qj);
			System.out.printf("Tag %s%d busy : %s , operation : %s , Vj : %d , Qj :%s , Vk : %d , Qk :%s , A : %d , Remaining cycles : %d\n",r.op,r.idx,r.busy,r.op,r.Vj,r.Qj,r.Vk,r.Qk,r.Address,r.remCycles);
		}
	}
	public ReservationSystem(int maxSize,RegisterFile reg) {
		records=new record[maxSize];
		for(int i=0;i<maxSize;i++) {
			records[i]=new record(i);
		}
		regFile=reg;
	}
	boolean existSpace() {
		boolean yes=false;
		for(record r:records) {
			yes|=(!r.busy);
		}
		return yes;
	}
	boolean existBusy() {
		boolean yes=false;
		for(record r:records) {
			yes|=(r.busy);
		}
		return yes;
	}
	void insert(InstructionQueue.Instructions ins,int cycles) {
		for(int i=0;i<records.length;i++) {
			if(!records[i].busy) {
				records[i]=new record(ins.op, cycles, i);
				
				
				Integer operand1=ins.j;
				if(!ins.op.equals("lw")&&regFile.regs[operand1].Qi==null) {
					records[i].Vj=operand1;
				}
				else if(!ins.op.equals("lw")){
					records[i].Qj=regFile.regs[operand1].Qi;
				}
				else {
					records[i].Address=operand1;	
					records[i].Vj=operand1;
					
				}
				
				Integer operand2=ins.k;
				if(!ins.op.equals("sw")&&regFile.regs[operand2].Qi==null) {
					records[i].Vk=operand2;
				}
				else if(!ins.op.equals("sw")) {
					records[i].Qk=regFile.regs[operand2].Qi;
				}
				else {
					
					records[i].Address=ins.des;
					records[i].Vk=operand2;
					
				}
				if(!ins.op.equals("sw"))
					regFile.RegWait(ins.des, ins.op+""+i);
				
				
				break;
			}
		}
	}
}
