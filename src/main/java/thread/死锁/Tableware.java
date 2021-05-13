package thread.死锁;

public class Tableware {
    private String toolName;

    public Tableware(String toolName) {
        this.toolName = toolName;
    }

    @Override
    public String toString() {
        return "Tool: " + toolName;
    }
}
