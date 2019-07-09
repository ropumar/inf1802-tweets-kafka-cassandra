

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class TweetManager implements LifecycleManager, Serializable {

    private boolean isRunning = true;
    Cluster cluster = null;
    @Override
    public void start() {

        cluster = Cluster.builder()
                .addContactPoint("localhost")
                .build();
        Session session = cluster.connect();
        ResultSet rs = session.execute ("select release_version from system.local");
        Row row = rs.one();
        System.out.println(row.getString("release_version"));

        KeyspaceRepository sr = new KeyspaceRepository(session);
        sr.createKeyspace("tweet","SimpleStrategy", 1);
        System.out.println("create repository");

        sr.useKeyspace("tweet");
        System.out.println("Using repository tweet");
        TweetRepository br = new TweetRepository(session);
        br.createTable();
        br.createTableByUser();

        isRunning = true;
        // Criar as propriedades do consumidor
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TweetDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "consumer_demo");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Criar o consumidor
        KafkaConsumer<String ,Tweet> consumer = new KafkaConsumer<>(properties);

        // Subscrever o consumidor para o nosso(s) tópico(s)
        consumer.subscribe(Collections.singleton("meu_topico"));
        System.out.println(2);
        Thread myConsumer = new Thread (new Runnable(){
            public void run(){
                // Ler as mensagens
                while (isRunning) {  // Apenas como demo, usaremos um loop infinito
                    ConsumerRecords<String, Tweet> poll = consumer.poll(Duration.ofMillis(1000));
                    for (ConsumerRecord<String, Tweet> record : poll) {
                        Tweet tw1 = (Tweet)record.value();
                        br.insertTweet(tw1);
//                      System.out.println(1);
                        System.out.println(record.topic() + " - " + record.partition() + " - " + record.value());
                    }
                }
            }
        });
        myConsumer.start();
    }

    @Override
    public void stop() {
        isRunning = false;
    }
}
