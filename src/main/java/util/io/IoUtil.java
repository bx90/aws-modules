package util.io;

import java.io.*;

/**
 * @author bsun
 */
public class IoUtil {
    public static void convertInputStreamToFile(InputStream inputStream, String targetFileNameWithPath) {
        if (inputStream == null) {
            System.out.println("IoUtil::convertInputStreamToFile::Provided input stream is null.");
            return;
        }
        try (OutputStream outputStream= new FileOutputStream(new File(targetFileNameWithPath))) {
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
