package server;

import com.google.api.client.json.JsonFactory;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import server.database.users.UserRequestHandler;
import server.database.users.UserController;
import server.database.abstracts.AbstractController;
import server.database.abstracts.AbstractRequestHandler;
import spark.Request;
import spark.Response;
import spark.Route;

import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.io.IOException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
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

        Route clientRoute = (req, res) -> {
            InputStream stream = userController.getClass().getResourceAsStream("/public/index.html");
            return stream != null ? IOUtils.toString(stream) : "Sorry, we couldn't find that!";
        };

        Route notFoundRoute = (req, res) -> {
            res.type("text");
            res.status(404);
            return "Sorry, we couldn't find that!";
        };

        get("/", clientRoute);

        /// User Endpoints ///////////////////////////


        //We will be taking this out later for security purposes but for the time being it is serving as the only
        //api routes

        get("api/users", userRequestHandler::getUsers);
        get("api/users/:id", userRequestHandler::getUserJSON);

        // Abstracts Endpoints

        get("api/abstracts", abstractRequestHandler::getAbstracts);
        get("api/abstracts/:id", abstractRequestHandler::getAbstractJSON);

        post("api/login", (req, res) -> {

            JSONObject obj = new JSONObject(req.body());
            String idTokenString = obj.getString("idToken");

            String CLIENT_SECRET_FILE = "./src/main/java/server/database/server_files/client_secret.json";

            NetHttpTransport transport = new NetHttpTransport();
            JsonFactory jsonFactory = new JacksonFactory();

            GoogleClientSecrets clientSecrets =
                    GoogleClientSecrets.load(
                            JacksonFactory.getDefaultInstance(), new FileReader(CLIENT_SECRET_FILE));

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(clientSecrets.getDetails().getClientId()))
                    .build();





            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                Payload payload = idToken.getPayload();

                System.out.println(payload.getSubject());

            }

            return "";
        });

        get("api/error", (req, res) -> {
            throw new RuntimeException("A demonstration error");
        });

        // Called after each request to insert the GZIP header into the response.
        // This causes the response to be compressed _if_ the client specified
        // in their request that they can accept compressed responses.
        // There's a similar "before" method that can be used to modify requests
        // before they they're processed by things like `get`.
        after("*", Server::addGzipHeader);

        get("api/*", notFoundRoute);

        get("/*", clientRoute);

        // Handle "404" file not found requests:
        notFound(notFoundRoute);

    }

    // Enable GZIP for all responses
    private static void addGzipHeader(Request request, Response response) {
        response.header("Content-Encoding", "gzip");
    }
}
