package springbook.domain;

public class Channel {

    private Long id;

    private String slackId;

    private String name;

    private Long workspaceId;

    public Channel() {
    }

    public Channel(final String slackId, final String name) {
        this.slackId = slackId;
        this.name = name;
    }

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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(final Long workspaceId) {
        this.workspaceId = workspaceId;
    }
}
