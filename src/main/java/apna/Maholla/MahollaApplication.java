package apna.Maholla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MahollaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MahollaApplication.class, args);
	}
}
