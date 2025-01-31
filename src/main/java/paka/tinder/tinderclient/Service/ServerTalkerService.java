package paka.tinder.tinderclient.Service;

import org.springframework.web.client.RestTemplate;
import paka.tinder.tinderclient.Secure.BillCipher;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

public class ServerTalkerService {

    //ID of public key in server list
    public Integer publicKeyID;
    public PublicKey publicKeyClient;
    public PublicKey publicKeyServer;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL = "http://localhost:8080/";

    private final String PATH_TO_CONFIG_FILE = "key.dat";

    BillCipher billCipherClient;

    public ServerTalkerService() {
        billCipherClient = new BillCipher();
        //TODO read from file
        //publicKeyID =
    }

    public void sendPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        publicKeyClient = billCipherClient.generateKeys();
        System.out.println(publicKeyClient);
        //Default id: -1
        Map<Integer, byte[]> serverPublicKeyIndInBytes = restTemplate.postForObject(
                URL + "getPublicKey", Map.of(publicKeyID, publicKeyClient.getEncoded()),Map.class);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        for (Map.Entry<Integer, byte[]> entry : serverPublicKeyIndInBytes.entrySet()) {
            publicKeyID = entry.getKey();
            byte[] bytePubKeyServer = entry.getValue();
            publicKeyServer = factory.generatePublic(new X509EncodedKeySpec(bytePubKeyServer));
        }
    }

    private void writeIdToFile() throws FileNotFoundException {
        //FileOutputStream configFile = new FileOutputStream();
    }
}
