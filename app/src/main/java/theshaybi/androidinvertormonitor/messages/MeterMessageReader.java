package theshaybi.androidinvertormonitor.messages;
import theshaybi.androidinvertormonitor.BannerBluetooth;

import android.os.Environment;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Trivial class for reading a message's worth of bytes from the input stream before
 * returning. Reads the first three bytes (opcode and length). It then reads in
 * length bytes.
 *
 * @author Muhammad Zahid
 */
public class MeterMessageReader {
    /*
     * |--------------------------------------------------------------------------|
     * | Contructors |
     * |--------------------------------------------------------------------------|
     */

    public MeterMessageReader(InputStream is) {
        super();

        myInputStream = is;
        ByteBuffer.allocate(MeterMessage.BUFFER_SIZE);

    }

    /*
     * |--------------------------------------------------------------------------|
     * | Public Methods (interface) |
     * |--------------------------------------------------------------------------|
     */

    public byte[] getMessage() throws IOException, IndexOutOfBoundsException {
        int offset = 0;
        if (myInputStream.read(pendingBuffer, offset, 1) > 0) {
            offset++;
            if (pendingBuffer[offset - 1] == MessageId.START_OF_BACKSEAT_MSG) {
                do {
                    myInputStream.read(pendingBuffer, offset, 1);
                    offset++;
                }
                while (BannerBluetooth.isreceive && pendingBuffer[offset - 1] != MessageId.END_OF_BACKSEAT_MSG);

            }

            byte[] newBuffer = new byte[offset];
            System.arraycopy(pendingBuffer, 0, newBuffer, 0, offset);
            return newBuffer;
        }
        return null;
    }

    public static void WriteinLogFile(String pMessage) {
        SetDirectoryPath();
        String ErrorLogFile = "TaxiPlexer_appLogs.txt";

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + DocumentDirectoryPath, ErrorLogFile);

        StringBuilder text = new StringBuilder();

        if (file.exists()) {
            // Read text from file
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {
                // You'll need to add proper error handling here
            }
        }
        text.append('\n');
        text.append(pMessage);

        FileWriter writer;
        try {
            writer = new FileWriter(file);
            writer.append(text.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void SetDirectoryPath() {
        DocumentDirectoryPath = "/TaxiPLexerdocs/";// getResources().getString(R.string.documentDirectoryPath);
        File FileDocDirectoryPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + DocumentDirectoryPath);

        if (!(FileDocDirectoryPath.exists() && FileDocDirectoryPath.isDirectory()))
            FileDocDirectoryPath.mkdir();
    }

    /*
     * |--------------------------------------------------------------------------|
     * | Attributes/Fields |
     * |--------------------------------------------------------------------------|
     */
    static String DocumentDirectoryPath = "";
    /**
     * Initial byte of a packet.
     */
    public static final byte   STX                   = 0x02;
    /**
     * Final byte of a packet.
     */
    public static final byte   ETX                   = 0x03;

    private final byte[] pendingBuffer = new byte[MeterMessage.BUFFER_SIZE];

    private final InputStream myInputStream;

    // private final MeterProtocolDecoder decoder;
}
