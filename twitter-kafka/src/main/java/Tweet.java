import java.util.Date;
import twitter4j.User;

public class Tweet {
    private Date date;
    private long id;
    private String text;
    private String source;
    private boolean isTruncated;
    private double latitude;
    private double longitude;
    private boolean isFavorited;
    private String user;

    public Tweet(long id,Date date, String text,String source,boolean isTruncated,double latitude, double longitude,boolean isFavorited,String user) {
        this.date = date;
        this.id = id;
        this.text = text;
        this.source = source;
        this.isTruncated = isTruncated;
        this.latitude = latitude;
        this.latitude = latitude;
        this.isFavorited = isFavorited;
        this.user = user;

    }
    public Date getCreatedAt() {
        return date;
    }

    public void setCreatedAt(Date createdDate) {
        this.date = createdDate;
    }

    public long getId(){
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public boolean getIsTruncated() {
        return isTruncated;
    }

    public void setIsTruncated(boolean isTruncated) {
        this.isTruncated = isTruncated;
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public boolean isFavorited() {
        return isFavorited;
    }

    public void isFavorited(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
