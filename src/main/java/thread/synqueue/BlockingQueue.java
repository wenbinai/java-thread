package thread.synqueue;

/**
 * 从零开始实现自己的阻塞队列
 */
public class BlockingQueue {
    private Object[] items;
    private int count;
    private int putIndex;
    private int takeIndex;

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
        while (true) {
            synchronized (this) {
                if (count != items.length) {
                    enqueue(item);
                    break;
                }
            }
            System.out.println("队列已满, 还不能生产");
            Thread.sleep(200L);
        }


    }

    public Object take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (count != 0) {
                    return dequeue();
                }
            }
            System.out.println("队列为空, 还不能消费");
            Thread.sleep(200L);
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
