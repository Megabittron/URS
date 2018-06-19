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

    /**
     * Abstracts request handler to fetch abstracts in a Json Format
     *
     */

    public String getAbstracts(Request req, Response res)
    {
        res.type("application/json");
        return AbstractController.getAbstracts(req.queryMap().toMap());
    }

    public String addNewAbstract(Request req, Response res)
    {

        res.type("application/json");
        Object o = JSON.parse(req.body());
        try {
            if(o.getClass().equals(BasicDBObject.class))
            {
                try {
                    BasicDBObject dbO = (BasicDBObject) o;

                    String userID = dbO.getString("userID");
                    String id = dbO.getString("id");
                    String title= dbO.getString("title");
                    String format= dbO.getString("format");
                    String abstracts= dbO.getString("abstracts");
                    String presentationType= dbO.getString("presentationType");
                    String formatChange= dbO.getString("formatChange");
                    String discipline= dbO.getString("discipline");
                    String featured= dbO.getString("featured");
                    String mediaServicesEquipment= dbO.getString("mediaServicesEquipment");
                    String specialRequirements= dbO.getString("specialRequirments");
                    String otherInfo= dbO.getString("otherInfo");
                    String approval= dbO.getString("approval");
                    String cc= dbO.getString("cc");
                    String rejection= dbO.getString("rejection");
                    String group= dbO.getString("group");
                    String roomAssignment= dbO.getString("roomAssignment");
                    String totalRewriteVotes= dbO.getString("totalRewriteVotes");
                    String majorRewriteVotes= dbO.getString("majorRewriteVotes");
                    String minorRewriteVotes= dbO.getString("minorRewriteVotes");
                    String acceptedVotes,
                    String comments,
                    String isPrimarySubmission,
                    String resubmitFlag,
                    String firstPresenterFirstName,
                    String firstPresenterLastName,
                    String firstPresenterEmail,
                    String secondPresenterFirstName,
                    String secondPresenterLastName,
                    String secondPresenterEmail,
                    String thirdPresenterFirstName,
                    String thirdPresenterLastName,
                    String thirdPresenterEmail,
                    String firstAdviserFirstName,
                    String firstAdviserLastName,
                    String firstAdviserEmail,
                    String secondAdviserFirstName,
                    String secondAdviserLastName,
                    String secondAdviserEmail

                    System.err.println("Adding new journal for user " + userID + " [title=" + title + ", content=" + content + ']');


}
