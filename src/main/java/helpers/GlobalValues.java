package helpers;

import entities.Configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class GlobalValues {

    public static String artifactoryURL;
    public static Configuration configuration;
    static {
        /*
         * Read Configuration.xml file
         */
        XMLConfiguration config = null;
        try {
            config = new XMLConfiguration("configuration.xml");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        artifactoryURL = config.getString("artifactoryUrl");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            configuration = (Configuration) jaxbUnmarshaller.unmarshal(new File("configuration.xml"));
        }  catch (JAXBException e) {
            e.printStackTrace();
        }


    }




}
