import java.io.IOException;

import org.apkinfo.api.GetApkInfo;
import org.apkinfo.api.domain.ApkInfo;


public class 根据apk包获取package_name {
//	public static void main(String[] args) {
//		ApkDecoder d = new ApkDecoder();
//		d.setApkFile(new File("D:/test/qd.apk"));
//		try {
//			Set<ResPackage> p = d.getResTable().listMainPackages();
//			for(ResPackage r:p){
//				//这里set最大只会等于1
//				//打印出包名
//				System.out.println(r.getName());
//			}
//		} catch (AndrolibException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void main(String[] args) throws IOException {
//		 ApkInfo manifestInfo = GetApkInfo.getApkInfoByFilePath("C:\\Users\\yuchao\\Downloads\\FinaleOfThrone_simple_playcn.apk");
//         System.out.println(""+manifestInfo.getPackageName());
//         System.out.println(""+manifestInfo.getVersionCode());
//         
//         ApkInfo manifestInfo2 = GetApkInfo.getApkInfoByFilePath("C:\\Users\\yuchao\\Downloads\\243794_1.apk");
//         System.out.println(""+manifestInfo2.getPackageName());
//         System.out.println(""+manifestInfo2.getVersionCode());
         
         ApkInfo manifestInfo3 = GetApkInfo.getApkInfoByFilePath("C:\\Users\\yuchao\\Downloads\\5008336_1.apk");
         System.out.println("package_name:"+manifestInfo3.getPackageName());
         System.out.println("version_code:"+manifestInfo3.getVersionCode());
         System.out.println("version_name:"+manifestInfo3.getVersionName());
	}
	
}