package springbook.domain;

import java.time.LocalDateTime;

public class Message {

    private Long id;

    private String slackId;

    private Long channelId;

    private String text;

    private Long memberId;

    private LocalDateTime postedDate;

    private LocalDateTime modifiedDate;

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

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(final Long channelId) {
        this.channelId = channelId;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(final Long memberId) {
        this.memberId = memberId;
    }

    public LocalDateTime getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(final LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(final LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}