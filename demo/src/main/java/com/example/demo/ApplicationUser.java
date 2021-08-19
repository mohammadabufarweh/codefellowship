package com.example.demo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;


    @Column(unique = true)
    private String username;

    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String bio;

    @OneToMany(mappedBy = "applicationUser")
    List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }


    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "follow",
            joinColumns = @JoinColumn(name = "followUser_Id"),
            inverseJoinColumns = @JoinColumn(name = "userFollowing_Id"))
    List<ApplicationUser> followUser;


    @ManyToMany(mappedBy = "followUser")
    List<ApplicationUser> userFollowing;

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<ApplicationUser> getFollowUser() {
        return followUser;
    }

    public void setFollowUser(List<ApplicationUser> followUser) {
        this.followUser = followUser;
    }

    public List<ApplicationUser> getUserFollowing() {
        return userFollowing;
    }

    public void setUserFollowing(List<ApplicationUser> userFollowing) {
        this.userFollowing = userFollowing;
    }

    public ApplicationUser(){

    }

    public ApplicationUser(String username, String password, String firstName, String lastName,String dateOfBirth, String bio) {
        this.username=username;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.dateOfBirth=dateOfBirth;
        this.bio=bio;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBio() {
        return bio;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void addFollower(ApplicationUser applicationUser){
        followUser.add(applicationUser);
    }
    public Integer getId() {
        return id;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
