package springbook;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbook.domain.Bookmark;
import springbook.domain.Channel;
import springbook.domain.ChannelSubscription;
import springbook.domain.Member;
import springbook.domain.Message;
import springbook.domain.Reminder;
import springbook.domain.Workspace;
import springbook.jdbc.JdbcBookmark;
import springbook.jdbc.JdbcChannel;
import springbook.jdbc.JdbcChannelSubscription;
import springbook.jdbc.JdbcMember;
import springbook.jdbc.JdbcMessage;
import springbook.jdbc.JdbcReminder;
import springbook.jdbc.JdbcWorkspace;

@Service
public class BatchInserter {

    private final JdbcBookmark jdbcBookmark;
    private final JdbcChannel jdbcChannel;
    private final JdbcChannelSubscription jdbcChannelSubscription;
    private final JdbcMember jdbcMember;
    private final JdbcMessage jdbcMessage;
    private final JdbcReminder jdbcReminder;
    private final JdbcWorkspace jdbcWorkspace;

    public BatchInserter(final JdbcBookmark jdbcBookmark, final JdbcChannel jdbcChannel,
                         final JdbcChannelSubscription jdbcChannelSubscription, final JdbcMember jdbcMember,
                         final JdbcMessage jdbcMessage, final JdbcReminder jdbcReminder,
                         final JdbcWorkspace jdbcWorkspace) {
        this.jdbcBookmark = jdbcBookmark;
        this.jdbcChannel = jdbcChannel;
        this.jdbcChannelSubscription = jdbcChannelSubscription;
        this.jdbcMember = jdbcMember;
        this.jdbcMessage = jdbcMessage;
        this.jdbcReminder = jdbcReminder;
        this.jdbcWorkspace = jdbcWorkspace;
    }

    @PostConstruct
    public void setUp() {
        insertWorkspace();
        System.out.println("workspace done");
        insertMember();
        System.out.println("member done");
        insertChannel();
        System.out.println("channel done");
        int timePlus = 0;
        int month = 1;
        for (int i = 1980; i < 2001; i++) {
            insertMessage(i, month, timePlus);
            System.out.println("message insert done : " + i);
            timePlus += 10_000;
        }
        month += 2;
        for (int i = 1980; i < 2001; i++) {
            insertMessage(i, month, timePlus);
            System.out.println("message insert done : " + i);
            timePlus += 10_000;
        }
        month += 2;
        for (int i = 1980; i < 2001; i++) {
            insertMessage(i, month, timePlus);
            System.out.println("message insert done : " + i);
            timePlus += 10_000;
        }
        month += 2;
        for (int i = 1980; i < 2001; i++) {
            insertMessage(i, month, timePlus);
            System.out.println("message insert done : " + i);
            timePlus += 10_000;
        }
        month += 2;
        for (int i = 1980; i < 2001; i++) {
            insertMessage(i, month, timePlus);
            System.out.println("message insert done : " + i);
            timePlus += 10_000;
        }

        System.out.println("??????????");
        insertBookmark();
        System.out.println("bookmark done 1");
        insertBookmark();
        System.out.println("bookmark done 2");
        insertBookmark();
        System.out.println("bookmark done 3");
        insertBookmark();
        System.out.println("bookmark done 4");
        insertBookmark();
        System.out.println("bookmark done 5");
        insertBookmark();
        System.out.println("bookmark done 6");

        insertReminder();
        System.out.println("reminder done 1");
        insertReminder();
        System.out.println("reminder done 2");
        insertReminder();
        System.out.println("reminder done 3");
        insertReminder();
        System.out.println("reminder done 4");
        insertReminder();
        System.out.println("reminder done 5");
        insertReminder();
        System.out.println("reminder done 6");
    }

    @Transactional
    public void insertWorkspace() {
        Workspace workspace = new Workspace();
        workspace.setSlackId("TESTWORKSPACE");
        workspace.setBotToken("TESTBOTTOKEN");
        workspace.setBotSlackId("TESTSLACKBOT");

        jdbcWorkspace.batchInsert(List.of(workspace));
    }

    @Transactional
    public void insertMember() {
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

            member.setToken(uuid);

            member.setWorkspaceId(1L);

            members[i] = member;
        }

        jdbcMember.batchInsert(Arrays.asList(members));
    }

    @Transactional
    public void insertChannel() {
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

            channel.setWorkspaceId(1L);

            channels[i] = channel;
        }

        jdbcChannel.batchInsert(Arrays.asList(channels));
    }

    @Transactional
    public void insertSubscription() {
        final int channelSize = 500;
        final int memberSize = 2_000;
        final int size = 10_000;

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

    @Transactional
    public void insertMessage(final int year, final int month, int time_plus) {
        final int loop = 1;
        LocalDateTime localDateTime = LocalDateTime.of(year, month, 1, 0, 0);
        for (int j = 0; j < loop; j++) {
            System.out.println(j + " loop");
            final int size = 10_000;
            final StringBuilder stringBuilder = new StringBuilder(64);

            Message[] messages = new Message[size];


            for (int i = 0; i < size; i++) {
                Random random = new Random();
                String uuid = UUID.randomUUID().toString();

                Message message = new Message();
                message.setSlackId(uuid);
                message.setText(stringBuilder.append("메시지 내용 ").append(uuid).toString());
                stringBuilder.setLength(0);

                message.setChannelId((long) (random.nextInt(499) + 1));
                message.setMemberId((long) (random.nextInt(1999) + 1));

                message.setPostedDate(localDateTime.plusMinutes(time_plus));
                message.setModifiedDate(localDateTime.plusMinutes(time_plus));

                messages[i] = message;
                time_plus += 1;
            }

            jdbcMessage.batchInsert(Arrays.asList(messages));
        }
    }

    @Transactional
    public void insertBookmark() {
        final int memberSize = 2_000;
        final int bookmarkPerMember = 50;

        for (int x = 1; x < memberSize + 1; x++) {
            LocalDateTime localDateTime = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
            Bookmark[] bookmarks = new Bookmark[bookmarkPerMember];

            for (int y = 0; y < bookmarkPerMember; y++) {
                Random random = new Random();

                Bookmark bookmark = new Bookmark();
                bookmark.setMessageId((long) (random.nextInt(999_999) + 1));
                bookmark.setMemberId((long) x);
                bookmark.setCreatedDate(localDateTime.plusMinutes(45 * y));

                bookmarks[y] = bookmark;
            }
            jdbcBookmark.batchInsert(Arrays.asList(bookmarks));
        }
    }

    @Transactional
    public void insertReminder() {
        final int memberSize = 2_000;
        final int reminderPerMember = 50;

        LocalDateTime localDateTime = LocalDateTime.of(2000, 1, 1, 0, 0);

        for (int x = 1; x < memberSize + 1; x++) {
            Reminder[] reminders = new Reminder[reminderPerMember];

            for (int y = 0; y < reminderPerMember; y++) {
                Random random = new Random();

                Reminder reminder = new Reminder();
                reminder.setMessageId((long) (random.nextInt(999_999) + 1));
                reminder.setMemberId((long) x);
                reminder.setRemindDate(localDateTime.plusMinutes(10 * y));
                reminders[y] = reminder;
            }

            jdbcReminder.batchInsert(Arrays.asList(reminders));
        }
    }
}
