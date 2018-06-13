package server.database.users;

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

public class UserController {

    private final Gson gson;
    private MongoDatabase database;

    // userCollection is the collection that the users data is in.

    private final MongoCollection<Document> userCollection;

    // Construct controller for user.
    public UserController(MongoDatabase database) {
        gson = new Gson();
        this.database = database;
        userCollection = database.getCollection("users");
    }

    /**  Helper method that gets a single user specified by the `id`
     //     * parameter in the request.
     //     *
     //     * @param id the Mongo ID of the desired user
     //     * @return the desired user as a JSON object if the user with that ID is found,
     //     * and `null` if no user with that ID is found
     //     */
    public String getUser(String id) {
        FindIterable<Document> jsonUsers
                = userCollection
                .find(eq("_id", new ObjectId(id)));

        Iterator<Document> iterator = jsonUsers.iterator();
        if (iterator.hasNext()) {
            Document user = iterator.next();
            return user.toJson();
        } else {

            // Did not find a user

            return null;
        }
    }

    /** Helper method which iterates through the collection, receiving all
     //     * documents if no query parameter is specified. If the SubjectID query parameter
     //     * is specified, then the collection is filtered so only documents of that
     //     * specified SubjectID are found.
     //     *
     //     * @param queryParams
     //     * @return an array of Users in a JSON formatted string
     //     */
    public String getUsers(Map<String, String[]> queryParams) {

        Document filterDoc = new Document();

        if (queryParams.containsKey("SubjectID")) {
            int targetAge = Integer.parseInt(queryParams.get("SubjectID")[0]);
            filterDoc = filterDoc.append("SubjectID", targetAge);
        }

        if (queryParams.containsKey("FirstName")) {
            String targetContent = (queryParams.get("FirstName")[0]);
            Document contentRegQuery = new Document();
            contentRegQuery.append("$regex", targetContent);
            contentRegQuery.append("$options", "i");
            filterDoc = filterDoc.append("company", contentRegQuery);
        }

        //FindIterable comes from mongo, Document comes from Gson

        FindIterable<Document> matchingUsers = userCollection.find(filterDoc);

        return JSON.serialize(matchingUsers);
    }

    public String addNewUser(String SubjectID, String FirstName, String LastName) {

        Document filterDoc = new Document();

        Document contentRegQuery = new Document();
        contentRegQuery.append("$regex", SubjectID);
        contentRegQuery.append("$options", "i");
        filterDoc = filterDoc.append("SubjectID", contentRegQuery);

        FindIterable<Document> matchingUsers = userCollection.find(filterDoc);

        if(JSON.serialize(matchingUsers).equals("[ ]")){
            ObjectId id = new ObjectId();
                // by default a user is given the tshirt size medium and the role of a user
            Document newUser = new Document();
            newUser.append("_id", id);
            newUser.append("SubjectID", SubjectID);
            newUser.append("FirstName", FirstName);
            newUser.append("LastName", LastName);
            newUser.append("tShirtSize", "medium");
            newUser.append("role", "user");

            try {
                userCollection.insertOne(newUser);
                System.err.println("Successfully added new user [_id=" + id + ", SubjectID=" + SubjectID + " FirstName=" + FirstName + " LastName=" + LastName + " with Font/Style " + matchingUsers.first().get("role")+"/"+matchingUsers.first().get("tShirtSize")+ "]");
                // return JSON.serialize(newUser);
                Document userInfo = new Document();
                userInfo.append("_id", matchingUsers.first().get("_id"));
                userInfo.append("FirstName", matchingUsers.first().get("FirstName"));
                userInfo.append("LastName", matchingUsers.first().get("LastName"));
                userInfo.append("tShirtSize", matchingUsers.first().get("tShirtSize"));
                userInfo.append("role", matchingUsers.first().get("role"));

                return JSON.serialize(userInfo);
            } catch(MongoException me) {
                me.printStackTrace();
                return null;
            }
        } else {
            //assumes there will only be 1 user returned
            Document userInfo = new Document();
            userInfo.append("_id", matchingUsers.first().get("_id"));
            userInfo.append("FirstName", matchingUsers.first().get("FirstName"));
            userInfo.append("LastName", matchingUsers.first().get("LastName"));
            userInfo.append("tShirtSize", matchingUsers.first().get("tShirtSize"));
            userInfo.append("role", matchingUsers.first().get("role"));

            return JSON.serialize(userInfo);
        }

        //----------------------------


    }

    //edits user t-shirt size settings
    public String editUsertShirtSize(String userID, String size) {

        Document filterDoc = new Document();
        String id;

        if (!(userID == "")) {
            id = userID;
        } else {
            return JSON.serialize("[ ]");
        }

        Document newtShirtSize = new Document();
        newtShirtSize.append("tShirtSize", size);

        Document setQuery = new Document();
        setQuery.append("$set", newtShirtSize);

        //Document searchQuery = new Document().append("_id", new ObjectId(id));
        Document searchQuery = new Document().append("_id", new ObjectId(id));

        try {
            userCollection.updateOne(searchQuery, setQuery);
            return JSON.serialize(size);
        } catch(MongoException me) {
            me.printStackTrace();
            return null;
        }
    }
// changing a user's role setting
    public String editUserrole(String userID, String position) {

        Document filterDoc = new Document();
        String id;

        if (!(userID == "")) {
            id = userID;
        } else {
            return JSON.serialize("[ ]");
        }

        Document newrole = new Document();
        newrole.append("role", position);

        Document setQuery = new Document();
        setQuery.append("$set", newrole);

        //Document searchQuery = new Document().append("_id", new ObjectId(id));
        Document searchQuery = new Document().append("_id", new ObjectId(id));

        try {
            userCollection.updateOne(searchQuery, setQuery);

            return JSON.serialize(position);
        } catch(MongoException me) {
            me.printStackTrace();
            return null;
        }
    }

}
