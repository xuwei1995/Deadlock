public class H {
    static final Test test = new Test();
    public static void main(String[] args) {
       new Thread(new Runnable1()).start();
        new Thread(new Runnable2()).start();
    }
    static class Runnable1 implements Runnable {
        @Override
        public void run() {
            synchronized (Test.class) {
                try {
                    Thread.sleep(3000);
                    System.out.println("1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static class Runnable2 implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Test.set 是个同步静态方法
            test.a=5;
            System.out.println("2");
        }
    }
}
