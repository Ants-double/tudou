import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.nio.ByteOrder;

/**
 * @author lyy
 * @Deprecated
 * @date 2020/8/7
 */
public class WaitLock {
    public static void main(String[] args) throws InterruptedException {
        LockObject lockObject=new LockObject();
        System.out.println(VM.current().details());
        System.out.println(ByteOrder.nativeOrder());
        System.out.println("锁之前");
        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
        Thread t1 = new Thread(()->{
            synchronized (lockObject) {
                try {
                    System.out.println("wait 之前");
                    System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
                    lockObject.wait();
                    System.out.println("wait 之后");

                    System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1 release");
            }
        });
        t1.start();

        Thread.sleep(1000);
        System.out.println("t1 锁之前");
        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
        synchronized (lockObject){
            System.out.println("锁中");
            lockObject.flag=true;
            System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
        }

        System.out.println("锁释放");
        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());

        System.gc();
        System.out.println("after gc()");
        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
    }
}
