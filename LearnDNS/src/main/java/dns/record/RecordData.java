package dns.record;


import dns.MessageContent;

public abstract class RecordData<T> implements MessageContent<RecordData<T>> {
	private int recordLength;

	void setRecordLength(int recordLength) {
		this.recordLength = recordLength;
	}
	protected int getRecordLength() {
		return recordLength;
	}
}