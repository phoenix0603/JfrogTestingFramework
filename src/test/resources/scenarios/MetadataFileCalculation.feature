@Critical @Metadata
Feature: Metadata calculation time

    @debian @x86
    Scenario: Debian upload
     Given Artifactory instance is UP
     #And I download all available <"debian"> packages in <"ubuntu"> repository
     #And I see that package contains <"102496"> records
     And I download ZIP archive with 100 debian packages from cloud artifactory
     And I upload <"100"> additional files from <"100.csv"> file
     #And I download all available <"debian"> packages in <"ubuntu"> repository
     #And I see that package contains <"500100"> records
     And I download Artifactory log
     Then I count time from the begining of file uploading to the end of metadata calculation and  count time which was spent on metadata calculation
