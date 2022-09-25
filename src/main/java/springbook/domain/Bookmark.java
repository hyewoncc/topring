package springbook.domain;

import java.time.LocalDateTime;

public class Bookmark {

    private Long id;

    private Long memberId;

    private Long messageId;

    private LocalDateTime createdDate;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setMemberId(final Long memberId) {
        this.memberId = memberId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(final Long messageId) {
        this.messageId = messageId;
    }


}
