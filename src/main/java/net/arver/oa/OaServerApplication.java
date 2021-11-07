package net.arver.oa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class OaServerApplication {


    public static void main(final String[] args) {
        SpringApplication.run(OaServerApplication.class, args);
    }

}
