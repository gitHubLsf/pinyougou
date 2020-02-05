package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 增删改的响应体
 *
 * @author linshaofeng
 * @date 2020/2/5 16:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result implements Serializable {

    private boolean success;

    private String message;

}
