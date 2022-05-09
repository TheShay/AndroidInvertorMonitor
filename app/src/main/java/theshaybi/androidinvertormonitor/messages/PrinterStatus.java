package theshaybi.androidinvertormonitor.messages;

public class PrinterStatus extends MeterMessage {

	/*
     * |--------------------------------------------------------------------------|
	 * | Contructors |
	 * |--------------------------------------------------------------------------|
	 */

    public PrinterStatus(byte[] byteArray) throws InvalidMeterMessageException {
        super(byteArray);
    }
	/*
	 * |--------------------------------------------------------------------------|
	 * | Public Methods (interface) |
	 * |--------------------------------------------------------------------------|
	 */

    /**
     * @return Returns the type.
     */
    public char getState() {
        return state;
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

        state = (char) byteArray[index];
        index += STATE_SIZE;

    }

	/*
	 * |--------------------------------------------------------------------------|
	 * | Attributes/Fields |
	 * |--------------------------------------------------------------------------|
	 */

    private char state;

    private static final int STATE_SIZE = 1;

}
