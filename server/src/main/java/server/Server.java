package server;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import server.database.users.UserRequestHandler;
import server.database.users.UserController;
import server.database.abstracts.AbstractController;
import server.database.abstracts.AbstractRequestHandler;
import spark.Request;
import spark.Response;

import java.io.IOException;


import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Server {
    private static final String userDatabaseName = "URS";
    private static final int serverPort = 4567;

    public static void main(String[] args) throws IOException {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase userDatabase = mongoClient.getDatabase(userDatabaseName);

        // Did not initialize abstract Database yet so commented out AbstractDatabase function

        // MongoDatabase abstractDatabase = mongoClient.getDatabase(abstractDatabase);

        UserController userController = new UserController(userDatabase);
        UserRequestHandler userRequestHandler = new UserRequestHandler(userController);

        //AbstractController abstractController = new AbstractController(abstractDatabase);
        //AbstractRequestHandler abstractRequestHandler = new AbstractRequestHandler(abstractController);

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

        redirect.get("/", "http://localhost:9000");

        /// User Endpoints ///////////////////////////


        //We will be taking this out later for security purposes but for the time being it is serving as the only
        //api routes

        get("api/users", userRequestHandler::getUsers);
        get("api/users/:id", userRequestHandler::getUserJSON);

        // Abstracts Endpoints

        //get("api/abstracts", abstractRequestHandler::getAbstracts);
        //get("api/abstracts/:id", abstractRequestHandler::getAbstractJSON);


        // An example of throwing an unhandled exception so you can see how the
        // Java Spark debugger displays errors like this.
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
