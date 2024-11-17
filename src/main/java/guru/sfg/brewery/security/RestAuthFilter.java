package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class RestAuthFilter extends AbstractRestAuthFilter {

    public RestAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    protected String getUserName(HttpServletRequest request) {
        return request.getHeader("Api-Key");
    }

    protected String getPassword(HttpServletRequest request) {
        return request.getHeader("Api-Secret");
    }
}
