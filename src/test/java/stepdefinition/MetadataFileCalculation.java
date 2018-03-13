package stepdefinition;

import clients.RestClients;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import entities.Configuration.Users;
import entities.Repositories.AllRepositoriesInfo;
import helpers.CommonMethods;
import org.junit.Assert;


import java.util.List;

import static com.jayway.restassured.RestAssured.get;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.specification.ProxySpecification.auth;
import static helpers.ApiUrls.repositoriesApi;
import static helpers.ApiUrls.versionApi;
import static helpers.GlobalValues.artifactoryURL;
import static helpers.GlobalValues.configuration;


public class MetadataFileCalculation {
    RestClients restClients = new RestClients();
    CommonMethods commonMethods = new CommonMethods();


    @Given("^Artifactory instance is UP$")
    public void artifactoryInstanceIsUP() throws Throwable {
        given().auth().basic(configuration.getUsers().getAdmin().getUserName(),configuration.getUsers().getAdmin().getPassword())
                .get(artifactoryURL+ versionApi)
                .then().statusCode(200);
    }

    @And("^I download all available Debian packages$")
    public void iDownloadAllAvailableDebianPackages() throws Throwable {
     AllRepositoriesInfo[] allAvailableRepos=  given().auth().basic(configuration.getUsers().getAdmin().getUserName(),configuration.getUsers().getAdmin().getPassword())
               .and().get(artifactoryURL+repositoriesApi).as(AllRepositoriesInfo[].class);


    }

    List<String> filesBeforeUloading;
    @And("^I download all available <\"([^\"]*)\"> packages in <\"([^\"]*)\"> repository$")
    public void iDownloadAllAvailablePackagesInRepository(String repoType, String repoName) throws Throwable {

        filesBeforeUloading = commonMethods.getAllPackagesMetadataFromRepo(repoName);
    }

    @And("^I see that package contains <\"([^\"]*)\"> records$")
    public void iSeeThatPackageContainsRecords(String arg0) throws Throwable {
        Assert.assertTrue("ERROR. Wrong size. Curent size is "+ filesBeforeUloading.size()+", but should be "+arg0,filesBeforeUloading.size()==Integer.parseInt(arg0));
    }
    public String timeOfStart;
    @And("^I upload <\"([^\"]*)\"> additional files from <\"([^\"]*)\"> file$")
    public void iUploadAdditionalFilesFromFile(String arg0, String arg1) throws Throwable {
        timeOfStart= commonMethods.uploadFilesToArtifactory();

    }

    @And("^I download ZIP archive with (\\d+) debian packages from cloud artifactory$")
    public void iDownloadZIPArchiveWithDebianPackagesFromCloudArtifactory(int arg0) throws Throwable {
        commonMethods.downloadZipWith100FilesFromArtifactory();
        commonMethods.removeFilesAndFolder();
        commonMethods.unZipFile("100.zip","Zip");
    }

    @And("^I download Artifactory log$")
    public void iDownloadArtifactoryLog() throws Throwable {
      commonMethods.getLogFile();

    }


    @Then("^I count time from the begining of file uploading to the end of metadata calculation and  count time which was spent on metadata calculation$")
    public void iCountTimeFromTheBeginingOfFileUploadingToTheEndOfMetadataCalculationAndCountTimeWhichWasSpentOnMetadataCalculation() throws Throwable {
        commonMethods.getTimeOfLastMetadataCalculation(timeOfStart);

    }
}
