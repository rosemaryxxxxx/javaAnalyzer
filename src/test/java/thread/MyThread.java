package thread;

public class MyThread extends Thread{
    public MyThread() {
    }

    public MyThread(String name) {
        super(name);
    }

    //run方法是每个线程运行过程中都必须执行的方法
    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println(this.getName() + ":" + i);
        }
    }
}
