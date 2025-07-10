package logger.utils;

import logger.pojo.Log;

import java.io.*;
import java.util.Set;

public class DeepCopyUtil {

    public static <T> T deepCopy(T original) {
        T copiedObject = null;
        try (
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut)
        ) {
            // Serialize object into byte stream
            out.writeObject(original);
            out.close();

            try (
                    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
                    ObjectInputStream in = new ObjectInputStream(byteIn)
            ) {
                // Deserialize object from byte stream
                copiedObject = (T) in.readObject();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle or throw as needed
        }
        T copy = (T) copiedObject;
        return copy;
    }
}