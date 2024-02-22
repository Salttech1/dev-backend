//package kraheja.commons.config;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import kraheja.commons.entity.MenuMaster;
//import kraheja.commons.entity.Privilege;
//import kraheja.commons.entity.Roles;
//import kraheja.commons.entity.Users;
//import kraheja.commons.repository.MenuMasterRepository;
//import kraheja.commons.repository.PrivilegeRepository;
//import kraheja.commons.repository.RolesRepository;
//import kraheja.commons.repository.UserRepository;
//
//@Component
//public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
//
//    boolean alreadySetup = true;
//
//    private final UserRepository userRepository;
//    private final RolesRepository rolesRepository;
//    private final PrivilegeRepository privilegeRepository;
////    private final MenuMasterRepository menuMasterRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public SetupDataLoader(UserRepository userRepository, RolesRepository rolesRepository, PrivilegeRepository privilegeRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.rolesRepository = rolesRepository;
//        this.privilegeRepository = privilegeRepository;
////        this.menuMasterRepository = menuMasterRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    @Transactional
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        if (alreadySetup) {
//            return;
//        }
//
//        // == create initial menus
////        final MenuMaster fdMenu = createMenuIfNotFound("FD", "Fixed Deposits", "fd/index.html", 1);
////        final MenuMaster tdsMenu = createMenuIfNotFound("TDS", "Tax", "tds/index.html", 2);
//
//        // == create initial privileges
//        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
//        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
//        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
//
//        // == create initial roles
//        final List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
//        final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));
////        final Roles adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges, Arrays.asList(fdMenu, tdsMenu));
////        createRoleIfNotFound("ROLE_USER", userPrivileges, Arrays.asList(fdMenu));
//
//        // == create initial user
//        createUserIfNotFound("XEDP", "edp", Arrays.asList(adminRole));
//
//        alreadySetup = true;
//    }
//
////    @Transactional
////    private MenuMaster createMenuIfNotFound(String name, String desc, String routeUrl, int sortOrder) {
////        Optional<MenuMaster> menu = menuMasterRepository.findByName(name);
////        if (!menu.isPresent()) {
////            menu = Optional.of(new MenuMaster(name, desc, routeUrl, sortOrder));
////        }
////        menu = Optional.of(menuMasterRepository.save(menu.get()));
////        return menu.get();
////    }
//
//    @Transactional
//    Privilege createPrivilegeIfNotFound(final String name) {
//        Optional<Privilege> privilege = privilegeRepository.findByName(name);
//        if (privilege.isPresent()) {
//            return privilege.get();
//        }
//        Privilege newPrivilege = privilegeRepository.save(new Privilege(name));
//        return newPrivilege;
//    }
//
//    @Transactional
//    Roles createRoleIfNotFound(final String name, final Collection<Privilege> privileges, Collection<MenuMaster> menus) {
//        Optional<Roles> role = rolesRepository.findByName(name);
//        if (!role.isPresent()) {
//            role = Optional.of(new Roles(name, privileges, menus));
//        }
//        role.get().setPrivileges(privileges);
//        role.get().setMenus(menus);
//        role = Optional.of(rolesRepository.save(role.get()));
//        return role.get();
//    }
//
//    @Transactional
//    Users createUserIfNotFound(final String username, final String password, final Collection<Roles> roles) {
//        Optional<Users> user = userRepository.findByUsername(username);
//        if (!user.isPresent()) {
//            user = Optional.of(new Users(username, passwordEncoder.encode(password), true, true, true, true, roles, null));
//        }
//        user.get().setRoles(roles);
//        user = Optional.of(userRepository.save(user.get()));
//        return user.get();
//    }
//}
