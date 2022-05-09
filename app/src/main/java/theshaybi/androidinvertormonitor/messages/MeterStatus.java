package theshaybi.androidinvertormonitor.messages;

import android.util.Log;

public class MeterStatus extends MeterMessage {

	/*
     * |--------------------------------------------------------------------------|
	 * | Contructors |
	 * |--------------------------------------------------------------------------|
	 */

    public MeterStatus(byte[] byteArray) throws InvalidMeterMessageException {
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
        return super.getLength() + ONOFF_STATE_SIZE + FAIL_STATE_SIZE + ENDB_STATE_SIZE;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[" + getClass().getName() + "] (");
        sb.append("State: ");
        sb.append(onoff_state);
        sb.append(")");

        return sb.toString();
    }

    /**
     * @return Returns the type.
     */
    public char getOnoff_state() {
        return onoff_state;
    }

    /**
     * @return Returns the type.
     */
    public char getFail_state() {
        return fail_state;
    }

    /**
     * @return Returns the type.
     */
    public char getEnableDisable_state() {
        return endb_state;
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

        onoff_state = (char) byteArray[index];
        index += ONOFF_STATE_SIZE;

        fail_state = (char) byteArray[index];
        index += FAIL_STATE_SIZE;

        endb_state = (char) byteArray[index];
        index += ENDB_STATE_SIZE;

        Log.i(getClass().getSimpleName(), "Parsed new state: " + onoff_state);

    }

	/*
	 * |--------------------------------------------------------------------------|
	 * | Attributes/Fields |
	 * |--------------------------------------------------------------------------|
	 */

    private char onoff_state;
    private char fail_state;
    private char endb_state;

    private static final int ONOFF_STATE_SIZE = 1;
    private static final int FAIL_STATE_SIZE  = 1;
    private static final int ENDB_STATE_SIZE  = 1;

    public static final byte METER_OFF                    = '0';
    public static final char METER_ON                     = '1';
    public static final byte METER_TIME_OFF               = '2';
    public static final byte METER_HIRED_TIME_OFF_PAYMENT = '3';

}
