package springbook.jdbc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import springbook.domain.Bookmark;
import springbook.domain.Message;
import springbook.domain.Reminder;

@SpringBootTest
class JdbcMessageTest {

    @Autowired
    private JdbcMessage jdbcMessage;

    @Autowired
    private JdbcBookmark jdbcBookmark;

    @Autowired
    private JdbcReminder jdbcReminder;

//    @DisplayName("메시지 벌크 인서트")
//    @Test
//    void messageBulkInsert() {
//        final var memberIds = List.of(3012L, 3013L, 3015L, 3016L, 3017L, 3019L, 3042L);
//        final var channelIds = List.of(2L, 3L, 4L, 5L, 6L, 7L, 8L);
//
//        final int size = 100_000;
//        final StringBuilder stringBuilder = new StringBuilder(64);
//
//        Message[] messages = new Message[size];
//
//        LocalDateTime localDateTime = LocalDateTime.of(2000, 1, 1, 0, 0);
//
//        for (int i = 0; i < size; i++) {
//            Random random = new Random();
//            String uuid = UUID.randomUUID().toString();
//
//            Message message = new Message();
//            message.setSlackId(uuid);
//            message.setText(stringBuilder.append("메시지 내용 ").append(uuid).toString());
//            stringBuilder.setLength(0);
//
//            message.setChannelId(channelIds.get(random.nextInt(7)));
//            message.setMemberId(memberIds.get(random.nextInt(7)));
//
//            message.setPostedDate(localDateTime.plusMinutes(i));
//            message.setModifiedDate(localDateTime.plusMinutes(i));
//
//            messages[i] = message;
//        }
//
//        jdbcMessage.batchInsert(Arrays.asList(messages));
//    }

    @DisplayName("테스트 메시지 벌크 인서트")
    @Test
    void messageBulkInsertToTest() {
        final int loop = 10;
        LocalDateTime localDateTime = LocalDateTime.of(2000, 1, 1, 0, 0);
        int time_plus = 0;
        for (int j = 0; j < loop; j++) {
            System.out.println(j + " loop");
            final int size = 100_000;
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

    @DisplayName("북마크 벌크 인서트")
    @Test
    void bookmarkInsert() {
        final int memberSize = 2_000;
        final int bookmarkPerMember = 300;

        for (int x = 1; x < memberSize + 1; x++) {
            LocalDateTime localDateTime = LocalDateTime.of(2010, 10, 10, 0, 0, 0);
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

    @DisplayName("리마인더 벌크 인서트")
    @Test
    void reminderInsert() {
        EmptyResultDataAccessException
        final int memberSize = 2_000;
        final int reminderPerMember = 300;

        LocalDateTime localDateTime = LocalDateTime.of(2005, 1, 1, 0, 0);

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
