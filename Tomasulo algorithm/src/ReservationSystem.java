
public class ReservationSystem {
	record[]records;
	RegisterFile regFile;
	record execute() {
		record ret=null;
		for(int i=0;i<records.length;i++) {
			if(records[i].busy) {
				records[i].run();
				if(ret==null && records[i].remCycles==0) {
					ret=records[i];
				}
			}
		}
		return ret;
	}
	void print() {
		for(record r:records) {
			System.out.printf("Tag %s%d busy : %s , operation : %s , Vj : %d , Qj :%d , Vk : %d , Qk :%d , A : %d , Remaining cycles : %d\n",r.op,r.idx,r.busy,r.op,r.Vj,r.Qj,r.Vk,r.Qk,r.Address,r.remCycles);
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
				
				if(!ins.op.equals("sw"))
					regFile.RegWait(ins.des, ins.op+""+i);
				
				Integer operand1=ins.j;
				if(regFile.regs[operand1].Qi==null) {
					records[i].Vj=operand1;
				}
				else {
					records[i].Qj=regFile.regs[operand1].Qi;
				}
				
				Integer operand2=ins.k;
				if(regFile.regs[operand2].Qi==null) {
					records[i].Vk=operand2;
				}
				else {
					records[i].Qk=regFile.regs[operand2].Qi;
				}
				
				break;
			}
		}
	}
}
