//package kraheja.commons.controller;
//
//import java.util.Optional;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import kraheja.commons.entity.MenuMaster;
//import kraheja.commons.repository.MenuMasterRepository;
//import kraheja.commons.service.MenuMasterService;
//import kraheja.fd.deposit.bean.request.MenuRequestBean;
//
//@RestController
//@RequestMapping("/menus")
//public class MenuMasterController {
//
//    private static final Logger logger = LoggerFactory.getLogger(MenuMasterController.class);
//
//    private final MenuMasterRepository menuMasterRepository;
//    
//    private final MenuMasterService menuMasterService;
//
//    @Autowired
//    public MenuMasterController(MenuMasterRepository menuMasterRepository, MenuMasterService menuMasterService) {
//        this.menuMasterRepository = menuMasterRepository;
//        this.menuMasterService = menuMasterService;
//    }
//
//    @GetMapping
//    public ResponseEntity<?> getMenus(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size) {
//    	return this.menuMasterService.fetchMenu();
//    }
//
//    @GetMapping(value = "/{id}")
//    public ResponseEntity<?> getMenu(@PathVariable long id) {
//        Optional<MenuMaster> menu = menuMasterRepository.findById(id);
//        if (menu.isPresent()) {
//            return ResponseEntity.ok(menu.get());
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @PostMapping
//    public ResponseEntity<?> addMenu(MenuRequestBean menuRequestBean) {
//        return this.menuMasterService.addMenu(menuRequestBean);
//    }
//    
//    @PutMapping
//    public ResponseEntity<?> putMenu(MenuRequestBean menuRequestBean) {
//        return this.menuMasterService.updateMenu(menuRequestBean);
//    }
//}
