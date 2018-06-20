package server;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.io.IOUtils;
import spark.Request;
import spark.Response;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import org.json.*;

import server.database.users.UserRequestHandler;
import server.database.users.UserController;
import server.database.abstracts.AbstractController;
import server.database.abstracts.AbstractRequestHandler;


public class Server {

    private static final String databaseName = "dev";

    private static final int serverPort = 4567;

    public static void main(String[] args) throws IOException {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase(databaseName);

        UserController userController = new UserController(database);
        UserRequestHandler userRequestHandler = new UserRequestHandler(userController);

        AbstractController abstractController = new AbstractController(database);
        AbstractRequestHandler abstractRequestHandler = new AbstractRequestHandler(abstractController);

        //Configure Spark
        port(serverPort);
        enableDebugScreen();

        // Specify where assets like images will be "stored"
        staticFiles.location("/public");

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        // Redirects for the "home" page
        redirect.get("", "/");

        /// User Endpoints //
        //We will be taking this out later for security purposes but for the time being it is serving as the only
        //api routes

        get("api/users", userRequestHandler::getUsers);
        get("api/users/:id", userRequestHandler::getUserJSON);

        post("api/login", (req, res) -> {

            JSONObject obj = new JSONObject(req.body());
            String authCode = obj.getString("code");


            try {
                // This is where we import the Client Secret File

                String CLIENT_SECRET_FILE = "./src/main/java/server/database/server_files/client_secret.json";

                GoogleClientSecrets clientSecrets =
                        GoogleClientSecrets.load(
                                JacksonFactory.getDefaultInstance(), new FileReader(CLIENT_SECRET_FILE));

                GoogleTokenResponse tokenResponse =
                        new GoogleAuthorizationCodeTokenRequest(
                                new NetHttpTransport(),
                                JacksonFactory.getDefaultInstance(),
                                "https://www.googleapis.com/oauth2/v4/token",
                                clientSecrets.getDetails().getClientId(),

                                // Replace clientSecret with the localhost one if testing
                                clientSecrets.getDetails().getClientSecret(),
                                authCode,
                                "http://localhost:9000")

                                // Specify the same redirect URI that you use with your web
                                // app. If you don't have a web version of your app, you can
                                // specify an empty string.
                                .execute();

                GoogleIdToken idToken = tokenResponse.parseIdToken();
                GoogleIdToken.Payload payload = idToken.getPayload();
                String subjectId = payload.getSubject();  // Use this value as a key to identify a user.
                String email = payload.getEmail();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                return userController.addNewUser(subjectId, givenName, familyName);

            } catch (Exception e) {
                System.out.println(e);
            }

            return "";
        });

        // Abstracts Endpoints

        get("api/abstracts", abstractRequestHandler::getAbstracts);
        get("api/abstracts/:id", abstractRequestHandler::getAbstractJSON);

        get("api/error", (req, res) -> {
            throw new RuntimeException("A demonstration error");
        });

        // Called after each request to insert the GZIP header into the response.
        // This causes the response to be compressed _if_ the client specified
        // in their request that they can accept compressed responses.
        // There's a similar "before" method that can be used to modify requests
        // before they they're processed by things like `get`.
        after("*", Server::addGzipHeader);

        // Handle "404" file not found requests:
        notFound((req, res) -> {
            res.type("text");
            res.status(404);
            return "Sorry, we couldn't find that!";
        });
    }

    // Enable GZIP for all responses
    private static void addGzipHeader(Request request, Response response) {
        response.header("Content-Encoding", "gzip");
    }
}
