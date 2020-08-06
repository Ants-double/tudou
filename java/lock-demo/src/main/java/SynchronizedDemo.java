import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.nio.ByteOrder;

/**
 * @author lyy
 * @Deprecated
 * @date 2020/8/6
 */
public class SynchronizedDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("demo synchronized");

        LockObject lockObject=new LockObject();
        System.out.println(Integer.toHexString(lockObject.hashCode()));
        System.out.println(ByteOrder.nativeOrder());
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
        Thread t1 = new Thread(()->{
            synchronized (lockObject) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1 release");
            }
        });
        t1.start();
        Thread.sleep(50000);
        for (int i=0;i<15;i++){
            System.out.println(i);


            synchronized (lockObject){
                lockObject.flag=true;
                System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
            }

        }


        System.out.println(lockObject.hashCode());
        System.out.println(Integer.toHexString(lockObject.hashCode()));
        System.out.println("demo synchronized over");
    }
}
