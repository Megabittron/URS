package server.database.abstracts;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import spark.Request;
import spark.Response;

public class AbstractRequestHandler {

    private final AbstractController abstractController;

    /**
     * Abstracts request handler constructor
     *
     * @param abstractController
     */

    public AbstractRequestHandler(AbstractController abstractController){
        this.abstractController = abstractController;
    }

    /**
    * Abstracts request handler to fetch abstracts in a Json Format
    *
    */

 public String getAbstractJSON(Request req, Response res){
        res.type("application/json");
        String id = req.params("id");

        // String variable was chosen as "abstracts" instead singular "abstract" to prevent java errors

        String abstracts;
        try {
            abstracts = abstractController.getAbstract(id);
        } catch (IllegalArgumentException e) {

            // This is thrown if the ID doesn't have the appropriate
            // form for a Mongo Object ID.
            // https://docs.mongodb.com/manual/reference/method/ObjectId/

            res.status(400);
            res.body("The requested abstract id " + id + " wasn't a legal Mongo Object ID.\n" +
                    "See 'https://docs.mongodb.com/manual/reference/method/ObjectId/' for more info.");
            return "";
        }

     // String variable was chosen as "abstracts" instead singular "abstract" to prevent java errors

     if (abstracts != null) {
            return abstracts;
        } else {
            res.status(404);
            res.body("The requested abstract with id " + id + " was not found");
            return "";
        }
    }




}
