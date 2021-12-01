package dns;

import dns.record.Record;
import dns.transport.Resolver;

import java.net.InetAddress;
import java.util.Random;


public class DnsTest {

    public static final Random RANDOM = new Random();
    //https://www.zhihu.com/question/32229915
    public static final String DNS_HOST = "223.5.5.5";

    public static final int PORT = 53;

    public static void main(String[] args) throws Exception {
        Resolver r = new Resolver(InetAddress.getByName(DNS_HOST));
        Message reply = r.request("zhihu.com");
		// System.err.println(reply);
        System.err.println("questions:");
        for (Question q : reply.getQuestions()) {
            System.err.println(" - " + q);
        }
        System.err.println("answers:");
        for (Record a : reply.getAnswers()) {
            System.err.println(" - " + a);
        }
    }

}
