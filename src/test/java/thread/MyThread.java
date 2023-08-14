package thread;

public class MyThread extends Thread{

    public MyThread(String name) {
        super(name);
    }

    //run方法是每个线程运行过程中都必须执行的方法
    @Override
    public void run() {
        System.out.println(this.getName());
    }

    public static void main(String[] args) {
        //创建线程
        MyThread t01 = new MyThread("Threat01");
        t01.start();
    }
}
