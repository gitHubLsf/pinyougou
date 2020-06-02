import com.lsf.pinyougou.util.IdWorker;
import org.junit.Test;

public class IdWorkerTest {

    @Test
    public void idWorkerTest1() {
        IdWorker idWorker = new IdWorker();

        // 测试产生 10 个 ID，每个 ID 产生之间间隔 1 毫秒
        for (int i = 0; i < 10; i++) {
            System.out.println("ms: " +  System.currentTimeMillis() + ", id: " + idWorker.nextId());

            // 睡眠 1 毫秒
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
