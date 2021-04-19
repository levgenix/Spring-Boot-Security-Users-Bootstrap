package ru.itsinfo.springbootsecurityusersbootstrap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itsinfo.springbootsecurityusersbootstrap.model.User;
import ru.itsinfo.springbootsecurityusersbootstrap.service.AppService;

import javax.validation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AppService appService;

    @Autowired
    public AdminController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping({"", "list"})
    public String getAllUsers(Model model) {
        model.addAttribute("users", appService.findAllUsers());
        model.addAttribute("allRoles", appService.findAllRoles());

        model.addAttribute("showUserProfile", model.containsAttribute("user"));

        return "admin-page";
    }

    @PatchMapping()
    public String update(@Valid @ModelAttribute("user") User user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        appService.updateUser(user, bindingResult, redirectAttributes);

        return "redirect:/admin";
    }

    @GetMapping("/{id}/profile")
    public String showUserProfileModal(@PathVariable("id") Long userId, Model model, RedirectAttributes attributes) {
        try {
            model.addAttribute("allRoles", appService.findAllRoles());
            model.addAttribute("user", appService.findUser(userId));
            return "fragments/user-form";
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("")
    public String deleteUser(@ModelAttribute("user") User user) {
        appService.deleteUser(user.getId());
        return "redirect:/admin";
    }

    /*@GetMapping(value = "/new")
    public String addUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles", appService.findAllRoles());
        return "user-form";
    }*/

    /*@GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable(value = "id", required = true) Long userId, Model model) {
        try {
            model.addAttribute("user", appService.findUser(userId));
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return "redirect:/admin";
        }
        model.addAttribute("allRoles", appService.findAllRoles());
        return "user-form";
    }*/

    /*@PostMapping("/addNew")
    public String addNew(User user) {
        appService.addNew(user); //appService.update(user)
        return "redirect:/admin";
    }*/

    /*@PostMapping()
    public String saveOrUpdateUser(@Valid @ModelAttribute("user") User user,
                                   BindingResult bindingResult, Model model,
                                   RedirectAttributes attributes) {
        *//*try {
            return appService.saveUser(user, bindingResult, model) ? "redirect:/admin" : "user-form";
        } catch (AssertionFailure | UnexpectedRollbackException e) {
            return "user-form";
        }*//*
        try {
            //return appService.saveUser(user, bindingResult, model) ? "redirect:/admin" : "admin-page";

            if (!appService.saveUser(user, bindingResult, model)) {
                attributes.addFlashAttribute("badUser", user);
            }
            //return "redirect:/admin";
        } catch (AssertionFailure | UnexpectedRollbackException e) {
            //return "redirect:/admin";
        }
        return "redirect:/admin";
    }*/

    /*@ModelAttribute("badUser")
    public User setBadUser(User user) {
        return user;
    }*/
}
