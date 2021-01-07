class record{
	public record(int i) {
		idx=i;
	}
	public record(String op1,int remCycles1,int i) {
		busy=true;
		op=op1;
		remCycles=remCycles1;
		idx=i;
	}
	boolean busy;
	String op;
	Integer Vj,Vk,Address;
	String Qj,Qk;
	int remCycles;//cycles remaining
	Integer res;
	int idx;//tag
	void run() {
		if(Vj!=null && Vk!=null) {
			remCycles=Math.max(0, remCycles-1);
		}
	}
}