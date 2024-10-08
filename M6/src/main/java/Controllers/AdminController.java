package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Domain.Entities.Admin;
import Domain.Services.Impl.AdminService;




@RestController
@RequestMapping("/api/auth")
public class AdminController {
 
    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Admin> create(@RequestBody Admin admin) {
        try {
            Admin newAdmin = adminService.create(admin);
            return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

/*     @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password){
        try{

            emailPasswordAuthentificationToken = new emailPasswordAuthentificationToken (email, password);
            
            Authentication authentication = 
        }catch{

        }
    } */
}
