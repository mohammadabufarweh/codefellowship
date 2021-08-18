package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
public class ApplicationUserController {

    @GetMapping("/")
    public String getHome(){
      return "homepage.html";
    }
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    PostRepository postRepository;

    @GetMapping("/signup")
   public String getSignUpPage(){
    return "signup.html";
}

    @GetMapping("/login")
    public String getSignInPage(){
        return "siginin.html";
    }

    @PostMapping("/signup")
    public RedirectView signUp(@RequestParam(value="username") String username, @RequestParam(value="password") String password ,
                               @RequestParam (value = "firstName") String firstName,
                               @RequestParam (value = "lastName") String lastName,
                               @RequestParam(value = "dateOfBirth")String dateOfBirth,
                               @RequestParam (value = "bio") String bio){

//        System.out.println("errorrrr"+username+firstName+lastName+dateOfBirth+bio+bCryptPasswordEncoder.encode(password));
        ApplicationUser newUser = new ApplicationUser(username,bCryptPasswordEncoder.encode(password),firstName,lastName,dateOfBirth,bio);
        applicationUserRepository.save(newUser);
        return new RedirectView("/login");
    }

    @GetMapping ("/users/{id}")
        public String dataUsers(@PathVariable Integer id , Model model ){
        Optional <ApplicationUser> applicationUser = applicationUserRepository.findById(id);

        Optional<Post> posts=  postRepository.findById(id);
        model.addAttribute("posts", posts);
        model.addAttribute("username", applicationUser.get().getUsername());
        model.addAttribute("firstName", applicationUser.get().getFirstName());
        model.addAttribute("lastName", applicationUser.get().getLastName());
        model.addAttribute("dateOfBirth", applicationUser.get().getDateOfBirth());
        model.addAttribute("bio", applicationUser.get().getBio());
        System.out.println(applicationUser);
        return "user.html";
    }



    @GetMapping ("/users")
    public String dataUser(Principal principal, Model model ){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        List<Post> posts =  PostRepository.findPostById(user.getId());
        System.out.println(user.getFirstName());
        model.addAttribute("user",user);
        model.addAttribute("posts", posts);
        return "user.html";

    }
//    @PostMapping("/follow")
//    public RedirectView addUserIFollow(@PathVariable("id") int id, Principal principal){
//        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
//        ApplicationUser user_id = applicationUserRepository.findById(id).get();
//        user.getUserFollowing().add(user_id);
//        user_id.getFollowUser().add(user);
//        applicationUserRepository.save(user_id);
//        applicationUserRepository.save(user);
//
//        return new RedirectView("/feed");
//    }
//
//    @GetMapping("/feed")
//    public String getMyFeedPage(Model m, Principal principal){
//        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
//        List<ApplicationUser> followUser = user.getFollowUser();
//        m.addAttribute("followUser", followUser);
//        m.addAttribute("user", user.getUsername());
//        return "feed.html";
//    }
//
//    @GetMapping("/error")
    @ResponseBody
    public String error (){
        return "Error Go Back";
    }


}
