package s3;

import com.amazonaws.regions.Regions;

import java.io.InputStream;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.config.Config;
import util.io.IoUtil;


/**
 * @author bsun
 */
public class S3Test {
    // Below are the arguments need to be put into the paraMap:
    private String accessKey;
    private String secretKey;
    private String region;
    private String bucketName;

    private String s3Path;
    private String sourcePath;
    private String sourceFile;
    private String finalPath;

    @BeforeTest
    public void setup() {
        Config aws = new Config("src/main/resources/properties/amazon.properties");
        Config autoTest = new Config("src/main/resources/properties/s3test.properties");

        accessKey = aws.getDecryption("aws.s3.accesskey");
        secretKey = aws.getDecryption("aws.s3.secretkey");
        region = Regions.US_WEST_2.getName();
        bucketName = aws.get("aws.s3.bucketname");

        s3Path = autoTest.get("s3.location");
        sourcePath = autoTest.get("source.location");
        finalPath = autoTest.get("local.location");

        sourceFile = "ref_id.png";
    }


    @Test
    public void upload() {
        S3 s3 = new S3(accessKey, secretKey, region);
        s3.uploadObject(bucketName, s3Path + sourceFile, sourcePath + sourceFile);
    }

    @Test
    public void download() {
        S3 s3 = new S3(accessKey, secretKey, region);
        InputStream inputStream = s3.downloadObject(bucketName, s3Path + sourceFile);
        IoUtil.convertInputStreamToFile(inputStream, finalPath + "download_" + sourceFile);
    }
}
