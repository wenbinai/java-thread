package thread.synqueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 从零开始实现自己的阻塞队列
 */
public class BlockingQueue {
    private Object[] items;
    private int count;
    private int putIndex;
    private int takeIndex;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public BlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        items = new Object[capacity];
        count = 0;
        putIndex = 0;
        takeIndex = 0;
    }

    public void put(Object item) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == items.length) {
                System.out.println("队列已满, 还不能生产");
                condition.await();
            }
            enqueue(item);
            // 唤醒消费者线程
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                System.out.println("队列为空, 还不能消费");
                condition.await();
            }

            Object item = dequeue();
            // 唤醒所有的消费者线程
            condition.signalAll();
            return item;
        } finally {
            lock.unlock();
        }


    }

    private void enqueue(Object item) {
        items[putIndex] = item;

        if (++putIndex == items.length) {
            putIndex = 0;
        }
        count++;
    }

    private Object dequeue() {
        Object item = items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length) {
            takeIndex = 0;
        }
        count--;
        return item;
    }
}
