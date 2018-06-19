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
            if(o.getClass().equals(BasicDBObject.class)) {
                try {
                    BasicDBObject dbO = (BasicDBObject) o;

                    String userID = dbO.getString("userID");
                    String id = dbO.getString("id");
                    String title = dbO.getString("title");
                    String format = dbO.getString("format");
                    String abstracts = dbO.getString("abstracts");
                    String presentationType = dbO.getString("presentationType");
                    String formatChange = dbO.getString("formatChange");
                    String discipline = dbO.getString("discipline");
                    String featured = dbO.getString("featured");
                    String mediaServicesEquipment = dbO.getString("mediaServicesEquipment");
                    String specialRequirements = dbO.getString("specialRequirments");
                    String otherInfo = dbO.getString("otherInfo");
                    String approval = dbO.getString("approval");
                    String cc = dbO.getString("cc");
                    String rejection = dbO.getString("rejection");
                    String group = dbO.getString("group");
                    String roomAssignment = dbO.getString("roomAssignment");
                    String totalRewriteVotes = dbO.getString("totalRewriteVotes");
                    String majorRewriteVotes = dbO.getString("majorRewriteVotes");
                    String minorRewriteVotes = dbO.getString("minorRewriteVotes");
                    String acceptedVotes = dbO.getString("acceptedVotes");
                    String comments = dbO.getString("comments");
                    String isPrimarySubmission = dbO.getString("isPrimarySubmission");
                    String resubmitFlag = dbO.getString("resubmitFlag");
                    String firstPresenterFirstName = dbO.getString("firstPresenterFirstName");
                    String firstPresenterLastName = dbO.getString("firstPresenterLastName");
                    String firstPresenterEmail = dbO.getString("firstPresenterEmail");
                    String secondPresenterFirstName = dbO.getString("secondPresenterFirstName");
                    String secondPresenterLastName = dbO.getString("secondPresenterLastName");
                    String secondPresenterEmail = dbO.getString("secondPresenterEmail");
                    String thirdPresenterFirstName = dbO.getString("thirdPresenterFirstName");
                    String thirdPresenterLastName = dbO.getString("thirdPresenterLastName");
                    String thirdPresenterEmail = dbO.getString("thirdPresenterEmail");
                    String firstAdviserFirstName = dbO.getString("firstAdviserFirstName");
                    String firstAdviserLastName = dbO.getString("firstAdviserLastName");
                    String firstAdviserEmail = dbO.getString("firstAdviserEmail");
                    String secondAdviserFirstName = dbO.getString("secondAdviserFirstName");
                    String secondAdviserLastName = dbO.getString("secondAdviserLastName");
                    String secondAdviserEmail = dbO.getString("secondAdviserEmail");

                    System.err.println("Adding new Abstract for user " +
                            "[_id=" + id + ", title=" + title + ", format=" + format + ", abstracts=" + abstracts + ", " +
                            "presentationType=" + presentationType + ", formatChange=" + formatChange + ", discipline=" + discipline + ", featured=" + featured + ", " +
                            "mediaServicesEquipment=" + mediaServicesEquipment + ", specialRequirements=" + specialRequirements + ", otherInfo=" + otherInfo + ", " +
                            "approval=" + approval + ", cc=" + cc + ", rejection=" + rejection + ", group=" + group + ", roomAssignment="
                            + roomAssignment + ", totalRewriteVotes=" + totalRewriteVotes + ", majorRewriteVotes=" + majorRewriteVotes + ", " +
                            "minorRewriteVotes=" + minorRewriteVotes + ", acceptedVotes=" + acceptedVotes + ", comments=" + comments + ", isPrimarySubmission="
                            + isPrimarySubmission + ", resubmitFlag=" + resubmitFlag + ", firstPresenterFirstName=" + firstPresenterFirstName + ", " +
                            "firstPresenterLastName=" + firstPresenterLastName + ", firstPresenterEmail=" + firstPresenterEmail + ", secondPresenterFirstName="
                            + secondPresenterFirstName + ", " + "secondPresenterLastName=" + secondPresenterLastName + ", secondPresenterEmail=" + secondPresenterEmail
                            + ", thirdPresenterFirstName=" + thirdPresenterFirstName + ", " + "thirdPresenterLastName=" + thirdPresenterLastName + ", " +
                            "thirdPresenterEmail=" + thirdPresenterEmail + ", firstAdviserFirstName=" + firstAdviserFirstName + ", " + "firstAdviserLastName="
                            + firstAdviserLastName + ", firstAdviserEmail=" + firstAdviserEmail + ", secondAdviserFirstName=" + secondAdviserFirstName + ", " + "secondAdviserLastName="
                            + secondAdviserLastName + ", secondAdviserEmail=" + secondAdviserEmail + ']');

                    return abstractController.addNewAbstract(userID, title, format, abstracts, presentationType, formatChange
                    , discipline, featured, mediaServicesEquipment, specialRequirements, otherInfo, approval, cc, rejection
                    group, roomAssignment, totalRewriteVotes, majorRewriteVotes, minorRewriteVotes, acceptedVotes, comments,
                            isPrimarySubmission, resubmitFlag, firstPresenterFirstName, firstPresenterLastName,
                            firstPresenterEmail, secondPresenterFirstName, secondPresenterLastName, secondPresenterEmail
                    , thirdPresenterFirstName, thirdPresenterLastName, thirdPresenterEmail, firstAdviserFirstName,
                            firstAdviserLastName, firstAdviserEmail).toString();
                }
                catch(NullPointerException e)
                {
                    System.err.println("A value was malformed or omitted, new journal request failed.");
                    return null;
                }

            }
            else
            {
                System.err.println("Expected BasicDBObject, received " + o.getClass());
                return null;
            }
        }
        catch(RuntimeException ree)
        {
            ree.printStackTrace();
            return null;
        }
    }

