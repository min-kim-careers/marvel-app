package major_project.model.parser.marvel;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

public class MarvelAuthenticator {
    private final static String publicKey = System.getenv("INPUT_API_KEY");
    private final static String privateKey = System.getenv("INPUT_API_APP_ID");

    public static String getAuthKey() {
        long ts = new Timestamp(System.currentTimeMillis()).getTime();
        String authKey = ts + privateKey + publicKey;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(StandardCharsets.UTF_8.encode(authKey));
            byte[] digest = md.digest();
            String myHash = String.format("%032x", new BigInteger(1, digest));
            return "ts=" + String.valueOf(ts) + "&apikey=" + publicKey + "&hash=" + myHash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
