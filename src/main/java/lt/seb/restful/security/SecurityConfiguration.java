package lt.seb.restful.security;

import lt.seb.restful.config.ConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(ConfigProperties.class)
public class SecurityConfiguration {
    private static final String INCLUDE_REGEX_PATTERN = "^(%s)$";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(getRequestMatcher(
                                "/swagger-ui.+|/api-docs|/actuator.*|/health.*|/error.*|/index.*|/events.*")).permitAll()
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    public RequestMatcher getRequestMatcher(final String paths) {
        final Pattern pattern = Pattern.compile(INCLUDE_REGEX_PATTERN.formatted(paths));
        return request -> pattern.matcher(request.getServletPath()).matches();
    }
}
