package s3.bean;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author bsun
 */
public class S3FileManager extends S3ObjectManager implements Closeable {
    private FileInputStream fileInputStream;

    public S3FileManager(String path) throws FileNotFoundException {
        super(path);
        this.fileInputStream = new FileInputStream(path);
    }

    public FileInputStream getFileInputStream() throws FileNotFoundException {
        return fileInputStream;
    }

    @Override
    public void close() throws IOException {
        fileInputStream.close();
    }
}
