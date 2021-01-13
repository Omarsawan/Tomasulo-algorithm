import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Simulator {
	static class Memory{
		int[]mem;
		Memory(int size){
			mem=new int[size];
			for(int i=0;i<size;i++) {
				mem[i]=(int)(Math.random()*15);
			}
		}
		int read(int add) {
			return mem[add];
		}
		void write(int add,int val) {
			mem[add]=val;
		}
	}
	static int whoWrote;
	static record executeReservationSystems(ReservationSystem []systems) {
		boolean wrote=false;
		record writingNow=null;
		//add res system
		if(!wrote)
			writingNow=systems[0].execute();
		else {
			systems[0].execute();
		}
		if(!wrote && writingNow!=null) {
			wrote=true;
			whoWrote=0;
		}
		
		//mul res system
		if(!wrote)
			writingNow=systems[1].execute();
		else {
			systems[1].execute();
		}
		if(!wrote && writingNow!=null) {
			wrote=true;
			whoWrote=1;
		}
		
		//load res system
		if(!wrote)
			writingNow=systems[2].execute();
		else {
			systems[2].execute();
		}
		if(!wrote && writingNow!=null) {
			wrote=true;
			whoWrote=2;
		}
		
		//store res system
		
		if(!wrote)
			writingNow=systems[3].execute();
		else {
			systems[3].execute();
		}
		if(!wrote && writingNow!=null) {
			wrote=true;
			whoWrote=3;
		}
		
		return writingNow;
	}
	static void dependenciesReservationSystems(ReservationSystem []systems,String nxtTag) {
		systems[0].handle(nxtTag);
		systems[1].handle(nxtTag);
		systems[2].handle(nxtTag);
		systems[3].handle(nxtTag);
	}
	static PrintWriter pw;
	public static void main(String[] args) throws FileNotFoundException {
		pw=new PrintWriter(new File("Output"));
		int addCycles=2;
		int mulCycles=5;
		int loadCycles=2;
		int storeCycles=2;
		
		
		
		InstructionQueue insQ=new InstructionQueue();
		RegisterFile regFile=new RegisterFile(32,pw);
		ReservationSystem addResSystem=new ReservationSystem(5,regFile,pw);
		ReservationSystem mulResSystem=new ReservationSystem(5,regFile,pw);
		ReservationSystem loadResSystem=new ReservationSystem(5,regFile,pw);
		ReservationSystem storeResSystem=new ReservationSystem(5,regFile,pw);
		//take input
		/*
		 * op=a;
			des=b;
			j=c;
			k=d;
			issue=x;
		 */
		insQ.addInstruction(insQ.createInstruction("add", 9,9,9));
	  insQ.addInstruction(insQ.createInstruction("lw", 6, 32, 2));
	insQ.addInstruction(insQ.createInstruction("lw", 2,44,3));
		insQ.addInstruction(insQ.createInstruction("mul", 0,2,4));
		insQ.addInstruction(insQ.createInstruction("add", 8,6,2));
		insQ.addInstruction(insQ.createInstruction("mul", 10,0,6));
		insQ.addInstruction(insQ.createInstruction("add", 6,8,2));
	
		record writingNxt=null;
		int whoWriteNxt=-1;
		String nxtTag=null;
		
		int curCycle=1;
		while(true) {
			pw.println("Current cycle is : "+(curCycle++));
			//removing from the reservation system
			switch (whoWriteNxt) {
				case 0: {
					addResSystem.records[writingNxt.idx]=new record(writingNxt.idx);
					regFile.writingTag(nxtTag);
					break;
				}
				case 1: {
					mulResSystem.records[writingNxt.idx]=new record(writingNxt.idx);
					regFile.writingTag(nxtTag);
					break;
				}
				case 2: {
					loadResSystem.records[writingNxt.idx]=new record(writingNxt.idx);
					regFile.writingTag(nxtTag);
					break;
				}
				case 3: {
					storeResSystem.records[writingNxt.idx]=new record(writingNxt.idx);
					regFile.writingTag(nxtTag);
					break;
				}
			
			}
			
			//execute
			record toWrite=executeReservationSystems(new ReservationSystem[] {addResSystem,mulResSystem,loadResSystem,storeResSystem});
			
			
			
			
			String tag=null;
			if(toWrite!=null) {
				tag=toWrite.op+""+toWrite.idx;
			}
			
			//writing
			if(nxtTag==null) {
				pw.println("No Instruction is writing in this Cycle");
			}
			else
				pw.printf("Tag %s is writing\n",nxtTag);
			if(nxtTag!=null) {
			dependenciesReservationSystems(new ReservationSystem[] {addResSystem,mulResSystem,loadResSystem,storeResSystem},nxtTag);
			}
			
			
			
			
			if(toWrite!=null) {
				writingNxt=toWrite;
				whoWriteNxt=whoWrote;
				nxtTag=tag;
			}
			else {
				writingNxt=null;
				whoWriteNxt=-1;
				nxtTag=null;
			}
			
			//issue
			InstructionQueue.Instructions nxt=insQ.nxtInstruction();
			if(nxt!=null) {
				String op=nxt.op;
				switch (op) {
					case "sub":{
						if(addResSystem.existSpace()) {
							addResSystem.insert(nxt, addCycles);
							insQ.fetched();
							pw.printf("Instruction %s %d %d %d is issued\n",nxt.op,nxt.des,nxt.j,nxt.k);
						}
						break;
					}
					case "add": {
						if(addResSystem.existSpace()) {
							addResSystem.insert(nxt, addCycles);
							insQ.fetched();
							pw.printf("Instruction %s %d %d %d is issued\n",nxt.op,nxt.des,nxt.j,nxt.k);
						}
						break;
					}
					case "mul": {
						if(mulResSystem.existSpace()) {
							mulResSystem.insert(nxt, mulCycles);
							insQ.fetched();
							pw.printf("Instruction %s %d %d %d is issued\n",nxt.op,nxt.des,nxt.j,nxt.k);
						}
						break;
					}
					case "div": {
						if(mulResSystem.existSpace()) {
							mulResSystem.insert(nxt, mulCycles);
							insQ.fetched();
							pw.printf("Instruction %s %d %d %d is issued\n",nxt.op,nxt.des,nxt.j,nxt.k);
						}
						break;
					}
					case "lw": {
						if(loadResSystem.existSpace()) {
							loadResSystem.insert(nxt, loadCycles);
							insQ.fetched();
							pw.printf("Instruction %s %d %d %d is issued\n",nxt.op,nxt.des,nxt.j,nxt.k);
						}
						break;
					}
					case "sw": {
						if(storeResSystem.existSpace()) {
							storeResSystem.insert(nxt, storeCycles);
							insQ.fetched();
							pw.printf("Instruction %s %d %d %d is issued\n",nxt.op,nxt.des,nxt.j,nxt.k);
						}
						break;
					}
					
				
				}
			}
			

			pw.println("add Reservation System : ");
			addResSystem.print();
			
			pw.println("___________________");
			
			pw.println("mul Reservation System : ");
			mulResSystem.print();
			
			pw.println("___________________");
			
			pw.println("load Reservation System : ");
			loadResSystem.print();
			
			pw.println("___________________");
			
			pw.println("store Reservation System : ");
			storeResSystem.print();
			
			pw.println("___________________");
			
			regFile.print();
			
			pw.printf("Cycle %d Finished\n",curCycle-1);
			pw.println("__________________________________________________-");
			

			
			boolean busy=addResSystem.existBusy() || mulResSystem.existBusy() || loadResSystem.existBusy() || storeResSystem.existBusy();
			if(!busy && whoWriteNxt==-1) {
				pw.println("finished !!!!!!");
				break;
			}
		}
		
	}

}
