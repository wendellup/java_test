
public class TestInfo {
	public static void main(String[] args) {
		Aoo aoo = null;
		getAoo(aoo);
		System.out.println(aoo);
	}

	private static void getAoo(Aoo aoo) {
		aoo = new Aoo(1, "str");
	}
}

class Aoo{
	private int num;
	private String no;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Aoo(int num, String no) {
		super();
		this.num = num;
		this.no = no;
	}
	
}
