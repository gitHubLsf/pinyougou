import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class MD5Test {

    @Test
    public void md5Test() {
        System.out.println(DigestUtils.md5Hex("linsf"));

    }
}
