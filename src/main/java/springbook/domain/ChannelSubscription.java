package springbook.domain;

public class ChannelSubscription {

    private Long id;

    private Long channelId;

    private Long memberId;

    private int viewOrder;

    public ChannelSubscription() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(final Long channelId) {
        this.channelId = channelId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(final Long memberId) {
        this.memberId = memberId;
    }

    public int getViewOrder() {
        return viewOrder;
    }

    public void setViewOrder(final int viewOrder) {
        this.viewOrder = viewOrder;
    }
}
