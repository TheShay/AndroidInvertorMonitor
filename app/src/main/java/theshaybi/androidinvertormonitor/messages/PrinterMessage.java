package theshaybi.androidinvertormonitor.messages;

public class PrinterMessage extends MeterMessage {

	/*
     * |--------------------------------------------------------------------------|
	 * | Contructors |
	 * |--------------------------------------------------------------------------|
	 */

    private PrinterMessage() {
        super();
    }

    public PrinterMessage(String textToPrintIn, String meterType, boolean isVerifone) throws IllegalArgumentException {
        this();
        if (meterType.equalsIgnoreCase("centrodyne") || isVerifone) {
            if (textToPrintIn.length() > MAX_PRINT_BLOCK_CENTRODYNE)
                throw new IllegalArgumentException("Text to print is too long: " + textToPrintIn.length() + ", max is " + MAX_PRINT_BLOCK_CENTRODYNE);
            messageId = MessageId.PRINT_LARGE_BLOCK;
            textToPrint = '2' + textToPrintIn;
        } else {
            if (textToPrintIn.length() > MAX_PRINT_BLOCK)
                throw new IllegalArgumentException("Text to print is too long: " + textToPrintIn.length() + ", max is " + MAX_PRINT_BLOCK);
            //messageId = isVerifone ? messageId = MessageId.PRINT_BLOCK : MessageId.LOAD_DATA_TO_PRINTER_BUFFER;
            messageId = MessageId.LOAD_DATA_TO_PRINTER_BUFFER;
            textToPrint = textToPrintIn;
        }
    }

	/*
     * |--------------------------------------------------------------------------|
	 * | Public Methods (interface) |
	 * |--------------------------------------------------------------------------|
	 */

    /**
     * Return the overall MM length.
     */
    @Override
    public int getLength() {
        return super.getLength() + textToPrint.getBytes().length;
    }

    /**
     * Convert the fields into a byte array.
     */
    @Override
    public byte[] toByteArray() {

        byte[] b = super.toByteArray();

        int index = getMessageBodyStart();

        byte[] textBytes = textToPrint.getBytes();
        System.arraycopy(textBytes, 0, b, index, textToPrint.length());
        index += textBytes.length;

        int bcc = verifyBlockCharacterChecksum(b);
        ByteArray.insertInt(b, index, MeterMessage.BLOCK_CHECKSUM_CHARACTER_LENGTH, bcc);

        return b;

    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[" + getClass().getName() + "] (");
        sb.append("Text: ");
        sb.append(textToPrint);
        sb.append(")");

        return sb.toString();
    }

    /**
     * @return Returns the text to print.
     */

    public String getTextToPrint() {
        return textToPrint;
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

        textToPrint = new String(ByteArray.extractByteArray(byteArray, index, getDataBlockLength()));
        textToPrint = textToPrint.trim();
    }

	/*
	 * |--------------------------------------------------------------------------|
	 * | Attributes/Fields |
	 * |--------------------------------------------------------------------------|
	 */

    private String textToPrint;

    public static final int MAX_PRINT_BLOCK = 127;
    public static final int MAX_PRINT_BLOCK_CENTRODYNE = 255;

}
