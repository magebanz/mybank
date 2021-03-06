package za.co.discovery.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"za.co.discovery.rest","za.co.discovery.service"})
@EnableJpaRepositories("za.co.discovery.dao")
@EntityScan("za.co.discovery.entities")
@EnableCaching
public class BankApplication {

    public static void main(String[] args) {

        SpringApplication.run(BankApplication.class, args);
    }

}
