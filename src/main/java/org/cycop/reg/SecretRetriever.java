package org.cycop.reg;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecretRetriever {

    private String endPoint;
    private String region;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SecretRetriever(String endPoint, String region){
        this.endPoint = endPoint;
        this.region = region;
    }

    public String getSecret(String secretName){
        AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder.EndpointConfiguration(endPoint, region);
        AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder.standard();
        clientBuilder.setEndpointConfiguration(config);
        AWSSecretsManager client = clientBuilder.build();

        String secret;

        GetSecretValueRequest req = new GetSecretValueRequest();
        req.setSecretId(secretName);
        GetSecretValueResult rsp = null;
        try {
            rsp = client.getSecretValue(req);

        } catch(ResourceNotFoundException e) {
            logger.error("The requested secret " + secretName + " was not found",e);
        } catch (InvalidRequestException e) {
            logger.error("The request was invalid due to: " + e.getMessage(),e);
        } catch (InvalidParameterException e) {
            logger.error("The request had invalid params: " + e.getMessage(),e);
        }

        if(rsp == null) {
            return "";
        }

        // Decrypted secret using the associated KMS CMK
        // Depending on whether the secret was a string or binary, one of these fields will be populated
        if(rsp.getSecretString() != null) {
            secret = rsp.getSecretString();
            return secret;
        }

        return "";
    }






}
