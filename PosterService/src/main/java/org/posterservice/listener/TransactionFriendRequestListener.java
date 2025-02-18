package org.posterservice.listener;

import lombok.RequiredArgsConstructor;
import org.posterservice.notify.Notify;
import org.posterservice.notify.impl.friend.dto.AcceptFriendRequestDTO;
import org.posterservice.notify.impl.friend.dto.DeclineFriendRequestDTO;
import org.posterservice.notify.impl.friend.dto.FriendRequestDTO;
import org.posterservice.event.AcceptFriendRequestEvent;
import org.posterservice.event.DeclineFriendRequestEvent;
import org.posterservice.event.FriendRequestEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class TransactionFriendRequestListener {

    private final Notify friendNotify;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void friendRequestListen(FriendRequestEvent event) {
        // update cache for example
        var friendRequestDTO = (FriendRequestDTO)event.getSource();
        friendNotify.notify(friendRequestDTO);
    }


    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void acceptFriendRequestListen(AcceptFriendRequestEvent event) {
        var acceptFriendRequestDTO = (AcceptFriendRequestDTO)event.getSource();
        friendNotify.notify(acceptFriendRequestDTO);
    }


    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void declineFriendRequestListen(DeclineFriendRequestEvent event) {
        var declineFriendRequestDTO = (DeclineFriendRequestDTO)event.getSource();
        friendNotify.notify(declineFriendRequestDTO);
    }
}
