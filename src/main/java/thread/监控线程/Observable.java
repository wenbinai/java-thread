package thread.监控线程;

/**
 * 观察者模式: 单个对象的状态发生变化需要通知第三方
 */
public interface Observable {
    enum Cycle {
        STARTED,
        RUNNING,
        DONE,
        ERROR
    }

    Cycle getCycle();

    void start();

    void interrupt();
}
