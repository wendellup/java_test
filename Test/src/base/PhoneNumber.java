package base;

import java.util.HashMap;
import java.util.Map;

public class PhoneNumber {
	private final short areaCode;
	private final short prefix;
	private final short lineNumber;
	
	public PhoneNumber(int areaCode, int prefix, int lineNumber){
		this.areaCode = (short)this.areaCode;
		this.prefix = (short)prefix;
		this.lineNumber = (short)lineNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + areaCode;
		result = prime * result + lineNumber;
		result = prime * result + prefix;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhoneNumber other = (PhoneNumber) obj;
		if (areaCode != other.areaCode)
			return false;
		if (lineNumber != other.lineNumber)
			return false;
		if (prefix != other.prefix)
			return false;
		return true;
	}
	
	public static void main(String[] args) {
		Map<PhoneNumber, String> m = new HashMap<PhoneNumber, String>();
		m.put(new PhoneNumber(707, 867, 5309), "Jenny");
		
		System.out.println(m.get(new PhoneNumber(707, 867, 5309)));
	}
}
