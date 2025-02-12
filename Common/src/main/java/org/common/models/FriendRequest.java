package org.common.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "friend_requests")
@EqualsAndHashCode(exclude = "id")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private FriendRequestStatus status = FriendRequestStatus.PENDING;

    @Version
    private Long version;

    static public FriendRequest create(User sender, User receiver) {
        return FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .build();
    }

    public boolean isAccepted() {
        return status == FriendRequestStatus.ACCEPTED;
    }

    public boolean isPending() {
        return status == FriendRequestStatus.PENDING;
    }

    public boolean isDeclined() {
        return status == FriendRequestStatus.DECLINED;
    }

}