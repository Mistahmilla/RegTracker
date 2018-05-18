package org.cycop.reg;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;

public class SecretRetriever {

    private String endPoint;
    private String region;

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
            System.out.println("The requested secret " + secretName + " was not found");
        } catch (InvalidRequestException e) {
            System.out.println("The request was invalid due to: " + e.getMessage());
        } catch (InvalidParameterException e) {
            System.out.println("The request had invalid params: " + e.getMessage());
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
