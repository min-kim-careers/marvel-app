package major_project.model.parser.sendgrid.container;

public class Email {
    private String toEmail;
    private String toName;
    private String fromEmail;
    private String fromName;
    private String subject;
    private String content;

    public Email(
        String toEmail,
        String toName,
        String fromEmail,
        String fromName,
        String subject,
        String content
        ) {
        this.toEmail = toEmail;
        this.toName = toName;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
        this.subject = subject;
        this.content = content;
    }

    public String getData() {
        String res = """
                {
                    \"personalizations\": [
                        {
                            \"to\": [
                                {
                                    \"email\": \"%s\",
                                    \"name\": \"%s\"
                                }
                            ],
                            \"subject\": \"%s\"
                        }
                    ],
                    \"content\": [
                        {
                            \"type\": \"text/plain\",
                            \"value\": \"%s\"
                        }
                    ],
                    \"from\": {
                        \"email\": \"%s\",
                        \"name\": \"%s\"
                    },
                    \"reply_to\": {
                        \"email\": \"%s\",
                        \"name\": \"%s\"
                    }
                }
                """;
        return String.format(res,
                    toEmail,
                    toName,
                    subject,
                    content,
                    fromEmail,
                    fromName,
                    fromEmail,
                    fromName
            );
    }

    public static class EmailBuilder {
        private String toEmail;
        private String toName;
        private String fromEmail;
        private String fromName;
        private String subject;
        private String content;

        public EmailBuilder to(String toEmail, String toName) {
            this.toEmail = toEmail;
            this.toName = toName;
            return this;
        }
        
        public EmailBuilder from(String fromEmail, String fromName) {
            this.fromEmail = fromEmail;
            this.fromName = fromName;
            return this;
        }

        public EmailBuilder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public Email build() {
            return new Email(
                toEmail,
                toName,
                fromEmail,
                fromName,
                subject,
                content
            );
        }
    }
}
