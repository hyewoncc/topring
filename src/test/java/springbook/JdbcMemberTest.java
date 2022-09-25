package springbook;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springbook.domain.Channel;
import springbook.domain.ChannelSubscription;
import springbook.domain.Member;
import springbook.jdbc.JdbcChannel;
import springbook.jdbc.JdbcChannelSubscription;
import springbook.jdbc.JdbcMember;

@SpringBootTest
class JdbcMemberTest {

    @Autowired
    private JdbcMember jdbcMember;

    @Autowired
    private JdbcChannel jdbcChannel;

    @Autowired
    private JdbcChannelSubscription jdbcChannelSubscription;

    @DisplayName("멤버 벌크 인서트")
    @Test
    void memberBulkInsert() {
        final int size = 2_000;
        final StringBuilder stringBuilder = new StringBuilder(32);

        Member[] members = new Member[size];

        for (int i = 0; i < size; i++) {
            String uuid = UUID.randomUUID().toString();
            String slicedUuid = uuid.substring(uuid.length() - 11);

            Member member = new Member();
            member.setSlackId(stringBuilder.append("U").append(slicedUuid).toString());
            stringBuilder.setLength(0);

            member.setThumbnailUrl(stringBuilder.append("https://").append(slicedUuid).append(".png").toString());
            stringBuilder.setLength(0);

            member.setUsername(stringBuilder.append("사용자").append(i).toString());
            stringBuilder.setLength(0);

            members[i] = member;
        }

        jdbcMember.batchInsert(Arrays.asList(members));
    }

    @DisplayName("채널 벌크 인서트")
    @Test
    void channelBulkInsert() {
        final int size = 500;
        final StringBuilder stringBuilder = new StringBuilder(32);

        Channel[] channels = new Channel[size];

        for (int i = 0; i < size; i++) {
            String uuid = UUID.randomUUID().toString();
            String slicedUuid = uuid.substring(uuid.length() - 11);

            Channel channel = new Channel();
            channel.setSlackId(stringBuilder.append("C").append(slicedUuid).toString());
            stringBuilder.setLength(0);

            channel.setName(stringBuilder.append(slicedUuid).append("채널").toString());
            stringBuilder.setLength(0);

            channels[i] = channel;
        }

        jdbcChannel.batchInsert(Arrays.asList(channels));
    }

    @DisplayName("구독 벌크 인서트")
    @Test
    void channelSubscriptionBulkInsert() {
        final int channelSize = 500;
        final int memberSize = 2_000;
        final int size = 100_000;

        ChannelSubscription[] subscriptions = new ChannelSubscription[size];

        for (int i = 0; i < size; i++) {
            Random random = new Random();

            ChannelSubscription subscription = new ChannelSubscription();
            subscription.setViewOrder(random.nextInt(channelSize - 1) + 1);

            subscription.setChannelId((long)(random.nextInt(channelSize - 1) + 1));

            subscription.setMemberId((long)(random.nextInt(memberSize - 1) + 1));

            subscriptions[i] = subscription;
        }

        jdbcChannelSubscription.batchInsert(Arrays.asList(subscriptions));
    }
}
