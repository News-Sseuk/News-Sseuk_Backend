package backend.newssseuk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "backend.newssseuk.springbootmongodb")
public class MongoConfig {
}
