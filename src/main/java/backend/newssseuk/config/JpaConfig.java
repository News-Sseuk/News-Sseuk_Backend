package backend.newssseuk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "backend.newssseuk.domain")
public class JpaConfig {
}
