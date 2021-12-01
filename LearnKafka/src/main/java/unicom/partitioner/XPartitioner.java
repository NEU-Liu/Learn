package unicom.partitioner;

import org.apache.kafka.common.Cluster;
import org.apache.kafka.clients.producer.Partitioner;
import java.util.Map;

public class XPartitioner implements Partitioner {
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        return 0;
    }

    public void close() {

    }


    public void configure(Map<String, ?> configs) {

    }
}
