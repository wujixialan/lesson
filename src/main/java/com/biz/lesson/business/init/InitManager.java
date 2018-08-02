package com.biz.lesson.business.init;

import com.biz.lesson.dao.config.SystemPropertyRepository;
import com.biz.lesson.dao.stu.ClazzRepository;
import com.biz.lesson.dao.stu.LessionRepository;
import com.biz.lesson.dao.stu.StuRepository;
import com.biz.lesson.dao.user.*;
import com.biz.lesson.model.config.SystemProperty;
import com.biz.lesson.model.stu.Clazz;
import com.biz.lesson.model.stu.Lession;
import com.biz.lesson.model.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;

/**
 * 项目初始化数据使用
 */
@Service
public class InitManager {

    private static final Logger logger = LoggerFactory.getLogger(InitManager.class);

    @Autowired
    private MainMenuRepository mainMenuRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private SystemPropertyRepository propertyRepository;

    @Autowired
    private StuRepository repository;

    @Autowired
    private ClazzRepository clazzRepository;
    @Qualifier("lessionRepository")
    @Autowired
    private LessionRepository lessionRepository;


    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            logger.info("开始初始化系统基础数据...");

            //User
            User user = new User();
            user.setUserId("admin");
            user.setName("超级管理员");
            user.setNameEn("SuperAdmin");
            user.setPassword("ceb4f32325eda6142bd65215f4c0f371");
            user = userRepository.save(user);

            //Main menu
            MainMenu mainMenu = new MainMenu();
            mainMenu.setName("系统信息");
            mainMenu.setNameEn("System Info");
            mainMenu.setIcon("fa fa-cogs");
            mainMenu.setCode(999);
            mainMenu = mainMenuRepository.save(mainMenu);

            //学籍管理
            MainMenu stuMenu = new MainMenu();
            stuMenu.setName("学籍管理");
            stuMenu.setNameEn("Student Manager");
            stuMenu.setIcon("fa fa-cogs");
            stuMenu.setCode(888);
            stuMenu = mainMenuRepository.save(stuMenu);

            //Menu Item
            MenuItem menuItemOfUser = buildMenuItem("用户管理", "User Management", "ROLE_USER;OPT_USER_LIST", 1, "/manage/user/list.do", mainMenu);
            menuItemOfUser = menuItemRepository.save(menuItemOfUser);

            MenuItem menuItemOfMainMenu = buildMenuItem("菜单管理", "Main Menu", "ROLE_MAINMENU;OPT_MAINMENU_LIST;ROLE_MENUITEM;ROLE_RESOURCE", 2, "/manage/mainMenus.do", mainMenu);
            menuItemOfMainMenu = menuItemRepository.save(menuItemOfMainMenu);

            MenuItem menuItemOfRole = buildMenuItem("角色管理", "Role Management", "ROLE_ROLE;OPT_ROLE_LIST", 3, "/manage/roles.do", mainMenu);
            menuItemOfRole = menuItemRepository.save(menuItemOfRole);

            MenuItem menuItemConfig = buildMenuItem("配置管理", "Config", "ROLE_CONFIG,OPT_CONFIG_LIST,OPT_CONFIG_DELETE,OPT_CONFIG_ADD,OPT_CONFIG_EDIT", 4, "/manage/config/list.do", mainMenu);
            menuItemConfig = menuItemRepository.save(menuItemConfig);

            MenuItem menuItemAccessLog = buildMenuItem("访问日志", "AccessLog", "OPT_ACCESSLOG_SEARCH", 4, "/manage/accesslog/search.do", mainMenu);
            menuItemAccessLog = menuItemRepository.save(menuItemAccessLog);

            MenuItem stuMenuItem = buildMenuItem("学生信息", "Student Manager", "Student_Manager", 4, "/stu/to/0/list.do", stuMenu);
            stuMenuItem = menuItemRepository.save(stuMenuItem);

            MenuItem clazzMenuItem = buildMenuItem("班级管理", "Clazz Manager", "Clazz_Manager", 4, "/clazz/to/0/list.do", stuMenu);
            clazzMenuItem = menuItemRepository.save(clazzMenuItem);

            MenuItem lessionMenuItem = buildMenuItem("选课管理", "Lession Manager", "Lession_Manager", 4, "/lession/to/0/list.do", stuMenu);
            lessionMenuItem = menuItemRepository.save(lessionMenuItem);


            //Resource
            Resource manageUser = builtResource("用户管理", "User Management", "OPT_USER_ADD;OPT_USER_EDIT;OPT_USER_DELETE;OPT_USER_RESET;OPT_USER_DETAIL", menuItemOfUser);
            manageUser = resourceRepository.save(manageUser);

            Resource checkUserDetail = builtResource("查看用户", "User Read", "OPT_USER_DETAIL", menuItemOfUser);
            checkUserDetail = resourceRepository.save(checkUserDetail);

