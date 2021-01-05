
public class RegisterFile {
	Register[] regs;

	public RegisterFile(int size) {
		regs = new Register[size];
		for (int i = 0; i < size; i++)
			regs[i] = new Register(i, "");
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
