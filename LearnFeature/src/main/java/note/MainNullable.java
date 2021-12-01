package note;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * @author liujd65
 * @date 2021/11/11 9:12
 **/


class TT{

    String id;

    String name;

    public TT(@NotNull String id, @Nullable String name) {
        this.id = id;
        this.name = name;
    }
}


public class MainNullable {
    public static void main(String[] args) {
        TT liu = new TT(null, "liu");
        System.out.println(liu);
    }
}
