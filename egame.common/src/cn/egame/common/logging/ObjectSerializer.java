package cn.egame.common.logging;

import java.io.*;

/**
 * Created by WangHuan on 2015/01/19.
 */
public class ObjectSerializer {
    private static ObjectSerializer objectSerializer = new ObjectSerializer();

    public static ObjectSerializer getInstance() {
        return objectSerializer;
    }

    public byte[] serialize(Object source) throws IOException {
        ByteArrayOutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            outputStream = new ByteArrayOutputStream(128);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(source);
            objectOutputStream.flush();
        } finally {
            if (outputStream != null) outputStream.close();
            if (objectOutputStream != null) objectOutputStream.close();
        }
        return outputStream.toByteArray();
    }

    public Object deSerialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(byteArrayInputStream);
            return ois.readObject();
        } finally {
            if (ois != null)
                ois.close();
            if (byteArrayInputStream != null)
                byteArrayInputStream.close();
        }
    }
}
