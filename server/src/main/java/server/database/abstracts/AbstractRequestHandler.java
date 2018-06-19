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




}
