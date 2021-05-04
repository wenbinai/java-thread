package thread.chap10;

public class CountupThread extends Thread {
    private long counter = 0;
    private volatile boolean shutdownRequested = false;

    // 关闭请求
    public void shutdownRequest() {
        shutdownRequested = true;
        interrupt();
    }

    // 判断是否关闭
    public boolean isShutdownRequested() {
        return shutdownRequested;
    }

    @Override
    public void run() {
        try {
            while (!isShutdownRequested()) {
                doWork();
            }
        } catch (InterruptedException e) {
        } finally {
            doShutdown();
        }
    }

    private void doShutdown() {
        System.out.println("doShutdown: counter = " + counter);
    }

    private void doWork() throws InterruptedException {
        counter++;
        System.out.println("doWorker: counter = " + counter);
        Thread.sleep(500);
    }
}
