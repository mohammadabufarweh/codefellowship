package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
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
        public String dataUsers(@PathVariable Integer id , Model model,Principal principal ){
        ApplicationUser user = applicationUserRepository.findById(id).get();
        ApplicationUser currentUser = applicationUserRepository.findByUsername(principal.getName());
        List<Post> posts =  PostRepository.findPostById(user.getId());
        model.addAttribute("profile", user);
        model.addAttribute("user", currentUser);
        model.addAttribute("posts", posts);
//        Optional <ApplicationUser> applicationUser = applicationUserRepository.findById(id);
//        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
//        List<Post> posts =  PostRepository.findPostById(user.getId());
//        model.addAttribute("posts", posts);
//        model.addAttribute("username", applicationUser.get().getUsername());
//        model.addAttribute("firstName", applicationUser.get().getFirstName());
//        model.addAttribute("lastName", applicationUser.get().getLastName());
//        model.addAttribute("dateOfBirth", applicationUser.get().getDateOfBirth());
//        model.addAttribute("bio", applicationUser.get().getBio());
//        System.out.println(user.id+"ssssssssssss");
        return "eachuser.html";
    }


    @GetMapping ("/users")
    public String dataUser(Principal principal, Model model ){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        List<Post> posts =  PostRepository.findPostById(user.getId());
        model.addAttribute("user",user);
        model.addAttribute("posts", posts);
        return "user.html";

    }
    @GetMapping ("/allprofiles")
    public String allprofiles(Principal principal, Model model ){
        List<ApplicationUser> users = (List<ApplicationUser>) applicationUserRepository.findAll();
        ApplicationUser currentUser = applicationUserRepository.findByUsername(principal.getName());
        List<Post> posts =  PostRepository.findPostById(currentUser.getId());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", users);
        model.addAttribute("posts", posts);
        return "allprofiles.html";

    }
    @PostMapping("/follow/{id}")
    public RedirectView addFollow(@PathVariable("id") int id, Principal principal){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        ApplicationUser user_id = applicationUserRepository.findById(id).get();
        user.getUserFollowing().add(user_id);
        user_id.getFollowUser().add(user);
        applicationUserRepository.save(user_id);
        applicationUserRepository.save(user);
        System.out.println(user.getFirstName().toString()+"222222222222221111111111111111111111");
        return new RedirectView("/feed");
    }

    @GetMapping("/feed")
    public String feedPage(Model m, Principal principal){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        List<ApplicationUser> followUser = user.getFollowUser();
        m.addAttribute("followUser", followUser);
        m.addAttribute("user", user);
        System.out.println("sss"+user.followUser);
        System.out.println(followUser+"ssssssssssssssssssssssssssssssss");
        return "feed.html";
    }

    @GetMapping("/error")
    @ResponseBody
    public String error (){
        return "Error Please Login ";
    }


}
