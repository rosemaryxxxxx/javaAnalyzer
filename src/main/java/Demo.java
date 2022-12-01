import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


public class Demo {
    
    public static void main(String[] args) throws IOException {

        String path = "D:\\code\\javazip\\t.zip";
        ZipFile zf = new ZipFile(path);

        // 要进行解压缩的zip文件
        File zipFile = new File("D:\\code\\javazip\\t.zip");

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

            while ((zipEntry = in.getNextEntry()) != null) {
                if (zipEntry.toString().endsWith("java")) {
                    System.out.println("这是一个java文件："+zipEntry);
                    // 获取zip压缩包中的子文件名称
                    String zipEntryFileName = zipEntry.getName();

                    //将zipEntryFileName中/替换成\
                    String zipEntryFileName1=zipEntryFileName.replaceAll("/", "\\\\");

                    // 创建该文件的输出流,解压后的文件保存位置，例如”D:\\code\\javazip\\t\\1.java“
                    String zipFilePath = targetDir.getPath() + "\\" + zipEntryFileName1.substring(zipEntryFileName1.indexOf("\\")+1);

                    // 创建解压缩子目录
                    File f = new File(zipFilePath);
                    File innerTargetDir = new File(f.getParent());
                    if (!innerTargetDir.exists()) {
                        innerTargetDir.mkdir();
                    }

                    // 输出流定义在try()块，结束自动清空缓冲区并关闭
                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(zipFilePath))) {

                        // 读取该子文件的字节内容
                        byte[] buff = new byte[1024];
                        int len = -1;
                        while ((len = in.read(buff)) != -1) {
                            bos.write(buff, 0, len);
                        }
                    }
                    String content = new String(Files.readAllBytes(Paths.get(zipFilePath)));
                    System.out.println(content);
                } else if ((zipEntry.toString().endsWith("LICENSE"))){
                    System.out.println("这是LICENSE："+zipEntry);
                    BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(zipEntry)));
                    String line;
                    String firstLine;
//                    //打印出所有行
//                    while((line = br.readLine()) != null){
//                        System.out.println(line.toString());
//                    }
                    //打印出第一行
                    firstLine = br.readLine();
                    System.out.println(firstLine);
                    kmp.kmp(firstLine,"MIT");

                    br.close();
                    //拿到每个文件对象的文件名
                    String zipEntryFileName = zipEntry.getName();
                    System.out.println(zipEntryFileName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}



//
//import java.io.*;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;
//import java.util.zip.ZipInputStream;
//
//public class Demo {
//    public static void main(String[] args) throws IOException {
//        String path = "D:\\code\\javazip\\t.zip";
//        ZipFile zf = new ZipFile(path);
//        InputStream in = new BufferedInputStream(new FileInputStream(path));
//        Charset gbk = Charset.forName("gbk");
//        ZipInputStream zin = new ZipInputStream(in,gbk);
//        ZipEntry ze;
//        while((ze = zin.getNextEntry()) != null){
//            if(ze.toString().endsWith("txt")){
//                BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
//                String line;
//                while((line = br.readLine()) != null){
//                    System.out.println(line.toString());
//                }
//                br.close();
//                //拿到每个文件对象的文件名
//                String zipEntryFileName = ze.getName();
//                System.out.println(zipEntryFileName);
//            }else if(ze.toString().endsWith("java")){
//                //拿到每个文件对象的文件名
//                String zipEntryFileName = ze.getName();
//                System.out.println(zipEntryFileName);
////                String content = new String(Files.readAllBytes(Paths.get(zipEntryFileName)));
////                System.out.println(content);
//            }
//            System.out.println();
//        }
//        zin.closeEntry();
//    }
//}
