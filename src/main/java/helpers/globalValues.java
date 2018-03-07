package helpers;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class globalValues {

    public static String artifactoryURL;

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


    }




}
