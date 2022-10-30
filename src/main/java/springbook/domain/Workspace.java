package springbook.domain;

public class Workspace {

    private Long id;

    private String slackId;

    private String botToken;

    private String botSlackId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getSlackId() {
        return slackId;
    }

    public void setSlackId(final String slackId) {
        this.slackId = slackId;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(final String token) {
        this.botToken = token;
    }

    public String getBotSlackId() {
        return botSlackId;
    }

    public void setBotSlackId(final String botSlackId) {
        this.botSlackId = botSlackId;
    }
}
