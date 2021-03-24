import com.sun.deploy.util.StringUtils;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.nio.ByteOrder;

/**
 * @author lyy
 * @Deprecated
 * @date 2020/8/7
 */
public class BiasLock {
    public static void main(String[] args) throws InterruptedException {
        //   Thread.sleep(5000);
        // -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
//        System.out.println("hash大"+Integer.toBinaryString(lockObject.hashCode()));
//        System.out.println("hash小"+Integer.toBinaryString(bytesToInt(changeBytes(intToByteArray(lockObject.hashCode())))));
//        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
        LockObject lockObject = new LockObject();
        System.out.println(VM.current().details());
        System.out.println(ByteOrder.nativeOrder());
        System.out.println("锁之前");
        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
        System.out.printf("hash %s\n",lockObject.hashCode());
        System.out.println("hash大"+Integer.toBinaryString(lockObject.hashCode()));
        System.out.println("hash小"+Integer.toBinaryString(bytesToInt(changeBytes(intToByteArray(lockObject.hashCode())))));
        synchronized (lockObject) {
            System.out.println("锁中");
            lockObject.flag = true;
            System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
        }

        System.out.println("锁释放");
        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());


    }

    public static int bytesToInt(byte[] bytes) {
        int addr = 0;
        if (bytes.length == 1) {
            addr = bytes[0] & 0xFF;
        } else {
            addr = bytes[0] & 0xFF;
            addr = (addr << 8) | (bytes[1] & 0xff);
            addr = (addr << 8) | (bytes[2] & 0xff);
            addr = (addr << 8) | (bytes[3] & 0xff);
        }
        return addr;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    public static byte[] changeBytes(byte[] a) {
        byte[] b = new byte[a.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = a[b.length - i - 1];
        }
        return b;
    }
}
