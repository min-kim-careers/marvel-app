package major_project.model.parser.sendgrid;

public class SendGridAuthenticator {
    private final static String publicKey = System.getenv("SENDGRID_API_KEY");

    public static String getAuthKey() {
        return publicKey;
    }
}
