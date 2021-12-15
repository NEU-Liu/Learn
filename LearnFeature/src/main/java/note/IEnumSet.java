package note;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;

enum Season {
    SPRING, SUMMER, FALL, WINTER
}

public class IEnumSet {
    public static void main(String[] args) {


        EnumSet es1 = EnumSet.allOf(Season.class);

        System.out.println(es1);

        EnumSet es2 = EnumSet.noneOf(Season.class);

        System.out.println(es2);

        es2.add(Season.WINTER);
        es2.add(Season.SPRING);

        System.out.println(es2);

        EnumSet es3 = EnumSet.of(Season.SUMMER, Season.WINTER);

        System.out.println(es3);
        EnumSet es4 = EnumSet.range(Season.SUMMER, Season.WINTER);

        System.out.println(es4);

        EnumSet es5 = EnumSet.complementOf(es4);
        System.out.println(es5);

        Collection c = new HashSet();
        c.clear();
        c.add(Season.SPRING);
        c.add(Season.WINTER);

        EnumSet es = EnumSet.copyOf(c);

        System.out.println(es);
        c.add("111");
        c.add("222");
        //下面代码出现异常，因为c集合里的元素不是全部都为枚举值
        //es = EnumSet.copyOf(c);
    }
}