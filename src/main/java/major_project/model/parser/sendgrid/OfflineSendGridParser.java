package major_project.model.parser.sendgrid;

import major_project.model.parser.sendgrid.container.Email;

public class OfflineSendGridParser implements SendGridParser {

    @Override
    public boolean sendEmail(Email email) {
        return true;
    }
    
}
