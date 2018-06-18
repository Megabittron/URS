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

    /**  Helper method that gets a single abstract specified by the `id` specific user ID
     //     * parameter in the request.
     //     *
     //     * @param id the Mongo ID of the desired Abstract
     //     * @return the desired Abstract as a JSON object if the user with that ID is found,
     //     * and `null` if no user with that ID is found
     //     */

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

    /** Helper method which iterates through the collection, receiving all
     //     * documents if no query parameter is specified. If the userID query parameter
     //     * is specified, then the collection is filtered so only documents of that
     //     * specified userID are found.
     //     *
     //     * @param queryParams
     //     * @return an array of Abstracts in a JSON formatted string
     //     */

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
            filterDoc = filterDoc.append("title", contentRegQuery);        }

        if (queryParams.containsKey("content")) {
            String targetContent = (queryParams.get("content")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("content", contentRegQuery);
        }



}
