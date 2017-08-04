package masterSpringMvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/")
    public String hello(){
        return "Hello, world!";
    }

    @RequestMapping("/getPage")
    public String getPage(Model model,@RequestParam(value = "name",required = false,defaultValue = "") String userName){
        model.addAttribute("message","hello "+userName);
        return "resultPage";
    }
}
