public class Test {

     static String s = new String("1");
     static String c = s;
    public int a;


    public static void main(String[] args) {
        new Thread(new Runnable1()).start();
        new Thread(new Runnable2()).start();
        new Thread(new Runnable3()).start();

    }


   public static class Runnable1 implements Runnable {
        @Override
        public void run() {
            System.out.println("Runnable1 run");
            synchronized (c) {
                System.out.println("Runnable1 锁了c:");
                System.out.println("c before sleep hash:" + System.identityHashCode(c));
                try {
                    Thread.sleep(5000);
                    System.out.println("c after sleep hash:" + System.identityHashCode(c));
                   c.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Runnable2 implements Runnable {
        @Override
        public void run() {

            try {
                Thread.sleep(1000);
                System.out.println("Runnable2 run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Runnable2：读取c的值为" + c);
        }
    }

    public static class Runnable3 implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(500);

                System.out.println("Runnable3 run");

                synchronized (c)
                {
                    System.out.println("Runnable3：读取c的值为" + c);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
