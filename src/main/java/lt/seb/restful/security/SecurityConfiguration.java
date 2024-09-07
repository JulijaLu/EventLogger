package lt.seb.restful.security;

import lombok.RequiredArgsConstructor;
import lt.seb.restful.config.BasicAuthConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(BasicAuthConfigProperties.class)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private static final String INCLUDE_REGEX_PATTERN = "^(%s)$";

    private final BasicAuthConfigProperties basicAuthConfigProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(getRequestMatcher("/swagger-ui.+|/api-docs|/actuator.*|/health.*|/error.*")).permitAll()
                    .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails userDetails = User.builder()
                .username(basicAuthConfigProperties.name())
                .password(passwordEncoder().encode(basicAuthConfigProperties.password()))
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    public RequestMatcher getRequestMatcher(final String paths) {
        final Pattern pattern = Pattern.compile(INCLUDE_REGEX_PATTERN.formatted(paths));
        return request -> pattern.matcher(request.getServletPath()).matches();
    }
}
