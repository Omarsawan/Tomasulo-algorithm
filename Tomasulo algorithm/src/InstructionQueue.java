import java.util.ArrayList;

public class InstructionQueue {
	int curIdx;
	static class Instructions{
	String op; //operation
	Integer des; // destination register
	Integer j;
	Integer k;
	int issue ; // issue number
	int execstart; // time exec starts at
	int execfin;// time exec finishes at
	int write; // the time in which the result is writter on the bus	
	public Instructions(String a, Integer b,Integer c,Integer d,int x) {
		op=a;
		des=b;
		j=c;
		k=d;
		issue=x;
	}
	}
	static ArrayList<Instructions> ints=new ArrayList<InstructionQueue.Instructions>();
	// to add an instruction to the instruction queue
	void addInstruction(Instructions i){
		ints.add(i);
	}
	Instructions createInstruction(String a, Integer b,Integer c,Integer d,int x) {
		return new Instructions(a, b, c, d, x);
	}
	
	Instructions nxtInstruction() {
		return curIdx>=ints.size()?null:ints.get(curIdx);
	}
	void fetched() {
		curIdx++;
	}
	
	// just for testing purposes 
	public static void main(String[] args) {
		Instructions i=new Instructions("ADD", 6, 8,2,3);
		InstructionQueue q=new InstructionQueue();
		q.addInstruction(i);
	}



}
