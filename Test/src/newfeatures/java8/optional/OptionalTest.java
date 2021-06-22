package newfeatures.java8.optional;

import java.util.Objects;
import java.util.Optional;

class Zoo {
	private Dog dog;

	public Dog getDog() {
		return dog;
	}

	public void setDog(Dog dog) {
		this.dog = dog;
	}
}

class Dog {
	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}

public class OptionalTest {
	public static void main(String[] args) {

		Zoo zoo = new Zoo();
		Dog dogge = new Dog();
		dogge.setAge(56);
		zoo.setDog(dogge);
		if (zoo != null) {
			Dog dog = zoo.getDog();
			if (dog != null) {
				int age = dog.getAge();
				System.out.println(age);
			}
		}

		Optional.ofNullable(zoo).map(o -> o.getDog()).map(d -> d.getAge()).ifPresent(age -> System.out.println(age));
		
		Zoo zoo2 = null;
//		Optional.ofNullable(zoo2).map(z -> z.getDog()).get().getAge();
		Optional.ofNullable(zoo2).map(z -> z.getDog()).ifPresent(dog -> System.out.println(dog));;
		Optional<Dog> dogOp = Optional.ofNullable(zoo2).map(z -> z.getDog());
		if(!dogOp.isPresent()) {
			System.out.println("dogOp not present");
		}
		
//		Objects.requireNonNull(zoo2);
		
		System.out.println(Optional.ofNullable(zoo).map(o -> o.getDog()).map(d -> d.getAge()).filter(v->v==null).orElse(3));;
		
		

	}
}
