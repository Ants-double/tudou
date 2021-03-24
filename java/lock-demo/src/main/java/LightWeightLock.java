import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.nio.ByteOrder;
import java.util.concurrent.CountDownLatch;

/**
 * @author lyy
 * @Deprecated
 * @date 2020/8/7
 */
public class LightWeightLock {
    static CountDownLatch countDownLatch = new CountDownLatch(1000000000);


    public static void main(String[] args) throws InterruptedException {
        LockObject lockObject=new LockObject();
        long start=System.currentTimeMillis();
        for (int i=0;i<2;i++){
            new Thread(()->{
                while (countDownLatch.getCount()>0){
                    lockObject.parse();
                }
            }).start();

        }
        countDownLatch.await();
        long end=System.currentTimeMillis();
        System.out.printf("%s ms",end-start);
    }



    public static void main2(String[] args) {
        LockObject lockObject=new LockObject();
        long start=System.currentTimeMillis();
        for (int i=0;i<1000000000L;i++){
            lockObject.parse();
        }
        long end=System.currentTimeMillis();
        System.out.printf("%s ms",end-start);
    }





    public static void main1(String[] args) {
        LockObject lockObject=new LockObject();
        System.out.println(VM.current().details());
        System.out.println(ByteOrder.nativeOrder());
        System.out.println("锁之前");
        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
        synchronized (lockObject){
            System.out.println("锁中");
            lockObject.flag=true;
            System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
        }

        System.out.println("锁释放");
        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
    }
}
