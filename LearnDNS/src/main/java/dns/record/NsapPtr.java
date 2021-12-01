package dns.record;



import dns.Domain;

import java.io.IOException;
import java.nio.ByteBuffer;



public class NsapPtr extends RecordData<NsapPtr> {

	private final Domain domain;

	public NsapPtr() {
		this(new Domain());
	}

	public NsapPtr(Domain domain) {
		this.domain = domain;
	}

	public Domain getDomain() {
		return domain;
	}

	@Override
	public RecordData<NsapPtr> toBytes(ByteBuffer buf) throws IOException {
		domain.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<NsapPtr> fromBytes(ByteBuffer buf) throws IOException {
		domain.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "NsapPtr [domain=" + domain + "]";
	}

}
