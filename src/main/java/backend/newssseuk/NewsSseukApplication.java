package backend.newssseuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaAuditing
//@ComponentScan(basePackages = {"backend.newssseuk.springbootmongodb"})
//@EnableMongoRepositories(basePackages = {"backend.newssseuk.springbootmongodb"})
public class NewsSseukApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewsSseukApplication.class, args);
    }

}
