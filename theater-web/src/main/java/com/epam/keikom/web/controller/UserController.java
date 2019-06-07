package com.epam.keikom.web.controller;

import com.epam.keikom.dao.domain.User;
import com.epam.keikom.dao.exceptions.UnknownIdentifierException;
import com.epam.keikom.service.IAuditoriumService;
import com.epam.keikom.service.IUserService;
import com.epam.keikom.web.converter.DtoToUser;
import com.epam.keikom.web.converter.UserToDTO;
import com.epam.keikom.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userService")
    private IUserService userService;

    @Autowired
    private IAuditoriumService auditoriumService;

    @Autowired
    private DtoToUser dtoToUser;

    @Autowired
    private UserToDTO userToDTO;

    @RequestMapping("/usersList")
    public ModelAndView getUsers(final Model model) {

        final List<User> users = new ArrayList<>(userService.getAll());
        final List<UserDTO> dtoList = new ArrayList<>();

        for (final User user:users){
            final UserDTO dto = userToDTO.apply(user);
            dtoList.add(dto);
        }

        return new ModelAndView("usersList", "users", dtoList);
    }

    @RequestMapping(value = "/add")
    public ModelAndView showForm() {

        return new ModelAndView("addUser", "user", new UserDTO());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute(value = "user") final UserDTO userDTO) {

        final User user = dtoToUser.apply(userDTO);
        userService.save(user);

        return "redirect:/user/usersList";
    }

    @RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") final Long id) throws UnknownIdentifierException {

        final User user = userService.getById(id);
        userService.remove(user);

        return "redirect:/user/usersList";

    }

    @RequestMapping("/update/{id}")
    public ModelAndView updateUser(@PathVariable("id") final Long id) throws UnknownIdentifierException {

        final UserDTO userDTO = userToDTO.apply(userService.getById(id));

        return new ModelAndView("editUser", "user", userDTO);
    }

    @RequestMapping("/showTickets/{id}")
    public ModelAndView showTickets(@PathVariable("id") final Long id) {

        return new ModelAndView("userTickets", "tickets", userService.getTicketFromByUser(id));

    }
}
