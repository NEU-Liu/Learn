package dns.record;



import dns.Domain;

import java.io.IOException;
import java.nio.ByteBuffer;



public class Ns extends RecordData<Ns> {

	private final Domain domain;

	public Ns() {
		this(new Domain());
	}

	public Ns(Domain domain) {
		this.domain = domain;
	}

	public Domain getDomain() {
		return domain;
	}

	@Override
	public Ns toBytes(ByteBuffer buf) throws IOException {
		domain.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<Ns> fromBytes(ByteBuffer buf) throws IOException {
		domain.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "Ns [domain=" + domain + "]";
	}
}