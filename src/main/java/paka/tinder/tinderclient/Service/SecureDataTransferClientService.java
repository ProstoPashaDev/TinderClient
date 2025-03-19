package paka.tinder.tinderclient.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import paka.tinder.tinderclient.Secure.BillCipher;

import java.io.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

public class SecureDataTransferClientService {

    //ID of public key in server list
    private Integer publicKeyID;
    private PublicKey publicKeyClient;
    private PublicKey publicKeyServer;
    private String signature;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL = "http://localhost:8080/";

    private BillCipher billCipherClient;

    public void sendPublicKey() {
        billCipherClient = new BillCipher();
        publicKeyClient = billCipherClient.generateKeys();
        //Getting index of publicKey in server
        publicKeyID = Integer.valueOf(restTemplate.postForObject(
                URL + "sendPublicKey", convertKeyToString(publicKeyClient), String.class));
        System.out.println(publicKeyID);
    }

    public void getPublicKey() {
        String publicKeyServerStrEncrypted = restTemplate.postForObject(URL + "getPublicKey", String.valueOf(publicKeyID), String.class);
        publicKeyServer = billCipherClient.Decrypt(publicKeyServerStrEncrypted, PublicKey.class);
        //billCipherClient.setPublicKey(publicKeyServer);
        System.out.println(publicKeyServer);
    }

    public void getSignature() {
        String encryptedSignature = restTemplate.postForObject(URL + "getSignature", String.valueOf(publicKeyID), String.class);
        signature = billCipherClient.Decrypt(encryptedSignature, String.class);
        System.out.println(signature);
    }

    public boolean testConnection() {
        System.out.println(URL + "testConnection/" + publicKeyID);
        System.out.println(billCipherClient.Encrypt("123"));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL + "testConnection/" + publicKeyID, billCipherClient.Encrypt(signature), String.class);
        System.out.println(responseEntity.getStatusCode());
        return true;
    }

    public void closeSession() {
        System.out.println(publicKeyID);
        restTemplate.delete(URL + "closeSession/" + publicKeyID);
    }

    public String convertKeyToString(PublicKey publicKey) {
        byte[] bytePublicKey = publicKey.getEncoded();
        //Converting byte[] to String
        return Base64.getEncoder().encodeToString(bytePublicKey);
    }
    public PublicKey convertStringToKey(String strKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytePublicKey  = Base64.getDecoder().decode(strKey);
        //Converting byte[] to PublicKey
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(new X509EncodedKeySpec(bytePublicKey));
    }
}
