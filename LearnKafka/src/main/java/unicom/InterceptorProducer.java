package unicom;

import org.apache.kafka.clients.producer.*;

import java.util.Arrays;
import java.util.Properties;

public class InterceptorProducer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, Arrays.asList("unicom.interceptor.TimeInterceptor","unicom.interceptor.CounterInterceptor"));

        KafkaProducer kafkaProducer = new KafkaProducer<String,String>(properties);


        int messages = 5;
        for (int i = 0; i < messages; i++) {
            ProducerRecord producerRecord = new ProducerRecord("xtopic","Hello " + i + " !");

            kafkaProducer.send(producerRecord, new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception==null){
                        System.out.println("metadata:" + metadata);
                    }
                }
            });
        }



        kafkaProducer.close();
    }
}
