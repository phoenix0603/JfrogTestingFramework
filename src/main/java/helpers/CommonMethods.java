package helpers;

import clients.RestClients;
import com.jayway.restassured.response.Response;
import entities.Repositories.RepositoryTreeBrowserResponse;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static helpers.ApiUrls.repositoryTreeBrowserApi;
import static helpers.GlobalValues.artifactoryURL;
import static com.jayway.restassured.RestAssured.given;
import static helpers.GlobalValues.configuration;

public class CommonMethods {

    public List<String> getAllPackagesMetadataFromRepo(String repositoryName) {
        try {
            List<String> filesFromMetadata = new ArrayList<>();
            List<String> packages = new ArrayList<>();
            scanPackages("dists", packages);
            for (String str : packages
                    ) {
                String temp = given().auth().basic(configuration.getUsers().getAdmin().getUserName(), configuration.getUsers().getAdmin().getPassword()).get(artifactoryURL + "/" + repositoryName + "/" + str).prettyPrint();
                Pattern pattern = Pattern.compile("Filename: (.*)");
                Matcher matcher = pattern.matcher(temp);
                while (matcher.find()) {
                    filesFromMetadata.add(matcher.group(1));
                }
            }

            return filesFromMetadata;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void scanPackages(String path, List<String> container) {
        try {
            String request = "{\"type\":\"junction\",\"repoType\":\"local\",\"repoKey\":\"ubuntu\",\"path\":\"" + path + "\",\"text\":\"ubuntu\",\"trashcan\":false}";
            RepositoryTreeBrowserResponse[] response = given().auth().basic(configuration.getUsers().getAdmin().getUserName(), configuration.getUsers().getAdmin().getPassword())
                    .contentType("application/json")
                    .body(request).post(artifactoryURL + repositoryTreeBrowserApi).as(RepositoryTreeBrowserResponse[].class);
            for (RepositoryTreeBrowserResponse elem : response
                    ) {
                if (elem.getRepoPkgType() != null) {
                    if (elem.getPath().endsWith("Packages")) {
                        container.add(elem.getPath());
                    }
                } else {
                    scanPackages(elem.getPath(), container);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadZipWith100FilesFromArtifactory() {
        try {
            InputStream stream = given().headers("Authorization", "Basic ZXZnZW55bDpEa3RmYjk0a0A=").get("https://jfinsttest.jfrog.io:443/jfinsttest/blazemeter/debian/test-data/100.zip").getBody().asInputStream();
            OutputStream outputStream = new FileOutputStream(new File("100.zip"));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = stream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }


        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("File wasn't downloaded.");
        }


    }

    public void unZipFile(String zipFile, String outputFolder) {
        //Open the file
        try (ZipFile file = new ZipFile("100.zip")) {
            FileSystem fileSystem = FileSystems.getDefault();
            //Get file entries
            Enumeration<? extends ZipEntry> entries = file.entries();

            //We will unzip files in this folder
            String uncompressedDirectory = "uncompressed/";
            Files.createDirectory(fileSystem.getPath(uncompressedDirectory));

            //Iterate over entries
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                //If directory then create a new directory in uncompressed folder
                if (entry.isDirectory()) {
                    System.out.println("Creating Directory:" + uncompressedDirectory + entry.getName());
                    Files.createDirectories(fileSystem.getPath(uncompressedDirectory + entry.getName()));
                }
                //Else create the file
                else {
                    InputStream is = file.getInputStream(entry);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    String uncompressedFileName = uncompressedDirectory + entry.getName();
                    Path uncompressedFilePath = fileSystem.getPath(uncompressedFileName);
                    Files.createFile(uncompressedFilePath);
                    FileOutputStream fileOutput = new FileOutputStream(uncompressedFileName);
                    while (bis.available() > 0) {
                        fileOutput.write(bis.read());
                    }
                    fileOutput.close();
                    System.out.println("Written :" + entry.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("File wasn't decompressed.");
        }
    }

    public String uploadFilesToArtifactory() {
        List<String> filesPath = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get("100.csv"))) {
            String returnTimeValue="";
            filesPath = stream.collect(Collectors.toList());

            int i=0;
            for (String data : filesPath
                    ) {
                String[] params = data.split(",");
                File initialFile = new File("uncompressed/" + params[0]);
                InputStream targetStream = new FileInputStream(initialFile);


                java.net.URL url = new java.net.URL(configuration.getArtifactoryUrl() + "/" + configuration.getArtifactoryDefaultRepository() + "/" + params[0] + ";deb.distribution=" + params[1] + ";deb.component=" + params[2] + ";deb.architecture=" + params[3]);
                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);

                byte [] fileContents = org.apache.commons.io.IOUtils.toByteArray(targetStream);
                String authorization = "Basic " + new String(new org.apache.commons.codec.binary.Base64().encode((configuration.getUsers().getAdmin().getUserName()+":"+configuration.getUsers().getAdmin().getPassword()).getBytes()));
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Authorization", authorization);

                if(conn.getResponseCode() != 201)
                {
                    Assert.fail("File: " + params[0] + " wasnt uploaded.");
                }
                if(i==0){
                  getLogFile();
                  returnTimeValue =  getLastTimeFromArtifactoryLog();
                }
                i++;
            }
            return returnTimeValue;
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        return null;
    }

    public void getLogFile() {
        try {
            InputStream inputStream = given().auth().basic(configuration.getUsers().getAdmin().getUserName(), configuration.getUsers().getAdmin().getPassword())
                    .get(configuration.getArtifactoryUrl() + "/api/systemlogs/downloadFile?id=artifactory.log").getBody().asInputStream();
            OutputStream outputStream = new FileOutputStream(new File("artifactory.log"));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }


        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    public String getLastTimeFromArtifactoryLog() {
        try {
            Stream<String> stream = Files.lines(Paths.get("artifactory.log"));
            List<String> log = stream.collect(Collectors.toList());
            return log.get(log.size() - 1).substring(0,23).trim();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
    public void getTimeOfLastMetadataCalculation(String beginTime){

        try {
            Stream<String> stream = Files.lines(Paths.get("artifactory.log"));
            List<String> log = stream.collect(Collectors.toList());
            int i =0;
            for (String str: log
                 ) {

                if(str.startsWith(beginTime)){
                    break;
                }
                i++;

            }
            List<String> newLog = log.subList(i,log.size());
            String endTime = newLog.get(newLog.size()-1).substring(0,23);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

            Date d1 = null;
            Date d2 = null;


                d1 = format.parse(beginTime);
                d2 = format.parse(endTime);

                //in milliseconds
                long diff = d2.getTime() - d1.getTime();

            Pattern pattern = Pattern.compile("took (\\d*) ms");
            Integer time = 0;
            for (String str :
                    newLog) {

                Matcher matcher = pattern.matcher(str);
                while (matcher.find()) {
                   time += Integer.parseInt(matcher.group(1));
                }

            }
            Assert.assertTrue("Time from start to end is "+ diff +" ms. Summ of calculation time is " + time +" ms.",true);
            System.out.println("Time from start to end is "+ diff +" ms. Summ of calculation time is " + time +" ms.");
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
    public void  removeFilesAndFolder(){

        try {
            FileUtils.deleteDirectory(new File("uncompressed/"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}