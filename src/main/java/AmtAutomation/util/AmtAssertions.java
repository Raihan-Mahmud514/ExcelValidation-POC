package AmtAutomation.util;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
public class AmtAssertions {
    public static Assertion initializeHardAsserts() throws Exception {
        Assertion hardAssert = new Assertion();
        return  hardAssert;
    }
    public static SoftAssert initializeSoftAsserts() throws Exception {

        SoftAssert softAssert = new SoftAssert();
        return  softAssert;
    }
}
