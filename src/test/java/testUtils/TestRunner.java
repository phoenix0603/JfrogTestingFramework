package testUtils; /**
 * Created by logovskoy
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;


/**
 * @RunWith Class with this annotation will run a Cucumber Feature. The class should be empty without any fields or methods.
 * @CucumberOptions This annotation provides the same options as the cucumber command line.
 * @params format What formatter(s) to use.
 * @params features The paths to the feature(s).
 * @params glue Where to look for glue code (stepdefs and hooks).
 * @params tags Identify scenarios or features to run.
 */

@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty",
        //"html:target/cucumber-htmlreport",
        "json:target/cucumber-report.json",
        "junit:target/cucumber-junit-report/cuc.xml"}
        , glue = {"stepdefinition", "testUtils"}
        , features = {"src/test/resources"}
        , tags = {"@debian"}
)
public class TestRunner {

    /**
     * This method is used to initialize reporting classes and set up webdriver
     * Also adds one test to report - that checks log in process - in case of fail on login page
     * - report will contain this information
     */
    @BeforeClass
    public static void BeforeClass() {

    }

    /**
     * Initializes reporting classes and sets up webdriver
     */
    @AfterClass
    public static void AfterClass()  {

    }


}
