package elasticsearch.work;

import lombok.*;

/**
 * @author caoyouyuan
 * @version 1.0
 * @date 2021/8/10 22:24
 * @desc es配置文件
 *
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EsConfig {

    private String username;

    private String password;

    private String hostname;

    private int port;

}
