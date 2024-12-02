package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // needed for use with Spring Data JPA SPeL
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/h2-console/**").permitAll() // do not use in production
                        .antMatchers("/",
                                "/webjars/**",
                                "/login",
                                "/resources/**"
                        ).permitAll()
                )
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginProcessingUrl("/login")
                        .loginPage("/").permitAll()
                        .successForwardUrl("/")
                        .defaultSuccessUrl("/"))
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/")
                        .permitAll())
                .httpBasic().and()
                .csrf().ignoringAntMatchers("/h2-console/**", "/api/**");

        // h2 console config
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // auth.userDetailsService(this.jpaUserDetailsService).passwordEncoder(passwordEncoder());
////        auth.inMemoryAuthentication()
////                .withUser("spring")
////                .password("{bcrypt}$2a$10$emXxGqOzgs36QSH72GkG0uyEwTPY2uvdmFTGgF9WvO7ZYeHLGDkji")
//////                .password("{SSHA}B62NvcIaSrw06fYoVHvvIgNjnHBukQArw6cjtg==")
//////                .password("guru")
////                .roles("ADMIN")
////                .and()
////                .withUser("user")
//////                .password("$2a$10$drHca4iFY/tD39Fp3.Mie.famYGpw/CdpWC5Ik3JrYjweMc/0RJwq")
////                .password("{sha256}9a89fa11d0eb5a53599b8321bc5e430b1c697dbd17a1329f33a20853593dc68b88e76570d8a500b8")
//////                .password("{SSHA}B62NvcIaSrw06fYoVHvvIgNjnHBukQArw6cjtg==")
//////                .password("password")
////                .roles("USER")
////                .and()
////                .withUser("scott")
////                .password("{bcrypt10}$2a$10$bsX5g5uXnnC3hfS1vaifxOSgKUP5z7tHjX.y58rmNMuDEpgHz8DNG")
//////                .password("{bcrypt15}$2a$15$Q2c.D6CwPcr3TS8nbBBOcex1S1ogKXNMRpNQuQfmBJkxdLJsKTwGa")
//////                .password("{ldap}{SSHA}gffdgLmjq4JH8dyryzpw74xQusvR+9gwZ/6TSw==")
//////                .password("tiger")
////                .roles("CUSTOMER");
//    }

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
