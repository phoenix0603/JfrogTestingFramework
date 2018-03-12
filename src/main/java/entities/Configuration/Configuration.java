package entities.Configuration;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Configuration {

    private Users users;

    private String artifactoryUrl;

    public String getArtifactoryDefaultRepository() {
        return artifactoryDefaultRepository;
    }

    public void setArtifactoryDefaultRepository(String artifactoryDefaultRepository) {
        this.artifactoryDefaultRepository = artifactoryDefaultRepository;
    }

    private String artifactoryDefaultRepository;

    public Users getUsers ()
    {
        return users;
    }

    public void setUsers (Users users)
    {
        this.users = users;
    }

    public String getArtifactoryUrl ()
    {
        return artifactoryUrl;
    }

    public void setArtifactoryUrl (String artifactoryUrl)
    {
        this.artifactoryUrl = artifactoryUrl;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [users = "+users+", artifactoryUrl = "+artifactoryUrl+"]";
    }
}
