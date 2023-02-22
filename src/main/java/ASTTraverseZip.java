import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ASTTraverseZip {

    public static void main(String[] args) throws IOException {

        String filepath = "D:\\code\\javazip\\t0222";

        ASTTraverseZip astTraverseZip = new ASTTraverseZip();
        File fileArray[] = astTraverseZip.getFileName(filepath);
        //处理文件中每一个.zip文件
        for (File file : fileArray ){
            if(file.getName().endsWith(".zip")){//只处理.zip文件
                astTraverseZip.astTraverseZip(file.getPath());
            }else{
                System.out.println(file.getName()+"[非zip文件]");
            }
        }
    }

    /**
     * 获取文件夹中所有zip文件的文件名
     * @param filepath
     */
    public File[] getFileName(String filepath){
        File f = new File(filepath);//获取路径  F:测试目录
        if (!f.exists()) {
            System.out.println(filepath + " not exists");//不存在就输出
            return null;
        }

        return f.listFiles();//用数组接收
//        for (int i = 0; i < fa.length; i++) {//循环遍历
//            File fs = fa[i];//获取数组中的第i个
//            if (fs.isDirectory()) {
//                System.out.println(fs.getName() + " [目录]");//如果是目录就输出
//            } else {
//                System.out.println(fs.getName());//否则直接输出
//            }
//        }
    }

    /**
     * 遍历一个zip文件中的所有.java文件，获取每个method中需要的部分
     * @param pathzip
     */
    public void astTraverseZip(String pathzip){
        File zipFile = new File(pathzip);
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

        // 2.解析读取zip文件
        try (ZipInputStream in = new ZipInputStream(new FileInputStream(zipFile), Charset.forName("gbk"))) {
            // 遍历zip文件中的每个子文件
            ZipEntry zipEntry = null;

            while ((zipEntry = in.getNextEntry()) != null) {
                String zipFilePath = null;
                if (zipEntry.toString().endsWith("java")) {
//                    System.out.println("这是一个java文件：" + zipEntry);
                    // 获取zip压缩包中的子文件名称
                    String zipEntryFileName = zipEntry.getName();

                    //将zipEntryFileName中/替换成\
                    String zipEntryFileName1 = zipEntryFileName.replaceAll("/", "\\\\");

                    // 创建该文件的输出流,解压后的文件保存位置，例如”D:\\code\\javazip\\t\\1.java“
                    zipFilePath = targetDir.getPath() + "\\" + zipEntryFileName1.substring(zipEntryFileName1.indexOf("\\") + 1);

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
//                    System.out.println(content);

                    //调用Asttraverse()方法对每一个.java文件进行遍历
                    ASTTraverse astTraverse = new ASTTraverse();
                    astTraverse.astTraverse(zipFilePath);

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
