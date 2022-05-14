package major_project.model.parser.sendgrid;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import major_project.model.parser.sendgrid.container.Email;

public class OnlineSendGridParser implements SendGridParser {
    private final static String hostUrl = "https://api.sendgrid.com/v3/mail/send";

    public boolean sendEmail(Email email) {
        try {
            URL url = new URL(hostUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");

            httpConn.setRequestProperty("Authorization", "Bearer " + SendGridAuthenticator.getAuthKey());
            httpConn.setRequestProperty("Content-Type", "application/json");

            httpConn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write(email.getData());
            writer.flush();
            writer.close();
            httpConn.getOutputStream().close();

            if (httpConn.getResponseCode() / 100 == 2) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
