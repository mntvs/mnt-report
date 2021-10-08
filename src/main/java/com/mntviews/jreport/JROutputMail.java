package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.mntviews.jreport.exception.JROutputException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.List;
import java.util.Properties;

import static com.mntviews.jreport.JRDefaultContext.checkIfNullThenDefault;

@JsonDeserialize(builder = JROutputMail.Builder.class)
public class JROutputMail extends JROutput {

    // todo Check null for required fields

    @JsonProperty("from")
    private final String from;
    @JsonProperty("to")
    private final String to;
    @JsonProperty("subject")
    private final String subject;
    @JsonProperty("body")
    private final String body;
    @JsonProperty("host")
    private final String host;
    @JsonProperty("userName")
    private final String userName;
    @JsonProperty("password")
    private final String password;
    @JsonProperty("port")
    private final String port;

    @JsonProperty("isAttachment")
    private final Boolean isAttachment;

    public String getPort() {
        return port;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getHost() {
        return host;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getIsAttachment() {
        return isAttachment;
    }

    private JROutputMail(Builder builder) {
        JROutputMail jrOutputMail = JRDefaultContext.getJrOutputMail();
        this.from = checkIfNullThenDefault(builder.from, jrOutputMail == null ? null : jrOutputMail.from);
        this.to = checkIfNullThenDefault(builder.to, jrOutputMail == null ? null : jrOutputMail.to);
        this.subject = checkIfNullThenDefault(builder.subject, jrOutputMail == null ? null : jrOutputMail.subject);
        this.body = checkIfNullThenDefault(builder.body, jrOutputMail == null ? null : jrOutputMail.body);
        this.host = checkIfNullThenDefault(builder.host, jrOutputMail == null ? null : jrOutputMail.host);
        this.userName = checkIfNullThenDefault(builder.userName, jrOutputMail == null ? null : jrOutputMail.userName);
        this.password = checkIfNullThenDefault(builder.password, jrOutputMail == null ? null : jrOutputMail.password);
        this.port = checkIfNullThenDefault(builder.port, jrOutputMail == null ? null : jrOutputMail.port);
        this.isAttachment = checkIfNullThenDefault(builder.isAttachment, jrOutputMail == null ? null : jrOutputMail.isAttachment);
    }

    public static Builder custom() {
        return new Builder();
    }

    @Override
    public void execute(List<JRFile> fileList) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.trust", host);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Multipart multipart = new MimeMultipart();


            if (isAttachment) {
                BodyPart messageBodyPart = new MimeBodyPart();
                // Now set the actual message
                messageBodyPart.setText(body);

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                for (JRFile file : fileList) {

                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new ByteArrayDataSource(file.getData().toByteArray(), file.getMimeType());

                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(file.getFileName());
                    multipart.addBodyPart(messageBodyPart);
                }

                // Send the complete message parts
                message.setContent(multipart);
            }

            Transport.send(message);
        } catch (Exception e) {
            throw new JROutputException(e);
        }


    }

    @JsonPOJOBuilder
    public static class Builder {
        private String from;
        private String to;
        private String subject;
        private String body;
        private String host;
        private String userName;
        private String password;
        private String port;
        private Boolean isAttachment;

        @JsonProperty("from")
        public Builder withFrom(String from) {
            this.from = from;
            return this;
        }

        @JsonProperty("to")
        public Builder withTo(String to) {
            this.to = to;
            return this;
        }

        @JsonProperty("subject")
        public Builder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        @JsonProperty("body")
        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        @JsonProperty("host")
        public Builder withHost(String host) {
            this.host = host;
            return this;
        }

        @JsonProperty("userName")
        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        @JsonProperty("password")
        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }


        @JsonProperty("port")
        public Builder withPort(String port) {
            this.port = port;
            return this;
        }

        @JsonProperty("isAttachment")
        public Builder withIsAttachment(Boolean isAttachment) {
            this.isAttachment = isAttachment;
            return this;
        }


        public JROutputMail build() {
            return new JROutputMail(this);
        }
    }

}
