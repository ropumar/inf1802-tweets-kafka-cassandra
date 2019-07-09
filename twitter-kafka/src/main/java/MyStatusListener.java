import twitter4j.*;

import java.util.logging.Logger;

public class MyStatusListener implements StatusListener {
    private static final Logger logger = Logger.getLogger(TweetCollectorResource.class.getName());

    private Producer myProducer;


    public MyStatusListener(Producer myProducer){
        this.myProducer =myProducer;
    }
    @Override
    public void onStatus(Status status) {
        double latitude ;
        double longitude;
        System.out.println(status.getUser().getName() + " : " + status.getText());
        if (status.getGeoLocation()!=null){
            latitude = status.getGeoLocation().getLatitude();
            longitude = status.getGeoLocation().getLongitude();
        }else{
            latitude = 0;
            longitude = 0;
        }

        Tweet tweet = new Tweet(status.getId(),status.getCreatedAt(),status.getText(),status.getSource(),status.isTruncated(),latitude,longitude,status.isFavorited(), status.getUser().getName());
        myProducer.ProducerSendMessage("meu_topico", tweet);
        displayTweet(tweet);
        System.out.println("teste");
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {

    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {

    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
    }

    public void displayTweet (Tweet tweet){
        System.out.println(tweet.getId() + " "+tweet.getCreatedAt()+" "+tweet.getText()+"" +tweet.getSource()+" "+tweet.getIsTruncated()+" "+ tweet.getLatitude()+" "+ tweet.getLongitude()+" "+ tweet.isFavorited()+" "+ tweet.getUser().getClass().getName());
    }

}

