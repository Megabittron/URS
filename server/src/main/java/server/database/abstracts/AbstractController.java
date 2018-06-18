package server.database.abstracts;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Iterator;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class AbstractController {
    private final Gson gson;
    private MongoDatabase database;

    // AbstractCollection is the collection that the collection data is in"

    private final MongoCollection<Document> abstractCollection;

    // Construct Abstract controller for the Abstracts:
    public AbstractController(MongoDatabase database) {
        gson = new Gson();
        this.database = database;
        abstractCollection = database.getCollection("abstracts");
    }

    /**
     * Helper method that gets a single abstract specified by the `id` specific user ID
     * //     * parameter in the request.
     * //     *
     * //     * @param id the Mongo ID of the desired Abstract
     * //     * @return the desired Abstract as a JSON object if the user with that ID is found,
     * //     * and `null` if no user with that ID is found
     * //
     */

    public String getAbstract(String id) {
        FindIterable<Document> jsonUsers
                = abstractCollection
                .find(eq("_id", new ObjectId(id)));

        Iterator<Document> iterator = jsonUsers.iterator();
        if (iterator.hasNext()) {
            Document user = iterator.next();
            return user.toJson();
        } else {

            // Did not find a Abstract for that specific userID
            return null;
        }
    }

    /**
     * Helper method which iterates through the collection, receiving all
     * //     * documents if no query parameter is specified. If the userID query parameter
     * //     * is specified, then the collection is filtered so only documents of that
     * //     * specified userID are found.
     * //     *
     * //     * @param queryParams
     * //     * @return an array of Abstracts in a JSON formatted string
     * //
     */

    public String getAbstracts(Map<String, String[]> queryParams) {

        Document filterDoc = new Document();

        if (queryParams.containsKey("userID")) {
            String targetContent = (queryParams.get("userID")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("userID", contentRegQuery);
        } else {
            System.out.println("It had no userID");
            return JSON.serialize("[ ]");
        }

        if (queryParams.containsKey("title")) {
            String targetContent = (queryParams.get("title")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("title", contentRegQuery);
        }

        if (queryParams.containsKey("format")) {
            String targetContent = (queryParams.get("format")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("format", contentRegQuery);

        }

        if (queryParams.containsKey("abstract")) {
            String targetContent = (queryParams.get("abstract")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("abstract", contentRegQuery);
        }

        if (queryParams.containsKey("presentationType")) {
            String targetContent = (queryParams.get("presentationType")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("presentationType", contentRegQuery);
        }

        if (queryParams.containsKey("formatChange")) {
            String targetContent = (queryParams.get("formatChange")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("formatChange", contentRegQuery);
        }

        if (queryParams.containsKey("discipline")) {
            String targetContent = (queryParams.get("discipline")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("discipline", contentRegQuery);
        }


        if (queryParams.containsKey("featured")) {
            String targetContent = (queryParams.get("featured")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("featured", contentRegQuery);
        }

        if (queryParams.containsKey("mediaServicesEquipment")) {
            String targetContent = (queryParams.get("mediaServicesEquipment")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("mediaServicesEquipment", contentRegQuery);
        }
        if (queryParams.containsKey("specialRequirements")) {
            String targetContent = (queryParams.get("specialRequirements")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("specialRequirements", contentRegQuery);
        }
        if (queryParams.containsKey("otherInfo")) {
            String targetContent = (queryParams.get("otherInfo")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("otherInfo", contentRegQuery);
        }
        if (queryParams.containsKey("approval")) {
            String targetContent = (queryParams.get("approval")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("approval", contentRegQuery);
        }
        if (queryParams.containsKey("cc")) {
            String targetContent = (queryParams.get("cc")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("cc", contentRegQuery);
        }
        if (queryParams.containsKey("cc")) {
            String targetContent = (queryParams.get("cc")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("cc", contentRegQuery);
        }
        if (queryParams.containsKey("rejection")) {
            String targetContent = (queryParams.get("rejection")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("rejection", contentRegQuery);
        }
        if (queryParams.containsKey("group")) {
            String targetContent = (queryParams.get("group")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("group", contentRegQuery);
        }
        if (queryParams.containsKey("roomAssignment")) {
            String targetContent = (queryParams.get("roomAssignment")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("roomAssignment", contentRegQuery);
        }

        if (queryParams.containsKey("totalRewriteVotes")) {
            String targetContent = (queryParams.get("totalRewriteVotes")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("totalRewriteVotes", contentRegQuery);
        }


        if (queryParams.containsKey("majorRewriteVotes")) {
            String targetContent = (queryParams.get("majorRewriteVotes")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("majorRewriteVotes", contentRegQuery);
        }















        //FindIterable comes from mongo, Document comes from Gson
        FindIterable<Document> matchingAbstracts = abstractCollection.find(filterDoc);

        return JSON.serialize(matchingAbstracts);

    }
}