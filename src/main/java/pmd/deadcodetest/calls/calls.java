package pmd.deadcodetest.calls;


import  pmd.deadcodetest.utils.KMP;


public class calls {
    public static void main(String[] args) {
        String parent = "abcdefjhhhhh";
        String sub = "bcd";

        if (KMP.kmp(parent, sub)) {
            System.out.println("true");
        }else {
            System.out.println("false");
        }

    }
}
