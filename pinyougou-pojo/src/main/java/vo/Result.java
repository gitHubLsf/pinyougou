package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 增删改的响应体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    private boolean success;

    private String message;

}
