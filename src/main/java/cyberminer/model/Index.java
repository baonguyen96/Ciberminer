package cyberminer.model;

import java.io.Serializable;

public class Index implements Serializable, Comparable<Index>{
    private int urlId;

    private String shift;
    public int getUrlId() {
        return urlId;
    }

    public void setUrlId(int urlId) {
        this.urlId = urlId;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    @Override
    public int compareTo(Index index) {
        return (this.getShift()).compareTo(index.getShift());
    }


}
