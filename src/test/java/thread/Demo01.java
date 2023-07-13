package thread;

public class Demo01 {

    public static void main(String[] args) {
        //创建线程
        MyThread t01 = new MyThread();
        MyThread t02 = new MyThread();
        MyThread t03 = new MyThread("线程03");

        //开启线程
//        t01.run();
//        t02.run();
//        t03.run();
        // 不会启动线程，不会分配新的分支栈。（这种方式就是单线程。）
        // start()方法的作用是：启动一个分支线程，在JVM中开辟一个新的栈空间，这段代码任务完成之后，瞬间就结束了。
        // 这段代码的任务只是为了开启一个新的栈空间，只要新的栈空间开出来，start()方法就结束了。线程就启动成功了。
        // 启动成功的线程会自动调用run方法，并且run方法在分支栈的栈底部（压栈）。
        // run方法在分支栈的栈底部，main方法在主栈的栈底部。run和main是平级的。
        t01.start();
        t02.start();
        t03.start();
        //设置线程名（补救的设置线程名的方式）
        t01.setName("线程01");
        t02.setName("线程02");
        //设置主线程名称
        Thread.currentThread().setName("主线程");
        for (int i = 0; i < 50; i++) {
            //Thread.currentThread() 获取当前正在执行线程的对象
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}
