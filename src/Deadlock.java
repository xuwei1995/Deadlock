public class Deadlock {
    String a = "a";
    String b = "b";

    public static void main(String[] args) throws InterruptedException {
        Deadlock deadlock = new Deadlock();
        deadlock.new ThreadA().start();
        deadlock.new ThreadB().start();
        Thread.sleep(1000);
        deadlock.new ThreadC().start();
    }

    class ThreadA extends Thread {
        @Override
        public void run() {
            super.run();
            System.out.println("threadA----run");
            synchronized (a) {
                System.out.println("threadA----我获取到了a的monitor");
                try {
                    //让B线程有足够时间拿到b的锁
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadA----我想再去拿到b的monitor 不知道他是不是被别的线程占用");
                synchronized (b) {
                    System.out.println("threadA----获取到了b的monitor");
                }
            }
        }
    }

    class ThreadB extends Thread {
        @Override
        public void run() {
            super.run();
            System.out.println("threadB----run");
            synchronized (b) {
                System.out.println("threadB----我获取到了b的monitor");
                System.out.println("threadB----我想再去拿到a的monitor 不知道他是不是被别的线程占用");
                synchronized (a) {
                    System.out.println("threadB----获取到了a的monitor");
                }
            }
        }
    }

    class ThreadC extends Thread {
        @Override
        public void run() {
            super.run();
           // String c =a+"123";
            a="123";
            System.out.println("我能不能拿到a,能拿到我就打印 我拿到了a");
            synchronized (a) {
                System.out.println("我拿到了a");
            }
        }
    }
}
