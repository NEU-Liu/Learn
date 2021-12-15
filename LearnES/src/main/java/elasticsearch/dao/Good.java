package elasticsearch.dao;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;
import java.util.Random;

/**
 * @author liujd65
 * @date 2021/12/3 14:34
 **/
@Data
public class Good {
    private int id;
    private double price;
    private String name;
    private String description;

    public static String randomInstance(){
        Good good = new Good();
        good.setId(new Random().nextInt(Integer.MAX_VALUE));
        good.setPrice(new Random().nextInt(10_000));
        good.setName(RandomStringUtils.randomAlphabetic(8));
        good.setDescription(RandomStringUtils.randomAlphabetic(1024));
        return JSON.toJSONString(good);
    }


}
