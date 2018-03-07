@Critical @Metadata
Feature: Metadata calculation time

  @test
  Scenario: test
    Given Test is created
    Then Test is passed


    @debian
    Scenario: Debian upload
      Given Artifactory instance is UP
    #  And I download Debian packages
    #  And I see that package contains <"500"> records
     # And I upload <"100"> additional files from <"100.csv"> file
      #And I download Debian packages
     # And I see that package contains <"600"> records
     # And I download Artifactory log
     # Then I count time from the begining of file uploading to the end of metadata calculation
     # And I count time which was spent on metadata calculation