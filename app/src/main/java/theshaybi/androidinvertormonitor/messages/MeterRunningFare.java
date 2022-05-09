package theshaybi.androidinvertormonitor.messages;

public class MeterRunningFare extends MeterMessage {
    /*
	 * |--------------------------------------------------------------------------|
	 * | Contructors |
	 * |--------------------------------------------------------------------------|
	 */

    public MeterRunningFare() {
        super();
        messageId = MessageId.REPORT_CURRENT_RUNNING_FARE;
    }

	/*
	 * public CreditCardDataMessage(String accountNumberIn, String expiryDateIn, String additionalDataIn, String fareIn)
	 * {
	 * this();
	 * accountNumberString = accountNumberIn;
	 * expiryDateString = expiryDateIn;
	 * additionalDataString = additionalDataIn;
	 * fareString = fareIn;
	 * }
	 */

    public MeterRunningFare(byte[] byteArray) throws InvalidMeterMessageException {
        super(byteArray);
    }

	/*
	 * |--------------------------------------------------------------------------|
	 * | Public Methods (interface) |
	 * |--------------------------------------------------------------------------|
	 */

    /**
     * Return the overall SMM length.
     */
    @Override
    public int getLength() {

        return super.getLength() + FARE_SIZE + EXTRAS_SIZE;
    }

    /**
     * Convert the fields into a byte array.
     */
    @Override
    public byte[] toByteArray() {
        return null;
		/*
         * byte[] b = super.toByteArray();
		 * 
		 * int index = getMessageBodyStart();
		 * 
		 * b[index] = (byte) state;
		 * index += STATE_SIZE;
		 * 
		 * int bcc = super.verifyBlockCharacterChecksum(b);
		 * ByteArray.insertInt(b, index, MeterMessage.BLOCK_CHECKSUM_CHARACTER_LENGTH, bcc);
		 * 
		 * return b;
		 */
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[" + getClass().getSimpleName() + "] (");
        sb.append(" Fare: ");
        sb.append(fareString);
        sb.append(", Extras : ");
        sb.append(extrasString);
        sb.append(" )");

        return sb.toString();
    }

    /**
     * @return Returns the extras value.
     */
    public String getExtras() {

        return extrasString;
    }

    /**
     * @return Returns the fare in ASCII.
     */
    public String getFare() {
        return fareString;
    }


	/*
	 * |--------------------------------------------------------------------------|
	 * | Protected Methods |
	 * |--------------------------------------------------------------------------|
	 */

    @Override
    protected void parseMessage(byte[] byteArray) throws InvalidMeterMessageException {
        super.parseMessage(byteArray);

        int index = getMessageBodyStart();

        fareString = new String(ByteArray.extractByteArray(byteArray, index, MeterRunningFare.FARE_SIZE));
        fareString = fareString.trim();
        fareString = Double.toString(Double.valueOf(fareString) / 100);
        index += MeterRunningFare.FARE_SIZE;

        extrasString = new String(ByteArray.extractByteArray(byteArray, index, MeterRunningFare.EXTRAS_SIZE));
        extrasString = extrasString.trim();
        extrasString = Double.toString(Double.valueOf(extrasString) / 100);
        index += MeterRunningFare.EXTRAS_SIZE;
    }

	/*
	 * |--------------------------------------------------------------------------|
	 * | Attributes/Fields |
	 * |--------------------------------------------------------------------------|
	 */

    private String fareString;
    private String extrasString;

    private static final int EXTRAS_SIZE = 8;
    private static final int FARE_SIZE = 8;
}
