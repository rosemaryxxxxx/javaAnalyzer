package shixian.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFiles {
    public static void main(String[] args) throws IOException {

        String zipPath = "D:\\code\\javazip\\t1.zip";
        List<String> paths = new ArrayList<>();
        extractFileStructureOfZip(zipPath,paths);


    }

    public static void extractFileStructureOfZip(String zipPath , List<String> javaPaths){
        // 要进行解压缩的zip文件
        File zipFile = new File(zipPath);

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
                if (zipEntry.toString().endsWith(".java")) {
                    System.out.println("这是一个java文件：" + zipEntry);
                    // 获取zip压缩包中的子文件名称
                    String zipEntryFileName = zipEntry.getName();

                    //将zipEntryFileName中/替换成\
                    String zipEntryFileName1 = zipEntryFileName.replaceAll("/", "\\\\");

                    // 创建该文件的输出流,解压后的文件保存位置，例如”D:\\code\\javazip\\t\\1.java“
                    zipFilePath = targetDir.getPath() + "\\" + zipEntryFileName1.substring(zipEntryFileName1.indexOf("\\") + 1);

                    //把.java文件路径装入集合中
                    javaPaths.add(zipFilePath);
                    // 创建解压缩子目录
                    File f = new File(zipFilePath);
                    File innerTargetDir = new File(f.getParent());
                    if (!innerTargetDir.exists()) {
                        //mkdirs()根据绝对路径新建目录，如果上一级目录不存在，则会将上一级目录创建完后，再创建后面一级的目录
                        innerTargetDir.mkdirs();
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

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
