package lt.seb.restful.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "spring.security.user")
@Validated
public record BasicAuthConfigProperties(
        @NotBlank String name,
        @NotBlank String password
) {}