package masterSpringMvc;

import masterSpringMvc.config.PicturesUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;
import java.net.Socket;

@SpringBootApplication
@EnableConfigurationProperties({PicturesUploadProperties.class})
public class MasterSpringMvcSpringMvcApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MasterSpringMvcSpringMvcApplication.class, args);
	}
}
