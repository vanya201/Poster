package org.posterservice.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.common.models.User;
import org.posterservice.exception.UserNotFound;
import org.posterservice.repositories.UserSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFriendsService {
    private final UserSearchRepository userSearchRepository;
    private final SearchUsersService searchUsersService;

    public List<User> searchFriendsByUser(User user, Pageable page) {
        return userSearchRepository.findByFriendsContains(user, page).getContent();
    }



    @Transactional
    public void deleteFriend(User userDetails, String friendName) {
        User user = searchUsersService.searchUserByName(userDetails.getUsername());
        User friend = searchUsersService.searchUserByName(friendName);
        user.removeFriend(friend);
        userSearchRepository.save(friend);
    }



    public void addFriend(User user, User friend) {
        user.setFriend(friend);
        userSearchRepository.save(friend);
    }
}
