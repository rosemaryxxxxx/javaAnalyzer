import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


public class licenseCheck {

    public static void main(String[] args) throws IOException {

        String path = "D:\\code\\javazip\\160402.zip";
        ZipFile zf = new ZipFile(path);

        // 要进行解压缩的zip文件
        File zipFile = new File("D:\\code\\javazip\\160402.zip");

        // 1.创建解压缩目录
        // 获取zip文件的名称
        String zipFileName = zipFile.getName();

        // 根据zip文件名称，提取压缩文件目录
        String targetDirName = zipFileName.substring(0, zipFileName.indexOf("."));

        // 创建解压缩目录
        File targetDir = new File(zipFile.getParent() + "\\" + targetDirName);

        if (!targetDir.exists()) {
            targetDir.mkdir(); // 创建目录
        }

        //实例化KMP类，以调用kmp算法进行字符串匹配
        KMP kmp = new KMP();


        // 2.解析读取zip文件
        try (ZipInputStream in = new ZipInputStream(new FileInputStream(zipFile), Charset.forName("gbk"))) {
            // 遍历zip文件中的每个子文件
            ZipEntry zipEntry = null;
            //是否存在LICENSE，默认为false
            boolean exist_LICENSE = false;
            while ((zipEntry = in.getNextEntry()) != null) {
                System.out.println(zipEntry.toString());
                if(zipEntry.toString().endsWith("LICENSE")){
                    exist_LICENSE = true;
                    BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(zipEntry)));
                    String firstLine;
                    //打印出第一行
                    firstLine = br.readLine();
                    br.close();
                    if ((kmp.kmp(firstLine,"MIT"))||
                            (kmp.kmp(firstLine,"Apache"))||
                            (kmp.kmp(firstLine,"BSD"))
                    ){
                        //操作
                        System.out.println("存在LICENSE且慷慨，LICENSE为："+firstLine);
                    }
                }
            }
            if(exist_LICENSE==false){
                System.out.println("不存在LICENSE");
            }
        }

    }

}
