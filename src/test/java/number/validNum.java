package number;

import com.github.javaparser.symbolsolver.resolution.typesolvers.AarTypeSolver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validNum {
    public static boolean isNumber(String str) {
        //Pattern pattern = Pattern.compile("^-?[0-9]+"); //这个也行
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?([df])?$");//这个也行
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isInteger(String str) {
        //Pattern pattern = Pattern.compile("^-?[0-9]+"); //这个也行
        Pattern pattern = Pattern.compile("^-?\\d+$");//这个也行
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isFloat(String str) {
        //Pattern pattern = Pattern.compile("^-?[0-9]+"); //这个也行
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?[f]$");//这个也行
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    public static boolean isDouble(String str) {
        //Pattern pattern = Pattern.compile("^-?[0-9]+"); //这个也行
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?([d])?$");//这个也行
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String analyseNumType(String argment){
        if(argment.isEmpty()) return "";
        if(isFloat(argment)) return "float";
        if(isInteger(argment) && !isDouble(argment)) return "int";
        if(isDouble(argment) && !isInteger(argment)) return "double";
        return "int or double";
    }


    private static void isNumberTest() {
        String str = "107687969";
        System.out.println(analyseNumType(str));
//        System.out.println(isInteger(str));
//        System.out.println(isFloat(str));
//        System.out.println(isDouble(str));
    }

    public static void main(String[] args) {
        isNumberTest();
    }
}
