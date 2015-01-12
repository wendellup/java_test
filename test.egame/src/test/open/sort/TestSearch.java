package test.open.sort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestSearch {
	private static List<Integer> searchList = new ArrayList<Integer>();
	List<Integer> filterList = new ArrayList<Integer>();

	public static void main(String[] args) throws IOException {
		try {
			
//			BufferedReader br = new BufferedReader(new InputStreamReader(
//					new FileInputStream(new File("id2.txt"))));
			
			URL url = TestSearch.class.getClassLoader().getResource("id2.txt");
			System.out.println("url:"+url);
			System.out.println("uri:"+url.toURI());
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(url.toURI()))));
			String str = null;
//			while((str = br.readLine())!=null){
//				System.out.println(str);
//			}
			str = br.readLine();
			String[] strs = str.split(",");
			for(String intStr : strs){
				if(intStr!=null && ""!=intStr){
					intStr = intStr.trim();
					searchList.add(Integer.valueOf(intStr));
				}
			}
			System.out.println(searchList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(searchList);
		System.out.println(searchList);
	}
}
