package unicom.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class CounterInterceptor implements ProducerInterceptor {

    int success = 0;
    int error = 0;
    public void configure(Map<String, ?> configs) {

    }

    public ProducerRecord onSend(ProducerRecord record) {
        return record;
    }

    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (exception==null){
            success+=1;
        }else {
            error+=1;
        }
    }

    public void close() {
        System.out.println("success:" + success);
        System.out.println("error:" + error);
    }


}
