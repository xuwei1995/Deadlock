public class Main {
    String a = "a";
    String b = "b";

    public static void main(String[] args) {
// TODO Auto-generated method stub
        Main m = new Main();
        m.new ThreadA().start();
        m.new ThreadB().start();
    }

    class ThreadA extends Thread {
        @Override
        public void run() {
// TODO Auto-generated method stub
            super.run();
            System.out.println("threadA----run");

            synchronized (a) {
                System.out.println("threadA----获取到了a的monitor");
                try {
                    sleep(1000);
                    System.out.println("threadA----我现在是持有a的monitor的 我要执行a.wait释放了a的monitor 我进入a的Wait队列 你们记得唤醒我");
                   //a.wait B线程卡在a对象入口对列 可以有权利进入了（如果多个线程在入口队列 有cpu调度决定谁进入）
                    a.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadA----我被唤醒");
                System.out.println("threadA----我想去获取b的monitor 我可能会在被卡在入口队列 5s后等待b线程去释放锁我就可以执行下去了");
                synchronized (b) {
                    System.out.println("threadA----我获得到了b的monitor");
                    b.notify();
                    System.out.println("threadA----我执行b.notify去通知b对象的wait队列中的一个线程去竞争b 这个线程从wait队列到入口队列去竞争 如果拿到了对象的锁线程进入可运行状态（Runable） ");

                }
            }

        }
    }

    class ThreadB extends Thread {
        @Override
        public void run() {
// TODO Auto-generated method stub
            super.run();
            System.out.println("threadB----run");
            synchronized (b) {
                System.out.println("threadB----获取到了b的monitor");
                System.out.println("threadB----我正在尝试获取a的monitor...有可能他正被别人给占用了 那么我就进入了a对象的入口队列等待其他线程释放我去争取a的monitor");
                synchronized (a) {
                    //ThreadA 中执行a.wait() 此时我竞争到了a的monitor 次demo中只有我在竞争 如果存在多个线程竞争a 也只有一个线程能获取 a 没有获取到的线程还在竞争
                    System.out.println("threadB----终于被我获取到了a的monitor");
                    try {
                        //占用a的monitor 3S
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("threadB----我执行a.notify让a.wait(在a wait队列中的线程去竞争)的线程被唤醒");
                    a.notify();
                }
                try {
                    System.out.println("threadB----过5秒后我执行b.wait释放了b的monitor 5s后我进入Wait队列  卡在b对象入口队列的线程5s后就可以去获取b了");
                    sleep(5000);
                    b.wait();
                    System.out.println("threadB----我被人通知从b对象的wait队列去b对象的入口队列去争取b的持有锁/monitor");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}