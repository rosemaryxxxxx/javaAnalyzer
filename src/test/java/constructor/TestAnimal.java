package constructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestAnimal {
    public static void main(String[] args){
        Animal animal = new Animal();
        Animal dog0 = new Animal("dog");
        Animal dog1 = new Animal(3);
        Animal cat = new Animal("cat",100);
        Dog dogg = new Dog();
        List<String> list = new ArrayList<String>();
        List<Animal> animalList = new LinkedList<Animal>();
    }
}
