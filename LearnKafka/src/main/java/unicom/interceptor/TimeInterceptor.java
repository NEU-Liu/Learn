package unicom.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class TimeInterceptor implements ProducerInterceptor {

    public void configure(Map<String, ?> configs) {

    }

    public ProducerRecord onSend(ProducerRecord record) {
        String value = System.currentTimeMillis() + ","  + record.value();
        return new ProducerRecord(record.topic(),record.partition(),record.key(),value,record.headers());
    }

    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    public void close() {

    }


}
