package guru.sfg.brewery.security;

import guru.sfg.brewery.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class BeerOrderAuthenticationManager {

    public boolean customerIdMatches(Authentication authentication, UUID customerId) {
        User authenticatedUser = (User) authentication.getPrincipal();

//        if (Objects.isNull(authenticatedUser)
//                || Objects.isNull(authenticatedUser.getCustomer())) {
//            log.debug("Auth user Customer Id: isNull Customer Id: {}", customerId);
//            return false;
//        }
        log.debug("Auth user Customer Id: {} Customer Id: {}", authenticatedUser.getCustomer().getId(), customerId);

        return authenticatedUser.getCustomer().getId().equals(customerId);
    }
}
