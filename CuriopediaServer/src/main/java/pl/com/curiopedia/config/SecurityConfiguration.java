package pl.com.curiopedia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.com.curiopedia.auth.StatelessAuthenticationFilter;
import pl.com.curiopedia.domain.user.entity.Authority;

/**
 * Created by jakub on 19.06.17.
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan(basePackages = { "pl.com.curiopedia" })
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userService;
    private final StatelessAuthenticationFilter statelessAuthenticationFilter;

    @Autowired
    public SecurityConfiguration(UserDetailsService userService, StatelessAuthenticationFilter statelessAuthenticationFilter) {
        super(true);
        this.userService = userService;
        this.statelessAuthenticationFilter = statelessAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .headers().cacheControl()
        ;

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/users").hasAuthority(Authority.ROLE_AUTHOR)
                .antMatchers(HttpMethod.GET, "/api/users/me").hasAuthority(Authority.ROLE_GUEST)
                .antMatchers(HttpMethod.PATCH, "/api/users/update").hasAuthority(Authority.ROLE_ADMIN)
                .antMatchers(HttpMethod.PATCH, "/api/users/delete").hasAuthority(Authority.ROLE_ADMIN)

                .antMatchers(HttpMethod.POST, "/api/curios").hasAuthority(Authority.ROLE_AUTHOR)

                .antMatchers(HttpMethod.GET, "/api/categories").hasAuthority(Authority.ROLE_AUTHOR)
                .antMatchers(HttpMethod.POST, "/api/categories").hasAuthority(Authority.ROLE_AUTHOR)

                .antMatchers(HttpMethod.GET, "/api/sources").hasAuthority(Authority.ROLE_AUTHOR)
                .antMatchers(HttpMethod.POST, "/api/sources").hasAuthority(Authority.ROLE_AUTHOR)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http401AuthenticationEntryPoint("'Bearer token_type=\"JWT\"'"));

        http.addFilterBefore(statelessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Prevent StatelessAuthenticationFilter will be added to Spring Boot filter chain.
     * Only Spring Security must use it.
     */
    @Bean
    public FilterRegistrationBean registration(StatelessAuthenticationFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userService;
    }
}