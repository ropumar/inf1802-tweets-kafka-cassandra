import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.Serializable;

public class TweetManager implements LifecycleManager , Serializable {
//    StatusListener listener = new MyStatusListener();
    public TwitterStream twitter;
    Producer myProducer;
    @Override
    public void start() {
        String _consumerKey = System.getenv().get("TWITTER_CONSUMER_KEY");
        String _consumerSecret = System.getenv().get("TWITTER_CONSUMER_SECRET");
        String _accessToken = System.getenv().get("TWITTER_ACCESS_TOKEN");
        String _accessTokenSecret = System.getenv().get("TWITTER_ACCESS_TOKEN_SECRET");
        System.out.println(_consumerKey);
        System.out.println(_consumerSecret);
        System.out.println(_accessToken);
        System.out.println(_accessTokenSecret);

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(_consumerKey)
                .setOAuthConsumerSecret(_consumerSecret)
                .setOAuthAccessToken(_accessToken)
                .setOAuthAccessTokenSecret(_accessTokenSecret);


        twitter = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
        myProducer = new Producer();

        twitter.addListener(new MyStatusListener(myProducer));
        twitter.filter("Bolsonaro");
    }

    @Override
    public void stop() {
        myProducer.ProducerClose();
        twitter.cleanUp();
        twitter.clearListeners();
    }
}
