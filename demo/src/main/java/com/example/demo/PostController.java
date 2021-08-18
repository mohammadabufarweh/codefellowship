package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class PostController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;



    @GetMapping("/post")
    public String postPage(Principal principal, Model model){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
            model.addAttribute("user", user);
            model.addAttribute("userId", user.getId());
            return "post.html";


    }
    @PostMapping("/post")
    public RedirectView getPostData(Integer userId, String body ){
        ApplicationUser applicationUser = applicationUserRepository.findById(userId).get();
        Post posts = new Post(body, applicationUser);
        postRepository.save(posts);
        return new RedirectView("/users");

    }

}
