package com.mtraidi.demo;

import com.mtraidi.demo.dao.IAuthority;
import com.mtraidi.demo.dao.IUser;
import com.mtraidi.demo.models.Authority;
import com.mtraidi.demo.models.Role;
import com.mtraidi.demo.models.User;
import com.mtraidi.demo.utils.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class FullApplication implements CommandLineRunner {

    @Autowired
    private StorageService storage;

    @Autowired
    private IUser iUser;

    @Autowired
    private IAuthority iAuthority;


    public static void main(String[] args) {
        SpringApplication.run(FullApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //storage.init();


        // TODO: ADMIN REGISTER
/*        Authority authority = new Authority();
        authority.setName(Role.ROLE_ADMIN.name());
        iAuthority.save(authority);
        User user = new User();
        user.setFirstName("Mahdi");
        user.setLastName("Traidi");
        user.setUsername("admin_scolarite");
        user.setPassword(hash("admin111"));
        user.setRole(Role.ROLE_ADMIN);
        user.setEnabled(true);
        user.setAuthorities(authority);
        iUser.save(user);*/
//        // TODO:Parent REGISTER
     /*   Authority authority2 = new Authority();
        authority2.setName(Role.ROLE_USER.name());
        iAuthority.save(authority2);
        User user2 = new User();
        user2.setFirstName("Jhon");
        user2.setLastName("Hmida");
        user2.setUsername("user");
        user2.setPassword(hash("user"));
        user2.setRole(Role.ROLE_USER);
        user2.setEnabled(true);
        user2.setAuthorities(authority2);
        iUser.save(user2);*/
    }

    String hash(String password) {


        String hashedPassword = null;
        int i = 0;
        while (i < 5) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            hashedPassword = passwordEncoder.encode(password);
            i++;
        }

        return hashedPassword;
    }
}
