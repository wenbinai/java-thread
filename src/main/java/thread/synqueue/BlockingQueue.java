package thread.synqueue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 从零开始实现自己的阻塞队列
 */
public class BlockingQueue {
    private Object[] items;
    private AtomicInteger count;
    private int putIndex;
    private int takeIndex;

    private final ReentrantLock putLock = new ReentrantLock();
    private final Condition notFull = putLock.newCondition();

    private final ReentrantLock takeLock = new ReentrantLock();
    private final Condition notEmpty = takeLock.newCondition();

    public BlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        items = new Object[capacity];
        count = new AtomicInteger(0);
        putIndex = 0;
        takeIndex = 0;
    }

    public void put(Object item) throws InterruptedException {
        int c = -1;
        putLock.lockInterruptibly();
        try {
            while (count.get() == items.length) {
                System.out.println("队列已满, 还不能生产");
                notFull.await();
            }
            enqueue(item);

            c = count.getAndIncrement();
            if (c + 1 < items.length) {
                notFull.signal();
            }
        } finally {
            putLock.unlock();
        }
        // 唤醒消费者线程 (防止死锁)
        if (c == 0)
            signNotEmpty();
    }

    private void signNotEmpty() {
        takeLock.lock();
        try {
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }


    public Object take() throws InterruptedException {
        Object item;
        int c = -1;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                System.out.println("队列为空, 还不能消费");
                notEmpty.await();
            }
            item = dequeue();
            c = count.getAndDecrement();
            if (c - 1 > 0) {
                notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }
        // 唤醒所有的消费者线程 防止死锁
        if (c ==  items.length)
            signNotFull();
        return item;
    }

    private void signNotFull() {
        putLock.lock();
        try {
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }

    private void enqueue(Object item) {
        items[putIndex] = item;

        if (++putIndex == items.length) {
            putIndex = 0;
        }
    }

    private Object dequeue() {
        Object item = items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length) {
            takeIndex = 0;
        }
        return item;
    }
}
