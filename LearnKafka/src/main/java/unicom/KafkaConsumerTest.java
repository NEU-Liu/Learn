package unicom;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;



public class KafkaConsumerTest implements Runnable {

	private final KafkaConsumer<String, String> consumer;
	private ConsumerRecords<String, String> msgList;
	private final String topic;
	private static final String GROUPID = "groupA";

	public KafkaConsumerTest(String topicName) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "127.0.0.1:9092");
		props.put("group.id", GROUPID);
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("auto.offset.reset", "earliest");
		props.put("key.deserializer", StringDeserializer.class.getName());
		props.put("value.deserializer", StringDeserializer.class.getName());
		this.consumer = new KafkaConsumer<String, String>(props);
		this.topic = topicName;
		this.consumer.subscribe(Arrays.asList(topic));

	}


	public void run() {
		int messageNo = 1;
		System.out.println("---------开始消费---------");
		try {
			for (;;) {
					msgList = consumer.poll(1);
					if(null!=msgList&&msgList.count()>0){
					for (ConsumerRecord<String, String> record : msgList) {
						//消费100条就打印 ,但打印的数据不一定是这个规律的
						if(messageNo%100==0){
							//System.out.println("receive: key = " + record.key() + ", value = " + record.value()+" offset==="+record.offset());
							String format = String.format("key:%s,value:%s,offset:%4d",record.key(),record.value(),record.offset());
							System.out.println(format);
							System.out.println(record);
						}
						//当消费了1000条就退出
						if(messageNo%1000==0){
							break;
						}
						messageNo++;
					}
				}else{	
					Thread.sleep(1000);
				}
			}		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			consumer.close();
		}
	}  
	public static void main(String args[]) {
		KafkaConsumerTest test1 = new KafkaConsumerTest("xtopic");
		Thread thread1 = new Thread(test1);
		thread1.start();
	}
}