package guru.sfg.brewery.security;

import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserUnlockService {

    private final UserRepository userRepository;

    @Scheduled(fixedRate = 300000)
    public void unlockAccounts() {
        log.debug("Running Unlocking Accounts");

        List<User> users = userRepository.findAllByAccountNonLockedAndLastModifiedDateBefore(false,
                Timestamp.valueOf(LocalDateTime.now().minusSeconds(30)));

        if (!users.isEmpty()) {
            log.debug("Locked Accounts Found, Unlocking");
            users.forEach(user -> user.setAccountNonLocked(true));

            userRepository.saveAll(users);
        }
    }
}