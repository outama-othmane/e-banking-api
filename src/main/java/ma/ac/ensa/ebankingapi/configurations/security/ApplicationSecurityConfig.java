package ma.ac.ensa.ebankingapi.configurations.security;

import ma.ac.ensa.ebankingapi.filters.JwtTokenVerifierFilter;
import ma.ac.ensa.ebankingapi.services.UserService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final JwtTokenVerifierFilter jwtTokenVerifierFilter;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
                                     UserService userService,
                                     JwtTokenVerifierFilter jwtTokenVerifierFilter) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtTokenVerifierFilter = jwtTokenVerifierFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO: Activate csrf
        http.csrf().disable();

        http.cors();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, Constants.APP_ROOT + "/auth/login").permitAll()
                .antMatchers(HttpMethod.GET, Constants.APP_ROOT + "/auth").permitAll()

                .and()
                .addFilterBefore(jwtTokenVerifierFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeRequests()
                .anyRequest()
                    .authenticated()

                .and();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);

        return provider;
    }
}
