package testUtils;


import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;


/**
 * Created by logovskoy
 * This class is for Cucumber internal use
 */
public class BeforeAfter  {



    /**
     * This method resets values to default before each scenario
     * In case if current scenario belongs to different feature than the one that was run previously
     * Then report is sent to reporting service and a new report is created
     * Also if next scenario requires new driver instance - it restarts driver.
     */
    @Before
    public void setUp(Scenario scenario) {

    }

    /**
     * This method returns user to homepage after each scenario
     * Also it reverts the state of courses, for an example deleting test
     * that was created during test
     */
    @After
    public void tearDown(Scenario scenario) throws Exception {


    }


}
