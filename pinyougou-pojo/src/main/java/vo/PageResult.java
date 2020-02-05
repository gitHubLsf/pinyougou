package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果类,后端返回给前端的数据
 * 由于分页结果需要在网络中传输,所以此类要实现序列化接口
 *
 * @author linshaofeng
 * @date 2020/2/5 13:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageResult implements Serializable {

    /**
     * 总记录数
     */
    private long total;

    /**
     * 当前页数据
     */
    private List rows;
}
