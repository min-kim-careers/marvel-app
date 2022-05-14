package major_project.model.parser.sendgrid;

import major_project.model.parser.sendgrid.container.Email;

public interface SendGridParser {
    public boolean sendEmail(Email email);
}
