package masterSpringMvc.controller;

import masterSpringMvc.profile.UserProfileSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserProfileSession userProfileSession;

    @RequestMapping("/")
    public String hello(){

         List<String> tastes = userProfileSession.getTastes();
         if(tastes.isEmpty()){
             return "redirect:/profile";
         }
         return "redirect:/search/mixed;keywords="+String.join(",",userProfileSession.getTastes());
    }

    @RequestMapping("/getPage")
    public String getPage(Model model,@RequestParam(value = "name",required = false,defaultValue = "") String userName){
        model.addAttribute("message","hello "+userName);
        return "resultPage";
    }
}
