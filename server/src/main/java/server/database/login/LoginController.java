package server.database.login;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.mongodb.client.MongoDatabase;
import java.io.FileReader;
import java.util.Collections;

public class LoginController {

    // Construct controller for login.
    public LoginController() {

    }


    public String verifyIdToken(String idTokenString) {
        String CLIENT_SECRET_FILE = "./src/main/java/server/database/server_files/client_secret.json";

        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        String toReturn = "Failed to verify idToken";

        try {
            GoogleClientSecrets clientSecrets =
                    GoogleClientSecrets.load(
                            JacksonFactory.getDefaultInstance(), new FileReader(CLIENT_SECRET_FILE));

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(clientSecrets.getDetails().getClientId()))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                if (payload.getHostedDomain().equals("morris.umn.edu")) {
                    toReturn = "Verified idToken";
                }
            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (Exception e) {
            System.out.println(e);

            toReturn = "null";
        }


        return toReturn;
    }
}
