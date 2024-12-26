package paka.tinder.tinderclient.Service;

import org.springframework.web.client.RestTemplate;
import paka.tinder.tinderclient.TableClassExample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ServiceExample {

    //
    //Begin fun example
    private static final Random random = new Random();

    private static final List<String> names =
            new ArrayList<>(Arrays.asList("Anna", "Anya", "Ann",
                    "Kirill", "Kiruha", "Kirillka",
                    "Aleksei", "Lexa", "Aloha"));

    private static final List<String> greetings =
            new ArrayList<>(Arrays.asList("Hi", "Hello", "Hola",
                    "Nihao", "Bonjur", "Здарова"));

    public static String lolExample() {
        return greetings.get(random.nextInt(greetings.size())) +
                " " + names.get(random.nextInt(names.size()));
    }
    //End fun example
    //


    //Instance of RestTemplate (need for sending/getting requests with server)
    private static final RestTemplate restTemplate = new RestTemplate();

    private static final String URL = "http://localhost:8080/";
    /**
     * Getting all instances from server
     */
    public static void GetAll() {
        //Send get method request of address URL,
        // then convert input to instances of class TableClassExample
        TableClassExample[] examples =
                restTemplate.getForObject(URL, TableClassExample[].class);

        //Printing
        for (int i = 0; i < examples.length; i++) {
            System.out.println(examples[i]);
        }
    }

    /**
     * Insert object using method post
     * @param example send this to server
     * (we need to sent instance of class to server only if
     * on server there is @RequestBody annotation)
     */
    public static void createExample(TableClassExample example) {
        restTemplate.postForEntity(URL, example, TableClassExample.class);
    }

    /**
     * Update example
     * @param example send this to server
     */
    public static void updateExample(TableClassExample example) {
        restTemplate.put(URL + "/" + example.getId(), example);
    }

    /**
     * Delete example
     * @param id sending only id
     */
    public static void deleteExample(int id) {
        restTemplate.delete(URL + "/" + id);
    }

}
