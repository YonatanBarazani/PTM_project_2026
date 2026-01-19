package test;

import java.util.Date;

public class Message {
    public final byte[] data;
    public final String asText;
    public final double asDouble;
    public final Date date;

    public Message(String msg) {
        if (msg == null) {
            this.asText = "";
        }
        else {
            this.asText = msg;
        }

       this.data = this.asText.getBytes();

        double num;
        try {
            num = Double.parseDouble(this.asText);
        }
        catch (Exception e) {
            num = Double.NaN;
        }
        this.asDouble = num;

        this.date = new Date();
    }

    public Message(byte[] data) {
        this(new String(data == null ? new byte[0] : data));
    }

    public Message(double num) {
        this(Double.toString(num));
    }
}
