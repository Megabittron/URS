package server.database.login;

import org.json.JSONObject;
import spark.Request;
import spark.Response;

/**
 *
 */
public class LoginRequestHandler {

    private final LoginController loginController;
    public LoginRequestHandler(LoginController loginController){
        this.loginController = loginController;
    }

    /**Method called from Server when the 'api/login' endpoint is received.
     * Get a JSON response with a list of all the users in the database.
     *
     * @param req the HTTP request
     * @param res the HTTP response
     * @return one user in JSON formatted string and if it fails it will return text with a different HTTP status code
     */

    public String loginUser(Request req, Response res) {
        String idTokenString = getIdTokenString(req);
        String verifyResponse = loginController.verifyIdToken(idTokenString);

        if (!verifyResponse.equals("null")) {
            return verifyResponse;
        } else {
            return "null";
        }

    }

    /**Method called from Server when the 'api/login' endpoint is received.
     * Get a JSON response with a list of all the users in the database.
     *
     * @param req the HTTP request
     * @return one user in JSON formatted string and if it fails it will return text with a different HTTP status code
     */

    private String getIdTokenString(Request req) {
        return new JSONObject(req.body()).getString("idToken");
    }
}