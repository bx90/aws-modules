package s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import s3.bean.S3FileManager;
import s3.bean.S3ObjectManager;

import java.io.IOException;

/**
 * @author bsun
 */
public class S3 {
    private String accessKey;
    private String secretKey;
    private String region;


    public S3(String accessKey, String secretKey, String region) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
    }

    public void uploadObject(String bucketName, String keyName, String path) {
        AmazonS3 s3Client = createS3Client();

        try (S3FileManager file = new S3FileManager(path)) {
            ObjectMetadata metadata = createObjectMetadata(file);
            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,
                    keyName, file.getFileInputStream(),
                    metadata);

            System.out.println("Uploading a new object to S3 from a file.");
            s3Client.putObject(objectRequest);
            System.out.println("Completed upload.");
        } catch (IOException | AmazonClientException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public S3ObjectInputStream downloadObject(String bucketName, String keyName) {
        AmazonS3 s3Client = createS3Client();
        try {
            System.out.println("Downloading an object");
            S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName, keyName));
            return s3object.getObjectContent();
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which" +
                    " means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means" +
                    " the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        return null;
    }

    private AmazonS3 createS3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

        return AmazonS3ClientBuilder.standard()
                                    .withCredentials(credentialsProvider)
                                    .withRegion(region)
                                    .build();
    }

    private ObjectMetadata createObjectMetadata(S3ObjectManager object) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(object.getSize());
        metadata.setContentType(object.getContentType());
        return metadata;
    }
}
