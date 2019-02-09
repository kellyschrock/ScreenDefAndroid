package com.example.screendef.fognl.android.screendef.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Streams {
    public interface Listener {
        void onBytesRead(int bytes);
    }

    public static <T extends OutputStream> T copy(InputStream in, T out) throws IOException {
        byte[] buf = new byte[8192];

        for(int read = in.read(buf); read != -1; read = in.read(buf)) {
            out.write(buf, 0, read);
        }

        return out;
    }

    public static <T extends OutputStream> T copy(InputStream in, T out, Listener listener) throws IOException {
        byte[] buf = new byte[8192];

        int totalBytes = 0;
        for(int read = in.read(buf); read != -1; read = in.read(buf)) {

            if(listener != null) {
                totalBytes += read;
                listener.onBytesRead(totalBytes);
            }

            out.write(buf, 0, read);
        }

        return out;
    }

    public static <T extends OutputStream> T copyAndClose(InputStream in, T out) throws IOException {
        try {
            return copy(in, out);
        } finally {
            in.close();
            out.flush();
            out.close();
        }
    }
}
