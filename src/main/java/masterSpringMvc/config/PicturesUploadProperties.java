package masterSpringMvc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "upload.pictures")
public class PicturesUploadProperties {

    private String uploadPath;
    private String anonymousPicture;

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getAnonymousPicture() {
        return anonymousPicture;
    }

    //new DefaultResourceLoader().getResource
    public void setAnonymousPicture(String anonymousPicture) {
        this.anonymousPicture = anonymousPicture;
    }
}
