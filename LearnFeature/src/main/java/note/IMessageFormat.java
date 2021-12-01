package note;

import java.text.MessageFormat;

/**
 * @author liujd65
 * @date 2021/11/25 14:51
 **/
public class IMessageFormat {
    public static void main(String[] args) {
        String format = MessageFormat.format("Hello,{0}{1}", "World", "!");
        System.out.println(format);
    }
}
