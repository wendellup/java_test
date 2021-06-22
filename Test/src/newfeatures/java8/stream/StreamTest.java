package newfeatures.java8.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
	public static void main(String[] args) {
		List<String> strings = Arrays.asList("abc", "def", "gkh", "abc");
		
		List<Integer> integerList = Arrays.asList(1, 2, 3, 4);
		
		strings.stream().filter(s-> s.contains("ab")).collect(Collectors.toList()).forEach(System.out::println);;
		
		System.out.println(integerList.stream().filter(s-> s>2).collect(Collectors.counting()));;
//		.forEach(System.out::println);;
		
		strings.stream().limit(1).forEach(System.out::println);;

		strings.stream().map(s -> s + "22").forEach(System.out::println);
		String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(","));
		
		
		 //对数组的统计，比如用
	    List<Integer> number = Arrays.asList(1, 2, 5, 4);

	    IntSummaryStatistics statistics = number.stream().mapToInt(x -> x).summaryStatistics();
	    
	    System.out.println("列表中最大的数 : "+statistics.getMax());
	    System.out.println("列表中最小的数 : "+statistics.getMin());
	    System.out.println("平均数 : "+statistics.getAverage());
	    System.out.println("所有数之和 : "+statistics.getSum());
	    
	    //concat 合并流
	    List<String> strings2 = Arrays.asList("xyz", "jqx");
	    Stream.concat(strings2.stream(),strings.stream()).count();
	    
	  //注意 一个Stream只能操作一次，不能断开，否则会报错。
	    Stream stream = strings.stream();
	    //第一次使用
	    stream.limit(2);
	    //第二次使用
//	    stream.forEach(System.out::println);
	    
	    String[] array = {"a", "b", "c", "d", "e"};
        Stream<String> stream2 = Arrays.stream(array);
        // loop a stream
        stream2.forEach(x -> System.out.println(x));
        stream2.close();
        stream2 = Arrays.stream(array);
        // reuse it to filter again! throws IllegalStateException
        long count = stream2.filter(x -> "b".equals(x)).count();
        System.out.println(count);
	    
        
        strings = Arrays.asList("abc", "def", "gkh", "abc");
        stream = strings.stream().filter(new Predicate() {
            @Override
            public boolean test(Object o) {
              System.out.println("Predicate.test 执行");
              return true;
              }
            });

         System.out.println("count 执行");
         stream.count();
	}
}
