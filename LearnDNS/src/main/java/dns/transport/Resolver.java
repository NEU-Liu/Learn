package dns.transport;

import dns.*;


import java.io.IOException;
import java.lang.Class;
import java.net.InetAddress;
import java.util.Random;



public class Resolver {

	private static final Random RANDOM = new Random();

	public static final int DNS_PORT = 53;

	private final InetAddress dnsServer;
	private boolean useUdp = true;

	public Resolver(InetAddress dnsServer) {
		this.dnsServer = dnsServer;
	}

	public void setUseUdp(boolean useUdp) {
		this.useUdp = useUdp;
	}

	public Message request(String domain) throws IOException {
		return this.request(domain, Type.ALL);
	}

	public Message request(String domain, Type questionType) throws IOException {
		return this.request(domain, dns.Class.IN, questionType);
	}

	public Message request(String domain, dns.Class questionClass) throws IOException {
		return this.request(domain, questionClass, Type.ALL);
	}

	public Message request(String domain, dns.Class questionClass, Type questionType) throws IOException {
		Message requestMessage = createMessage(domain, questionClass, questionType);
		return getReply(requestMessage);
	}

	public Message getReply(Message requestMessage) throws IOException {
		if (requestMessage.getHeader().getId() == 0) {
			requestMessage.getHeader().setId((short) RANDOM.nextInt(1 << 15));
		}

		DnsTransport transport;
		if (useUdp) {
			transport = new UdpTransport(dnsServer);
		} else {
			transport = new TcpTransport(dnsServer);
		}
		return getReply(requestMessage, transport);
	}

	public Message getReply(Message requestMessage, DnsTransport transport) throws IOException {
		return transport.sendQuery(requestMessage);
	}

	private Message createMessage(String domain, dns.Class questionClass, Type questionType) {
		Question question = new Question();
		question.setDomain(Domain.fromQName(domain));
		question.setQuestionClass(questionClass);
		question.setQuestionType(questionType);

		Header header = new Header();
		header.setOpcode(Header.Opcode.QUERY);
		header.setRequest(true);
		header.setRecursionDesired(true);

		Message requestMessage = new Message();
		requestMessage.setHeader(header);
		requestMessage.getQuestions().add(question);

		return requestMessage;
	}

}
