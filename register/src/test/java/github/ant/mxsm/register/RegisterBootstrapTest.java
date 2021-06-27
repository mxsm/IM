package github.ant.mxsm.register;

import org.junit.jupiter.api.Test;

/**
 * @author mxsm
 * @Date 2021/6/26
 * @Since
 */
class RegisterBootstrapTest {

    @Test
    void main() {
        new Thread(() -> {
            RegisterBootstrap.main(null);
        }).start();

    }

    @Test
    void mainPrintHelp() {
        new Thread(() -> {
            String[] args = "-h".split(",");
            RegisterBootstrap.main(args);
        }).start();

    }

    @Test
    void mainCfileNotExists() {
        new Thread(() -> {
            String[] args = "-c a".split(",");
            RegisterBootstrap.main(args);
        }).start();

    }

    @Test
    void mainCfileIsEmpty() {
        new Thread(() -> {
            String[] args = "-c ".split(",");
            RegisterBootstrap.main(args);
        }).start();

    }

    @Test
    void main0() {
        new Thread(() -> {
            RegisterBootstrap.main0(null);
        }).start();

    }
}