package thread.监控线程;

public class ObservableThread<T> extends Thread implements Observable {
    private final TaskLifecycle<T> lifecycle;
    private Task<T> task;
    private Cycle cycle;

    public ObservableThread(Task<T> task) {
        this(new EmptyLifecycle<T>(), task);
    }

    public ObservableThread(TaskLifecycle<T> lifecycle, Task<T> task) {
        super();
        if (task == null) {
            throw new IllegalArgumentException("The task is required");
        }
        this.lifecycle = lifecycle;
        this.task = task;
    }

    @Override
    public Cycle getCycle() {
        return this.cycle;
    }

    private void update(Cycle cycle, T result, Exception e) {
        this.cycle = cycle;
        if (lifecycle == null) {
            return;
        }
        try {
            switch (cycle) {
                case STARTED:
                    this.lifecycle.onStart(currentThread());
                    break;
                case RUNNING:
                    this.lifecycle.onRunning(currentThread());
                    break;
                case DONE:
                    this.lifecycle.onFinish(currentThread(), result);
                    break;
                case ERROR:
                    this.lifecycle.onError(currentThread(), e);
                    break;
            }
        } catch (Exception ex) {
            if (cycle == Cycle.ERROR) {
                throw ex;
            }
        }
    }

    /**
     * final 表示不允许子类再次重写
     */
    @Override
    public final void run() {
        this.update(Cycle.STARTED, null, null);
        try {
            this.update(Cycle.RUNNING, null, null);
            T result = this.task.call();
            this.update(Cycle.DONE, result, null);
        } catch (Exception e) {
            this.update(Cycle.ERROR, null, e);
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }
}
