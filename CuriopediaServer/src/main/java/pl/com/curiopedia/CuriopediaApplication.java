package pl.com.curiopedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * Created by jakub on 18.06.17.
 */
@EntityScan(
        basePackageClasses = {CuriopediaApplication.class, Jsr310JpaConverters.class}
)
@SpringBootApplication
public class CuriopediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CuriopediaApplication.class, args);
    }
}