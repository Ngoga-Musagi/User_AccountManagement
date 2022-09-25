package com.userAccount.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.userAccount.model.User;
import com.userAccount.repository.UserRepository;
import com.userAccount.utility.UserAccountUtils;

@Controller
@RequestMapping("/app")
@CrossOrigin
public class UserController {
	
	
     @Autowired
	 private UserRepository repo;
     
     @Autowired
     private UserAccountUtils util;
	@GetMapping("")
	public String viewHomePage() {
		
		return "index";
	}
	
	@GetMapping("/register")
	public String signUp(Model model) {
		
		model.addAttribute("user", new User());
		return "signup_form";
	}
	
	@GetMapping("/reset")
	public String reset(Model model, User user) {
		
		model.addAttribute("user", user);
		return "reset_form";
	}
	@GetMapping("/verify")
	public String verify(Model model,User user) {
		model.addAttribute("user", user);
		return "verification_form";
	}
	
	@PostMapping("/process_register")
	public String processRegistration(User user,Model model) {
		
		if(util.isPasswordValid(user.getPassword())) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword=encoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			repo.save(user);
			return "register_success";
		} else {
			model.addAttribute("error", util.getMessage());
			return "signup_form";
		}
		
		
	}
	@PostMapping("/reset_password")
	public String resetPassword(User user) {
		
		//System.out.println(user.getEmail()+ " "+ user.getPassword());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword=encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		repo.updateUserPass(user.getPassword(), user.getEmail());
		return "register_success";
		
	}
	
	
	
	@GetMapping("/list_users")
	public String viewUserList(Model model) {
		
		List<User> listUser =repo.findAll();
		model.addAttribute("listUsers", listUser);
		return "users";
	}
	
	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

//    @GetMapping("/uploadimage") public String displayUploadForm() {
//        return "imageupload/index";
//    }

    @PostMapping("/upload") public String uploadImage(Model model, @RequestParam("image") MultipartFile file, User user, @RequestParam(name = "nid") String nid) throws IOException {
        
    	//System.out.println(user.getEmail()+" "+ nid +" ");
    	try {
    	StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        
        Files.write(fileNameAndPath, file.getBytes());
//        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
         //System.out.println(fileNameAndPath.toString());
        user.setPhoto(fileNameAndPath.toString());
        user.setNid(nid);
        user.setStatus("VERIFIED");
        repo.verifyAccount(user.getPhoto(),user.getNid(),user.getStatus(), user.getEmail());
        return "users";
    	}catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error";
		}
       
    }
	
}
