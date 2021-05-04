package thread.synqueue;

import java.util.ArrayList;
import java.util.List;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        final BlockingQueue q = new BlockingQueue(2);
        final int threads = 2;
        final int times = 10;
        List<Thread> threadList = new ArrayList<Thread>(threads * 2);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threads; i++) {
            final int offset = i * times;
            Thread producer = new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        q.put(new Integer(offset + j));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threadList.add(producer);
            producer.start();
        }

        for (int i = 0; i < threads; i++) {
            Thread consumer = new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        Integer item = (Integer) q.take();
                        System.out.println(item);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threadList.add(consumer);
            consumer.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("总耗时: %.2fs", (endTime - startTime) / 1e3));
    }
}
