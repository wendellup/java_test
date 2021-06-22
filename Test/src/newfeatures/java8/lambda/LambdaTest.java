package newfeatures.java8.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LambdaTest {

	public static void main(String[] args) {
//		1.Runnable 接口
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("The runable now is using!");
			}
		}).start();

		// 用lambda
		new Thread(() -> System.out.println("It's a lambda function!")).start();

//		2.Comperator 接口
		List<Integer> strings = Arrays.asList(1, 2, 3);

		Collections.sort(strings, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		});

		// Lambda
		Collections.sort(strings, (Integer o1, Integer o2) -> o1 - o2);
		System.out.println(strings);

		// 分解开
		Comparator<Integer> comperator = (Integer o1, Integer o2) -> o1 - o2;
		Collections.sort(strings, comperator);

//		集合迭代
		List<String> stringList = Arrays.asList("1", "2", "3");
		// 传统foreach
		for (String s : stringList) {
			System.out.println(s);
		}
		// Lambda foreach
		stringList.forEach((s) -> System.out.println(s));
		// or
		stringList.forEach(System.out::println);
		// map
		Map<Integer, String> map = new HashMap<>();
		map.forEach((k, v) -> System.out.println(k+v));

	}

}
