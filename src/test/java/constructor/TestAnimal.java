package constructor;

import org.checkerframework.checker.units.qual.A;

public class TestAnimal {
    public static void main(String[] args){
        Animal animal = new Animal();
        Animal dog0 = new Animal("dog");
        Animal dog1 = new Animal(3);
        Animal cat = new Animal("cat",100);
    }
}
