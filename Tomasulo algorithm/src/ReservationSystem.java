
public class ReservationSystem {
	static class record{
		boolean busy;
		String op;
		Integer Vj,Vk,Qj,Qk,Address;
		int remCycles;//cycles remaining
		
		void run() {
			if(Vj!=null && Vk!=null) {
				remCycles--;
			}
		}
	}
	record[]records;
	public ReservationSystem(int maxSize) {
		records=new record[maxSize];
		for(int i=0;i<maxSize;i++) {
			records[i]=new record();
		}
	}
}
