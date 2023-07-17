package overload;

import java.util.ArrayList;
import java.util.List;

public class Overloading {
    public int test(){
        System.out.println("test1");
        return 1;
    }

    public int test(int a){
        System.out.println("test2int");
        return a;
    }

    public int test(Integer b){
        System.out.println("test2Integer");
        return 0;
    }
//    public int test(int a){
//        System.out.println("test2int");
//        return a;
//    }

    //以下两个参数类型顺序不同
    public String test(int a,String s){
        System.out.println("test3");
        return "returntest3";
    }

    public String test(String s,int a){
        System.out.println("test4");
        return "returntest4";
    }

    public static void t(List<String> list, int a){
        System.out.println(list);
    }

    public static void main(String[] args){
        List<String> stringList = new ArrayList<>();
        int a = 0;
        t(stringList,a);
        Overloading o = new Overloading();
        int i = 2;
        int c = o.test(2);
//        System.out.println(o.test(i,"test3"));
//        System.out.println(o.test("test4",1));
    }

}
