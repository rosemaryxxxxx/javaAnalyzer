package pmd.deadcodetest.utils;

import java.util.List;

import static pmd.deadcodetest.utils.KMPWithMain.kmpwithmain;

public class callsss {
    public static void main(String[] args) {
        String parent = "abcdefjhhhhh";
        String sub = "bcd";
        List<String> l;

        if (kmpwithmain(parent, sub)) {
            System.out.println("true");
        }else {
            System.out.println("false");
        }

    }
}
