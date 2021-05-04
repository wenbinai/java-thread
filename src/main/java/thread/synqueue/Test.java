package thread.synqueue;

public class Test {
    public static void main(String[] args) {
        BlockingQueue q = new BlockingQueue(5);
        for (int i = 0; i < 3; i++) {
            new Thread(new Producer(q)).start();
            new Thread(new Customer(q)).start();
        }

    }
}

class Producer implements Runnable {
    BlockingQueue queue;

    public Producer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        for (int i = 0; ; i++) {
            try {
                queue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Customer implements Runnable {
    BlockingQueue queue;

    public Customer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        for (int i = 0; ; i++) {
            try {
                Integer take = (Integer) queue.take();
                System.out.println(take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}