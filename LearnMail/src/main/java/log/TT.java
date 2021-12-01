package log;

import java.io.PrintStream;
import java.util.logging.Level;

/**
 * @author liujd65
 * @date 2021/11/25 14:24
 **/
public class TT {

    ILogger logger;

    public static void main(String[] args) {
        TT tt = new TT();
        tt.logger.log(Level.CONFIG,"FDA");
        tt.logger.fine("fda");
        tt.logger.config("dafdasfdsafasfdasfdsafdas");
    }


    public TT(){
        logger = new ILogger(this.getClass(), "DEBUG", true, System.out);
    }




}
