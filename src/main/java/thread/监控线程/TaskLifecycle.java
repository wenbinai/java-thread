package thread.监控线程;

public interface TaskLifecycle<T> {
    // 任务启动触发该方法
    void onStart(Thread thread);

    // 任务启动 触发该方法
    void onRunning(Thread thread);

    // 任务结束
    void onFinish(Thread thread, T result);

    // 任务出现异常
    void onError(Thread thread, Exception e);


}


class EmptyLifecycle<T> implements TaskLifecycle<T> {
    @Override
    public void onStart(Thread thread) {

    }

    @Override
    public void onRunning(Thread thread) {

    }

    @Override
    public void onFinish(Thread thread, T result) {

    }

    @Override
    public void onError(Thread thread, Exception e) {

    }
}
