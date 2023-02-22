import java.io.File;

public class Test {

    public static void main(String[] args) {
        getFileName();
    }

    public static void getFileName() {
        String path = "D:\\code\\javazip\\t0222"; // 路径
        File f = new File(path);//获取路径  F:测试目录
        if (!f.exists()) {
            System.out.println(path + " not exists");//不存在就输出
            return;
        }

        File fileArray[] = f.listFiles();//用数组接收
        for (File file : fileArray ){
            if(file.getName().endsWith(".zip")){
                System.out.println(file.getName());
            }else{
                System.out.println(file.getName()+"[非zip文件]");
            }
        }
    }
}

