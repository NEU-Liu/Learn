package dns.record;



import dns.Mailbox;

import java.io.IOException;
import java.nio.ByteBuffer;



public class Mr extends RecordData<Mr> {

	private final Mailbox mailbox;

	public Mr() {
		this(new Mailbox());
	}

	public Mr(Mailbox mailbox) {
		this.mailbox = mailbox;
	}

	public Mailbox getMailbox() {
		return mailbox;
	}

	@Override
	public RecordData<Mr> toBytes(ByteBuffer buf) throws IOException {
		mailbox.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<Mr> fromBytes(ByteBuffer buf) throws IOException {
		mailbox.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "Mr [mailbox=" + mailbox + "]";
	}

}
