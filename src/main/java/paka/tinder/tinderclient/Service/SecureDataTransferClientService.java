package paka.tinder.tinderclient.Service;

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
    public Integer publicKeyID;
    public PublicKey publicKeyClient;
    public PublicKey publicKeyServer;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL = "http://localhost:8080/";

    private final String PATH_TO_CONFIG_FILE = "./config/key.dat";

    BillCipher billCipherClient;

    public SecureDataTransferClientService() throws IOException {
        billCipherClient = new BillCipher();
        publicKeyID = readIDFromFile();
    }

    public void sendPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        publicKeyClient = billCipherClient.generateKeys();
        //Getting index of publicKey in server
        publicKeyID = Integer.valueOf(restTemplate.postForObject(
                URL + "getPublicKey", convertKeyToString(publicKeyClient), String.class));
        writeIDToFile();
    }

    private void writeIDToFile() throws IOException {
        //Maybe try/catch, not sure
        BufferedWriter configFileWriter = new BufferedWriter(new FileWriter(PATH_TO_CONFIG_FILE));
        configFileWriter.write("PublicKeyID: " + publicKeyID);
        configFileWriter.flush();
    }
    private Integer readIDFromFile() throws IOException {
        //Forman "json-like": variable: value
        BufferedReader configFileReader = new BufferedReader(new FileReader(PATH_TO_CONFIG_FILE));
        return Integer.parseInt(configFileReader.readLine().split(": ")[1]);
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
