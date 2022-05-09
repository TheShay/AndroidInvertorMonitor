package theshaybi.androidinvertormonitor.messages;

public class MeterStatsData extends MeterMessage {
	/*
	 * |--------------------------------------------------------------------------|
	 * | Contructors |
	 * |--------------------------------------------------------------------------|
	 */

	public MeterStatsData() {
		super();
		messageId = MessageId.REPORT_METER_STATS_DATA;
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

	public MeterStatsData(byte[] byteArray) throws InvalidMeterMessageException {
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

		return super.getLength() + UNIT_SIZE + TOTAL_FARE_SIZE + TOTAL_EXTRAS_SIZE + TOTAL_TAX_SIZE + TOTAL_DISTANCE_SIZE + TOTAL_PAID_DISTANCE_SIZE + TOTAL_TRIPS_SIZE;
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
		sb.append("[" + getClass().getName() + "] (");
		sb.append(" Unit: ");
		sb.append(unitString);
		sb.append(", TotalFare: ");
		sb.append(totalFareString);
		sb.append(", TotalExtras : ");
		sb.append(totalExtrasString);
		sb.append(", TotalTax: ");
		sb.append(totalTaxString);
		sb.append(", TotalDistance: ");
		sb.append(totalDistanceString);
		sb.append(", TotalPaidDistance: ");
		sb.append(totalPaidDistanceString);
		sb.append(", TotalTrips: ");
		sb.append(totalTripsString);
		sb.append(" )");

		return sb.toString();
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

		unitString = new String(ByteArray.extractByteArray(byteArray, index, MeterStatsData.UNIT_SIZE));
		unitString = unitString.trim();
		index += MeterStatsData.UNIT_SIZE;

		totalFareString = new String(ByteArray.extractByteArray(byteArray, index, MeterStatsData.TOTAL_FARE_SIZE));
		totalFareString = totalFareString.trim();
		totalFareString = Double.toString(Double.valueOf(totalFareString) / 100);
		index += MeterStatsData.TOTAL_FARE_SIZE;

		totalExtrasString = new String(ByteArray.extractByteArray(byteArray, index, MeterStatsData.TOTAL_EXTRAS_SIZE));
		totalExtrasString = totalExtrasString.trim();
		totalExtrasString = Double.toString(Double.valueOf(totalExtrasString) / 100);
		index += MeterStatsData.TOTAL_EXTRAS_SIZE;

		totalTaxString = new String(ByteArray.extractByteArray(byteArray, index, MeterStatsData.TOTAL_TAX_SIZE));
		totalTaxString = totalTaxString.trim();
		totalTaxString = Double.toString(Double.valueOf(totalTaxString) / 100);
		index += MeterStatsData.TOTAL_TAX_SIZE;

		totalDistanceString = new String(ByteArray.extractByteArray(byteArray, index, MeterStatsData.TOTAL_DISTANCE_SIZE));
		totalDistanceString = totalDistanceString.trim();
		index += MeterStatsData.TOTAL_DISTANCE_SIZE;

		totalPaidDistanceString = new String(ByteArray.extractByteArray(byteArray, index, MeterStatsData.TOTAL_PAID_DISTANCE_SIZE));
		totalPaidDistanceString = totalPaidDistanceString.trim();
		index += MeterStatsData.TOTAL_PAID_DISTANCE_SIZE;

		totalTripsString = new String(ByteArray.extractByteArray(byteArray, index, MeterStatsData.TOTAL_TRIPS_SIZE));
		totalTripsString = totalTripsString.trim();
		index += MeterStatsData.TOTAL_TRIPS_SIZE;



	}

	public String getTotalTripsString() {
		return totalTripsString;
	}

	public String getTotalPaidDistanceString() {
		return totalPaidDistanceString;
	}

	public String getTotalDistanceString() {
		return totalDistanceString;
	}

	public String getTotalTaxString() {
		return totalTaxString;
	}

	public String getTotalExtrasString() {
		return totalExtrasString;
	}

	public String getTotalFareString() {
		return totalFareString;
	}

	public String getUnitString() {
		return unitString;
	}
/*
	 * |--------------------------------------------------------------------------|
	 * | Attributes/Fields |
	 * |--------------------------------------------------------------------------|
	 */

	private String unitString;
	private String totalFareString;
	private String totalExtrasString;
	private String totalTaxString;
	private String totalDistanceString;
	private String totalPaidDistanceString;
	private String totalTripsString;

	private static final int UNIT_SIZE = 1;
	private static final int TOTAL_FARE_SIZE = 10;
	private static final int TOTAL_EXTRAS_SIZE = 10;
	private static final int TOTAL_TAX_SIZE = 10;
	private static final int TOTAL_DISTANCE_SIZE = 10;
	private static final int TOTAL_PAID_DISTANCE_SIZE = 10;
	private static final int TOTAL_TRIPS_SIZE = 10;


}
