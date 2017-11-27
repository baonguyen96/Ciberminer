package cyberminer.model;

public class Url {

    private int id;
    private String urlLink;
    private String description;
    private int hitrate =0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHitrate() {
        return hitrate;
    }

    public void setHitrate(int hitrate) {
        this.hitrate = hitrate;
    }
}
