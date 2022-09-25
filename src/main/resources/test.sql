select cs
from ChannelSubscription cs
where cs.member.id = :memberId;

select cs
from ChannelSubscription cs
where cs.member.id = :memberId
order by cs.viewOrder;

select b
from Bookmark b
WHERE b.message.id = :messageId
  and b.member.id = :memberId;
