package com.mntviews.jreport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.mntviews.jreport.exception.JRConnectionException;

import java.sql.Connection;
import java.sql.SQLException;

@JsonDeserialize(builder = JRConnection.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JRConnection {

    @JsonIgnore
    private final Connection connection;
    @JsonIgnore
    private final Connectable connectionCreator;
    @JsonProperty("url")
    private final String url;
    @JsonProperty("userName")
    private final String userName;
    @JsonProperty("password")
    private final String password;
    @JsonProperty("dataSourceTag")
    private final String dataSourceTag;

    private JRConnection(Builder builder) {
        this.connection = builder.connection;
        this.connectionCreator = builder.connectionCreator;
        this.url = builder.url;
        this.userName = builder.userName;
        this.password = builder.password;
        this.dataSourceTag = builder.dataSourceTag;
    }


    @JsonIgnore
    public Connectable getConnectionCreator() {
        return connectionCreator;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDataSourceTag() {
        return dataSourceTag;
    }

    @JsonIgnore
    public Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            if (connectionCreator != null) {
                try {
                    return connectionCreator.createConnection(url, userName, password);
                } catch (SQLException e) {
                    throw new JRConnectionException(e);
                }
            } else return null;
        }
    }

    public static Builder custom() {
        return new JRConnection.Builder();
    }

    public static Builder custom(JRConnection jrConnection) {
        return new JRConnection.Builder()
                .withConnection(jrConnection.getConnection())
                .withUrl(jrConnection.getUrl())
                .withUserName(jrConnection.getUserName())
                .withPassword(jrConnection.getPassword())
                .withDataSourceTag(jrConnection.getDataSourceTag())
                .withConnectionCreator(jrConnection.getConnectionCreator());
    }

    @JsonPOJOBuilder
    public static class Builder {
        private Connection connection;
        private Connectable connectionCreator;

        @JsonProperty("url")
        private String url;
        @JsonProperty("userName")
        private String userName;
        @JsonProperty("password")
        private String password;
        @JsonProperty("dataSourceTag")
        private String dataSourceTag;


        public JRConnection build() {
            return new JRConnection(this);
        }

        @JsonIgnore
        public Builder withConnection(Connection connection) {
            this.connection = connection;
            return this;
        }

        @JsonProperty("url")
        public Builder withUrl(String url) {
            this.url = url;
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

        @JsonProperty("dataSourceTag")
        public Builder withDataSourceTag(String dataSourceTag) {
            this.dataSourceTag = dataSourceTag;
            return this;
        }

        public Builder withConnectionCreator(Connectable connectionCreator) {
            if (this.connection == null) {
                this.connectionCreator = connectionCreator;
            }
            return this;
        }
    }


}
