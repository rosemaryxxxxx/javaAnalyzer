package constructor;

public class Animal {
    private String type;
    private int age;
    public Animal(){};
    public Animal(String type){
        this.type = type;
    }
    public Animal(int age){
        this.age = age;
    }
    public Animal(String type, int age){
        this.type = type;
        this.age = age;
    }
    public void move(){
        System.out.println("动物可以移动");
    }
}
