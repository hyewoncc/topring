package springbook.domain;

import java.time.LocalDateTime;

public class Reminder {

    private Long id;

    private Long memberId;

    private Long messageId;

    private LocalDateTime remindDate;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
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

    public LocalDateTime getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(final LocalDateTime remindDate) {
        this.remindDate = remindDate;
    }
}
