package reflection.reflect;

import reflection.entity.Method;

public class Test {
    public static void main(String[] args){
        java.lang.reflect.Method[] methods = Method.class.getMethods();
        for (java.lang.reflect.Method method : methods) {
            System.out.println(method);
        }
    }
}
