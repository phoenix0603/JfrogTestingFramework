package entities.Repositories;
import java.util.HashMap;
import java.util.Map;

public class RepositoryTreeBrowserResponse {

    private String repoKey;
    private String path;
    private String text;
    private String repoType;
    private Boolean hasChild;
    private Boolean local;
    private String type;
    private Boolean compacted;
    private Boolean distribution;
    private Boolean trash;
    private String repoPkgType;
    private String mimeType;
    private String fileType;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getRepoKey() {
        return repoKey;
    }

    public void setRepoKey(String repoKey) {
        this.repoKey = repoKey;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRepoType() {
        return repoType;
    }

    public void setRepoType(String repoType) {
        this.repoType = repoType;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    public Boolean getLocal() {
        return local;
    }

    public void setLocal(Boolean local) {
        this.local = local;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getCompacted() {
        return compacted;
    }

    public void setCompacted(Boolean compacted) {
        this.compacted = compacted;
    }

    public Boolean getDistribution() {
        return distribution;
    }

    public void setDistribution(Boolean distribution) {
        this.distribution = distribution;
    }

    public Boolean getTrash() {
        return trash;
    }

    public void setTrash(Boolean trash) {
        this.trash = trash;
    }

    public String getRepoPkgType() {
        return repoPkgType;
    }

    public void setRepoPkgType(String repoPkgType) {
        this.repoPkgType = repoPkgType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
