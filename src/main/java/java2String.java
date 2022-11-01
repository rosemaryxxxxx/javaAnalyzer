import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class java2String {
    static String content;
    public static void main(String[] args){
        {
            try {
                content = new String(Files.readAllBytes(Paths.get("D:\\code\\javazip\\t\\1.java")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
