public class Test2
{
	public static int count(String text) {
        String Reg="^[\u4e00-\u9fa5]{1}$";//正则
        int result=0;
        for(int i=0;i<text.length();i++){
         String b=Character.toString(text.charAt(i));
         if(b.matches(Reg))result++;
        }
        return result;
    }
	
	public static String getSeason(int m){//Don't write main() function
		  if(m==12||m==1||m==2){
			  return m+"月属于Winter";
		  }
		  if(m==3||m==4||m==5){
			  return m+"月属于Spring";
		  }
		  if(m==6||m==7||m==8){
			  return m+"月属于Summer";
		  }
		  if(m==9||m==10||m==11){
			  return m+"月属于Autumn";
		  }
		  return "";
	}
	
	public static String convertToLowerCase(String s){//Don't write main() function
		if(s==null){
			return null;
		}
		return s.toLowerCase();
	}
	
	public static void main(String[] args) {
		String str = "呵呵123涅米";
		System.out.println(convertToLowerCase(null));
	}
}