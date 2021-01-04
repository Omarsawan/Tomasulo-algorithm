import java.util.ArrayList;

public class InstructionQueue {
static class Instructions{
String op; //operation
String des; // destination register
String j;
String k;
int issue ; // issue number
int execstart; // time exec starts at
int execfin;// time exec finishes at
int write; // the time in which the result is writter on the bus	
public Instructions(String a, String b,String c,String d,int x) {
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


// just for testing purposes 
public static void main(String[] args) {
	Instructions i=new Instructions("ADD", "F6", "F8","F2",3);
	InstructionQueue q=new InstructionQueue();
	q.addInstruction(i);
}



}
