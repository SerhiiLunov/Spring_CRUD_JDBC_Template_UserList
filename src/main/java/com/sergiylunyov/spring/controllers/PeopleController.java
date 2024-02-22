package com.sergiylunyov.spring.controllers;

import com.sergiylunyov.spring.Models.Person;
import com.sergiylunyov.spring.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
//    створюємо обʼєкт, одночасно додаючи його в модел, викликаємо вʼю, де назначаємо дані цьому обʼєкту
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
//   присвоюємо створеному в методі обʼєкту значення обʼєкта з форми html (на цьому єтапі відбувається валідація) та додаємо в model
//   все це за допомогою @ModelAttribute
//   методом save додаємо обʼєкт person в бд
//   робимо redirect на страницю з усіма обʼєктами (/people)
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        // беремо значення id, додаємо обʼєкт з таким id в модел
        // викликаємо вʼю, де змінюємо дані цього обʼєкта
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        // беремо значення id, додаємо обʼєкт з таким id в моде
        // викликаємо вʼю, де змінюємо дані цього обʼєкта
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.replace(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        // беремо значення id, додаємо обʼєкт з таким id в моде
        // викликаємо вʼю, де змінюємо дані цього обʼєкта
        personDAO.delete(id);
        return "redirect:/people";
    }
}
