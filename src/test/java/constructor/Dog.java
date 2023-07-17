package constructor;

import override.Animal;

public class Dog extends Animal {
    @Override
    public void move(){
        super.move();
        System.out.println("狗可以跑和走");
    }
    public void move(int steps){
        System.out.println("狗走了"+steps+"步");
    }
    public void wang(){
        System.out.println("狗可以wang");
    }
}
