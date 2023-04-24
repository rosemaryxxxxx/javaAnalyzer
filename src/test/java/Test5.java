public class Test5 {
    public static String replaceSlashWithPoint(String s){
        if(s == null) return null;
        int len = s.length();
        s=s.substring(0,len-5);
        String str = s.replaceAll("\\\\",".");
        return str;
    }

    public static void main(String[] args){
        String s = "javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMP.java";
        System.out.println(replaceSlashWithPoint(s));
    }
}
