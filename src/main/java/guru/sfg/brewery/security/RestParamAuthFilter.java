package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class RestParamAuthFilter extends AbstractRestAuthFilter {
    public RestParamAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    protected String getUserName(HttpServletRequest request) {
        return request.getParameter("apiKey");
    }

    protected String getPassword(HttpServletRequest request) {
        return request.getParameter("apiSecret");
    }
}
