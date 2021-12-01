package dns.record;



import dns.Domain;

import java.io.IOException;
import java.nio.ByteBuffer;



public class Rt extends RecordData<Rt> {

	private int preference;
	private final Domain intermediateHost;

	public Rt() {
		this.intermediateHost = new Domain();
	}

	public Rt(int preference, Domain intermediateHost) {
		if (preference > 65535 || preference < 0) {
			throw new IllegalArgumentException("Preference must be between 0 and 65535.");
		}
		this.preference = preference;
		this.intermediateHost = intermediateHost;
	}

	public int getPreference() {
		return preference;
	}

	public Domain getIntermediateHost() {
		return intermediateHost;
	}

	@Override
	public RecordData<Rt> toBytes(ByteBuffer buf) throws IOException {
		buf.putShort((short) preference);
		intermediateHost.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<Rt> fromBytes(ByteBuffer buf) throws IOException {
		preference = buf.getShort() & 0xFFFF;
		intermediateHost.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "Rt [preference=" + preference + ", intermediateHost="
				+ intermediateHost + "]";
	}

}
