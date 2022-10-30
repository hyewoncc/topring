package springbook.domain;

public class Member {

    private Long id;

    private String slackId;

    private String username;

    private String thumbnailUrl;

    private boolean isFirstLogin = true;

    private String token;

    private Long workspaceId;

    public Member() {
    }

    public Member(final String slackId, final String username, final String thumbnailUrl) {
        this.slackId = slackId;
        this.username = username;
        this.thumbnailUrl = thumbnailUrl;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(final String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(final boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(final Long workspaceId) {
        this.workspaceId = workspaceId;
    }
}
