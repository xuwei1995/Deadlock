public class A {


    String a = "a";
    String b = "b";

    public static void main(String[] args) throws InterruptedException {
// TODO Auto-generated method stub
        A m = new A();
        m.new threadA().start();
        m.new threadB().start();
        Thread.sleep(5000);
        m.new threadC().start();
    }
    class  threadC extends Thread{
        @Override
        public void run() {
            super.run();
                System.out.println("我拿到了a"+a);
                synchronized (b)
                {
                    System.out.println("我拿到了b");
                }

        }
    }
    class threadA extends Thread {
        @Override
        public void run() {
// TODO Auto-generated method stub
            super.run();
            synchronized (a) {
                try {
                    System.out.println("threadA----a.wait");
                    a.wait();
                    synchronized (b) {
                        b.notifyAll();
                        System.out.println("threadA----b.notifyAll()后");
                        System.out.println(a + b);
                    }


                } catch (InterruptedException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    class threadB extends Thread {
        @Override
        public void run() {
// TODO Auto-generated method stub
            super.run();
            synchronized (b) {
                synchronized (a) {
                    a.notifyAll();
                    System.out.println("threadB----a.notifyAll 后");
                    System.out.println(a + b);
                }
            }
        }
    }
}