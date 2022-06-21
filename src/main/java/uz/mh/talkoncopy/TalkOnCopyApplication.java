package uz.mh.talkoncopy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import uz.mh.talkoncopy.properties.ServerProperty;

@SpringBootApplication
@EnableConfigurationProperties({
        ServerProperty.class
})
public class TalkOnCopyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalkOnCopyApplication.class, args);
    }

}
