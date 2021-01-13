import java.io.PrintWriter;

public class RegisterFile {
	Register[] regs;
	PrintWriter pw;
	public RegisterFile(int size,PrintWriter p) {
		regs = new Register[size];
		for (int i = 0; i < size; i++)
			regs[i] = new Register(i, null);
		pw=p;
	}
	void RegNoWait(int num) {
		regs[num].Qi=null;
	}
	void RegWait(int num,String qi) {
		regs[num].Qi=qi;
	}
	void writingTag(String s) {
		for(int i=0;i<regs.length;i++) {
			if(regs[i].Qi!=null && regs[i].Qi.equals(s)) {
				RegNoWait(i);
			}
		}
	}
	void print() {
		pw.println("Register file :");
		for(int i=0;i<regs.length;i++) {
			pw.printf("Register F%d , Qi=%s , Content=%d\n",regs[i].num,regs[i].Qi,regs[i].content);
		}
		pw.println("_____________");
	}
	public static class Register {
		int num;
		String Qi;
		Double content;

		public Register(int i, String Q) {
			num = i;
			Qi = Q;
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
