package masterSpringMvc.profile;

import masterSpringMvc.config.PicturesUploadProperties;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;


@Controller
@SessionAttributes("picturePath")
public class PictureUploadController {

    private Logger logger = LoggerFactory.getLogger(PictureUploadController.class);

    public  final String PICTURE_DIR;

    private final String anonymousPicture;

    private final MessageSource messageSource;

    public PictureUploadController(PicturesUploadProperties uploadProperties,MessageSource messageSource){
        PICTURE_DIR = uploadProperties.getUploadPath();
        anonymousPicture = uploadProperties.getAnonymousPicture();
        this.messageSource = messageSource;
    }

    @ModelAttribute("picturePath")
    public String picturePath(){
        return anonymousPicture;
    }

    @RequestMapping(value = "/uploadedPicture")
    public void getUploadedPicture(HttpServletResponse response,@ModelAttribute("picturePath")String picturePath) throws IOException{
        //ClassPathResource resource = new ClassPathResource("/images/anonymous.png");
        logger.info("picturePath is "+picturePath);
        if(!picturePath.contains("classpath")) {
            File fp = new File(picturePath);
            response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath));
            IOUtils.copy(new FileInputStream(fp), response.getOutputStream());
        }else {
            Resource resource = new DefaultResourceLoader().getResource(picturePath);
            response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath));
            IOUtils.copy(resource.getInputStream(), response.getOutputStream());
        }
    }


    @RequestMapping(value = "/profile",params = {"upload"},method = RequestMethod.POST)
    public String onUpload(MultipartFile file , RedirectAttributes redirectAttributes,Model model) throws IOException {

        if(file.isEmpty() || !isImage(file)){
            redirectAttributes.addFlashAttribute("error","Incorrect file. Please upload a picture");
            return "redirect:/profile";
        }
        String path = copyFileToPictures(file);
        model.addAttribute("picturePath",path);
        return "redirect:profile";
    }

    private String copyFileToPictures(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File tempFile = File.createTempFile("pic",getFileExtension(fileName),new DefaultResourceLoader().getResource(PICTURE_DIR).getFile());
        try(InputStream in = file.getInputStream();
            OutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
       return  tempFile.getPath();
    }

    private boolean isImage(MultipartFile file){
        return file.getContentType().startsWith("image");
    }

    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOException(IOException exception){
        ModelAndView modelAndView = new ModelAndView("profile/uploadPage");
        modelAndView.addObject("error",exception.getMessage());
        return modelAndView;
    }

    @RequestMapping("/uploadError")
    public ModelAndView onUploadError(HttpServletRequest request,Locale locale){
        ModelAndView modelAndView = new ModelAndView("profile/uploadPage");
        modelAndView.addObject("error",messageSource.getMessage("upload.file.too.big",null,locale));
        return modelAndView;
    }

}
