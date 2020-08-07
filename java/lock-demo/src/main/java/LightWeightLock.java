import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.nio.ByteOrder;

/**
 * @author lyy
 * @Deprecated
 * @date 2020/8/7
 */
public class LightWeightLock {
    public static void main(String[] args) {
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
