import java.util.concurrent.CountDownLatch;

/**
 * @author lyy
 * @Deprecated
 * @date 2020/8/6
 */
public class LockObject {
    public  boolean flag=false;
    int i;
    public synchronized void parse() {
        LightWeightLock.countDownLatch.countDown();
        i++;
    }
}

