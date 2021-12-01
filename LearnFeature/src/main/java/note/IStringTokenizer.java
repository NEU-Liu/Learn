package note;

import java.util.StringTokenizer;

/**
 * @author liujd65
 * @date 2021/11/24 15:08
 **/
public class IStringTokenizer {
    public static void main(String[] args) {
        String str = "google ,taobao,facebook,zhihu";
        StringTokenizer st=new StringTokenizer(str,",");
        while(st.hasMoreTokens()) {
            System.out.println(st.nextToken().trim());
        }
    }
}
