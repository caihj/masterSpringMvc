package masterSpringMvc.profile;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import java.io.*;


@Controller
public class PictureUploadController {

    public static final Resource PICTURE_DIR=new FileSystemResource("./pictures");

    @RequestMapping("upload")
    public String uploadPage(){
        return "profile/uploadPage";
    }

    public String onUpload(MultipartFile file ) throws IOException {
        String fileName = file.getOriginalFilename();
        File tempFile = File.createTempFile("pic",getFileExtension(fileName),PICTURE_DIR.getFile());
        InputStream in = file.getInputStream();
        OutputStream out = new FileOutputStream(tempFile);
        IOUtils.copy(in,out);

        return "profile/uploadPage";
    }

    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }


}
