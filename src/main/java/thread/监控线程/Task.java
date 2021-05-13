package thread.监控线程;

@FunctionalInterface
public interface Task<T> {
    T call();
}
