package eu.przysucha.placesnearby;

import eu.przysucha.placesnearby.model.Role;
import eu.przysucha.placesnearby.model.RoleEnum;
import eu.przysucha.placesnearby.model.User;
import eu.przysucha.placesnearby.repo.RoleRepository;
import eu.przysucha.placesnearby.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static eu.przysucha.placesnearby.model.RoleEnum.ROLE_ADMIN;
import static eu.przysucha.placesnearby.model.RoleEnum.ROLE_USER;

@Component
public class Start {

    RoleRepository roleRepository;
    UserRepository userRepository;

    @Autowired
    public Start(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init () {
        Role admin = new Role(ROLE_ADMIN);
        Role ruser = new Role(ROLE_USER);
        roleRepository.save(admin);
        roleRepository.save(ruser);


        Set<Role> roles = new HashSet<>();
        Role role1 = new Role();
        role1.setName(ROLE_ADMIN);
        Role role2 = new Role();
        role2.setName(ROLE_USER);

        roles.add(admin);
        roles.add(ruser);

        User user = new User("Jan", "jdd@dgmgal.de", "$2a$10$m0noy0Qx4nwjyxNs8cVZS.bW6rhnBbsSRxkgiMlPvC/swqL9bvyPm");
        user.setRoles(roles);

        userRepository.save(user);
    }
}
