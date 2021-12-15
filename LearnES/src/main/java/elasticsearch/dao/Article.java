package elasticsearch.dao;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;
import java.util.Random;

/**
 * @author liujd65
 * @date 2021/12/2 17:16
 **/
@Data
@NoArgsConstructor
public class Article {

    private int id;
    private String authorName;
    private String title;
    private String content;
    private Date date;


    public static String randomInstance(){
        Article article = new Article();
        article.setId(new Random().nextInt(Integer.MAX_VALUE));
        article.setAuthorName(RandomStringUtils.randomAlphabetic(6));
        article.setTitle(RandomStringUtils.randomAlphabetic(12));
        article.setContent(RandomStringUtils.randomAlphabetic(0,1000));
        article.setDate(new Date());
        return JSON.toJSONString(article);
    }




}
