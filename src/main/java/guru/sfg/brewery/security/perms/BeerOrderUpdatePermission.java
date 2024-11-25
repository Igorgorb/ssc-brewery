package guru.sfg.brewery.security.perms;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('order.update') or hasAuthority('customer.order.update')" +
        " AND @beerOrderAuthenticationManager.customerIdMatches(authentication, #customerId)")
public @interface BeerOrderUpdatePermission {
}
