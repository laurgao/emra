// Dummy interface for typing of functions with no inputs and no outputs,
// so we can pass callback functions as arguments to methods.
@FunctionalInterface
public interface VoidFunction {
    void run();
}
