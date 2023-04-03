package test;

import pmd.deadcodetest.utils.KMP;

public class Test {
    public static void main(String[] args){
        String p = "asdasfafa";
        String s = "fa";
        //跨包调用方法
        KMP.kmp(p,s);
    }
}
