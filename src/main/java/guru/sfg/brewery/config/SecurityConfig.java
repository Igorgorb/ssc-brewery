package guru.sfg.brewery.config;

import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.RestParamAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    public RestParamAuthFilter restParamAuthFilter(AuthenticationManager authenticationManager) {
        RestParamAuthFilter filter = new RestParamAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(restParamAuthFilter(authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class);
        http
                .authorizeRequests(authorize -> authorize.antMatchers("/",
                                "/webjars/**",
                                "/login",
                                "/resources/**",
                                "/beers/find",
                                "/beers"
                        ).permitAll()
                        .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                        .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll())
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("spring")
                .password("{bcrypt}$2a$10$emXxGqOzgs36QSH72GkG0uyEwTPY2uvdmFTGgF9WvO7ZYeHLGDkji")
//                .password("{SSHA}B62NvcIaSrw06fYoVHvvIgNjnHBukQArw6cjtg==")
//                .password("guru")
                .roles("ADMIN")
                .and()
                .withUser("user")
//                .password("$2a$10$drHca4iFY/tD39Fp3.Mie.famYGpw/CdpWC5Ik3JrYjweMc/0RJwq")
                .password("{sha256}9a89fa11d0eb5a53599b8321bc5e430b1c697dbd17a1329f33a20853593dc68b88e76570d8a500b8")
//                .password("{SSHA}B62NvcIaSrw06fYoVHvvIgNjnHBukQArw6cjtg==")
//                .password("password")
                .roles("USER")
                .and()
                .withUser("scott")
                .password("{bcrypt10}$2a$10$bsX5g5uXnnC3hfS1vaifxOSgKUP5z7tHjX.y58rmNMuDEpgHz8DNG")
//                .password("{bcrypt15}$2a$15$Q2c.D6CwPcr3TS8nbBBOcex1S1ogKXNMRpNQuQfmBJkxdLJsKTwGa")
//                .password("{ldap}{SSHA}gffdgLmjq4JH8dyryzpw74xQusvR+9gwZ/6TSw==")
//                .password("tiger")
                .roles("CUSTOMER");
    }

    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("spring")
//                .password("guru")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}
