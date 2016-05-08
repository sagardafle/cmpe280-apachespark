import java.util.*;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer {
	public static void main(String[] args) {
		long events = Long.parseLong(args[0]);
		Random rnd = new Random();
		Properties props = new Properties();
		props.put("metadata.broker.list", "localhost:9092");
		props.put("producer.type", "sync");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("partitioner.class", "KafkaPartitioner");
		props.put("request.required.acks", "1");
		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> producer = new Producer<String, String>(config);
		for (long nEvents = 0; nEvents < events; nEvents++) {
			System.out.println("creating event " + nEvents);
			long runtime = new Date().getTime();
			String tempreture = "" + rnd.nextInt(9);
			String msg = runtime + " Current Tempreture is :" + tempreture;
			KeyedMessage<String, String> data = new KeyedMessage<String, String>("test", tempreture, msg);
			try {
			    Thread.sleep(1000);                 //3000 milliseconds is three second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			producer.send(data);
		}
		producer.close();
	}
}