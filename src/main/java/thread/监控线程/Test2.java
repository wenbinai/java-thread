package thread.监控线程;

import java.beans.IntrospectionException;
import java.util.concurrent.TimeUnit;

public class Test2 {
    public static void main(String[] args) {
        EmptyLifecycle<String> lifeCycle = new EmptyLifecycle<String>() {
            @Override
            public void onStart(Thread thread) {
                System.out.println("启动");
            }

            @Override
            public void onRunning(Thread thread) {
                System.out.println("正在运行");
            }

            @Override
            public void onFinish(Thread thread, String result) {
                System.out.println("The result is: + " + result);
            }

            @Override
            public void onError(Thread thread, Exception e) {
                System.out.println("发生异常");
            }
        };

        ObservableThread<String> observaleThrad = new ObservableThread<>(lifeCycle, () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" finish done.");
            return "Hello Observer";
        });
        observaleThrad.start();
    }
}
