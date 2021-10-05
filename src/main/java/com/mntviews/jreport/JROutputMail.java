package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.mntviews.jreport.exception.JROutputException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import static com.mntviews.jreport.JRDefaultContext.*;

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

    @JsonProperty("withAttachment")
    private final Boolean withAttachment;

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

    public Boolean getWithAttachment() {
        return withAttachment;
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
        this.withAttachment = checkIfNullThenDefault(builder.withAttachment, jrOutputMail == null ? null : jrOutputMail.withAttachment);
    }

    public static Builder custom() {
        return new Builder();
    }

    private static class PipedDataSource implements DataSource {
        InputStream in;
        String type;

        public PipedDataSource(InputStream in, String type) {
            this.in = in;
            this.type = type;
        }

        public String getContentType() {
            return type;
        }

        public InputStream getInputStream() {
            return in;
        }

        public String getName() {
            return "DataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("No OutputStream");
        }
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


           // if (withAttachment!=null && withAttachment) {
            if (true) {
                BodyPart messageBodyPart = new MimeBodyPart();
                // Now set the actual message
                messageBodyPart.setText(body);

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                for (JRFile file : fileList) {

                    messageBodyPart = new MimeBodyPart();

                /*PipedInputStream in = new PipedInputStream();
                final PipedOutputStream out = new PipedOutputStream(in);
                ((Runnable) () -> {
                    try {
                        file.getData().writeTo(out);
                    } catch (IOException e) {
                        throw new JROutputException(e);
                    } finally {
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e) {
                                throw new JROutputException(e);
                            }
                        }
                    }
                }).run();*/

//                DataSource source = new PipedDataSource(in, file.getMimeType());
                    DataSource source = new ByteArrayDataSource(file.getData().toByteArray(), file.getMimeType());

                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(file.getFileName());
                    multipart.addBodyPart(messageBodyPart);
                }

                // Send the complete message parts
                message.setContent(multipart);
            }
            /*SMTPTransport transport = new SMTPTransport(session, null);
            transport.connect(this.host, this.userName, null);

            transport.issueCommand("AUTH XOAUTH2 " + new String(BASE64EncoderStream.encode(String.format("user=%s\1auth=Bearer %s\1\1", this.userName, smtpUserAccessToken).getBytes())), 235);
            transport.sendMessage(message, message.getAllRecipients());
            */
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
        private Boolean withAttachment;

        @JsonCreator
        public Builder() {

        }

        @JsonProperty("from")
        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        @JsonProperty("to")
        public Builder setTo(String to) {
            this.to = to;
            return this;
        }

        @JsonProperty("subject")
        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        @JsonProperty("body")
        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        @JsonProperty("host")
        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        @JsonProperty("userName")
        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        @JsonProperty("password")
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }


        @JsonProperty("port")
        public Builder setPort(String port) {
            this.port = port;
            return this;
        }

        @JsonProperty("withAttachment")
        public Builder setWithAttachment(Boolean withAttachment) {
            this.withAttachment = withAttachment;
            return this;
        }


        public JROutputMail build() {
            return new JROutputMail(this);
        }
    }

}
