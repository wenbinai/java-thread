package thread.监控线程;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        ObservableThread<Object> objectObservableThread = new ObservableThread<>(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("finish done.");
            return null;
        });
        objectObservableThread.start();
    }
}