            Resource manageMenuItem = builtResource("菜单管理", "Menu Management", "OPT_MAINMENU_ADD;OPT_MAINMENU_EDIT;OPT_MAINMENU_DELETE;OPT_MAINMENU_DETAIL;OPT_MENUITEM_ADD;OPT_MENUITEM_EDIT;OPT_MENUITEM_DELETE;OPT_MENUITEM_DETAIL;OPT_RESOURCE_ADD;OPT_RESOURCE_EDIT;OPT_RESOURCE_DELETE", menuItemOfMainMenu);
            manageMenuItem = resourceRepository.save(manageMenuItem);

            Resource checkMainMenu = builtResource("菜单查看", "Main Menu Management", "OPT_MAINMENU_DETAIL;OPT_MENUITEM_DETAIL", menuItemOfMainMenu);
            checkMainMenu = resourceRepository.save(checkMainMenu);

            Resource manageRole = builtResource("角色管理", "Role Management", "OPT_ROLE_ADD;OPT_ROLE_EDIT;OPT_ROLE_DELETE;OPT_ROLE_DETAIL", menuItemOfRole);
            manageRole = resourceRepository.save(manageRole);

            Resource checkRoleDetail = builtResource("角色查看", "Role Read", "OPT_ROLE_DETAIL", menuItemOfRole);
            checkRoleDetail = resourceRepository.save(checkRoleDetail);

            Resource manageConfig = builtResource("配置管理", "Config Management", "ROLE_CONFIG,OPT_CONFIG_LIST,OPT_CONFIG_DELETE,OPT_CONFIG_ADD,OPT_CONFIG_EDIT", menuItemConfig);
            manageConfig = resourceRepository.save(manageConfig);

            Resource stuManage = builtResource("学生信息", "Student Info", "STU_ADD,STU_MODIFY,STU_DELETE,STU_FIND", stuMenuItem);
            stuManage = resourceRepository.save(stuManage);

            Resource clazzManage = builtResource("班级管理", "Clazz Info", "STU_ADD,STU_MODIFY,STU_DELETE,STU_FIND", stuMenuItem);
            clazzManage = resourceRepository.save(clazzManage);

            Resource lessionManage = builtResource("选课管理", "Lession Info", "STU_ADD,STU_MODIFY,STU_DELETE,STU_FIND", stuMenuItem);
            clazzManage = resourceRepository.save(clazzManage);


            //Role
            Role role = new Role();
            role.setName("超级管理员");
            role.setNameEn("Super Admin");
            role.setDescription("拥有菜单管理，角色管理，用户管理权限, 学籍管理  ");
            role.setMenuItems(newArrayList(menuItemOfUser, menuItemOfMainMenu, menuItemOfRole, menuItemConfig, menuItemAccessLog, stuMenuItem, clazzMenuItem, lessionMenuItem));
            role.setResources(newArrayList(manageUser, checkMainMenu, manageMenuItem, checkUserDetail, manageRole, checkRoleDetail, manageConfig, stuManage));
            role = roleRepository.save(role);

            user.setRoles(newArrayList(role));
            userRepository.save(user);


            propertyRepository.save(new SystemProperty("access_log_save_sync", "true", true, "是否同步存储访问日志"));
            propertyRepository.save(new SystemProperty("company_name", "博智信息", true, "界面中使用的公司名称"));
            propertyRepository.save(new SystemProperty("domain.cn", "lesson.biz-united.com.cn", true, "访问此系统使用的域名"));
            propertyRepository.save(new SystemProperty("system_logo", "", true, "系统LOGO"));
            propertyRepository.save(new SystemProperty("system_title", "LESSON SIX", true, "系统名称"));


            /**
             * 保存班级信息。
             */
            List<Clazz> clazzes = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                Clazz clazz = new Clazz();
                clazz.setClazzName("软件140" + i);
                clazz.setScore(0.0F);
                clazz.setPerNum(0);
                clazzes.add(clazz);
            }
            clazzRepository.save(clazzes);

            /**
             * 保存课程信息
             */
            List<Lession> lessions = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                Lession lession = new Lession();
                lession.setSubName("数学" + i);
                lession.setScore(0.0F);
                lession.setPerNum(0);
                lessions.add(lession);
            }
            lessionRepository.save(lessions);
            logger.info("完成初始化系统基础数据...");
        }
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    private MenuItem buildMenuItem(String name, String nameEn, String symbol, Integer code, String link, MainMenu mainMenu) {
        MenuItem menuItemOfRole = new MenuItem();
        menuItemOfRole.setName(name);
        menuItemOfRole.setNameEn(nameEn);
        menuItemOfRole.setSymbol(symbol);
        menuItemOfRole.setCode(code);
        menuItemOfRole.setLink(link);
        menuItemOfRole.setMainMenu(mainMenu);
        return menuItemOfRole;
    }

    private Resource builtResource(String name, String nameEn, String symbol, MenuItem menuItemOfUser) {
        Resource resource = new Resource();
        resource.setName(name);
        resource.setNameEn(nameEn);
        resource.setSymbol(symbol);
        resource.setMenuItem(menuItemOfUser);
        return resource;
    }
}
