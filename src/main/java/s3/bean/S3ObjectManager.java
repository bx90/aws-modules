package s3.bean;

import com.amazonaws.util.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;

/**
 * @author bsun
 */
public class S3ObjectManager {
    private String path;
    private Long size;
    private String contentType;

    private S3ObjectManager() {
    }

    public S3ObjectManager(String path) throws FileNotFoundException {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public Long getSize() {
        try (FileInputStream tempStream = new FileInputStream(path)) {
            size = (long) IOUtils.toByteArray(tempStream).length;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    public String getContentType() {
        contentType = parseContentType();
        return contentType;
    }

    private String parseContentType() {
        //  Files.probeContentType(path); this Java native API is BUGGY!!!!
        String fileType;
        fileType = URLConnection.guessContentTypeFromName(path);
        System.out.println(fileType);
        return fileType;
    }
}
