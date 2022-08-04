package excelvalidation.util;
import java.util.concurrent.TimeUnit;
public class amtUtilities {
    public static void sleep(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        }
        catch (Exception e) {
            /// throw new Exception("Pause between steps was interrupted", e);
            e.printStackTrace();
        }
    }
}
