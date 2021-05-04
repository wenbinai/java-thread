package thread.chap10;


public class Main {
    public static void main(String[] args) {
        System.out.println("main: BEGIN");
        try {
            CountupThread t = new CountupThread();
            t.start();
            Thread.sleep(1000);
            System.out.println("main: shutdownRequest");
            t.shutdownRequest();
            System.out.println("main: join");
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("main: END");
    }
}

// 二叉树, 根节点root节点到叶子节点的路径集合
