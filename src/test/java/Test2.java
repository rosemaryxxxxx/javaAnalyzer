import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Test2 {
    public static void main(String[] args)
    {
        String filePath = "D:\\code\\javazip\\t0222\\" + "1.txt";
        FileWriter fw = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                file.createNewFile();
            }
            fw = new FileWriter(filePath);
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write("hello\n");
            bw.write("world\n");
            bw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fw.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

    }
}
