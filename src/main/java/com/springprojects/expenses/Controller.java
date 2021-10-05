package com.springprojects.expenses;

import org.springframework.boot.Banner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;

@org.springframework.stereotype.Controller
public class Controller {
    ApplicationContext context =
            new AnnotationConfigApplicationContext(com.springprojects.expenses.ApplicationContext.class);

    @RequestMapping("/")
    public String showTable(Model model){

        Table table = context.getBean("table", Table.class);
        model.addAttribute("table", table);

        return "table";
    }

    @RequestMapping("/new")
    public String newForm(Model model) {
        Entry newEntry = new Entry();

        model.addAttribute("new_entry", newEntry);
        Table table = context.getBean("table", Table.class);

        Integer id = table.addReservedID();
        model.addAttribute("id", id);
        newEntry.setID(id);

        System.out.println("Generated ID "+id);
        return "new_entry";
    }

    @RequestMapping("/edit")
    public String newForm(Model model, @RequestParam("id") Integer id) {
        Table table = context.getBean("table", Table.class);
        Entry updateEntry = table.getEntries().get(id);
        model.addAttribute("new_entry",updateEntry);
        model.addAttribute("id", id);
        return "new_entry";
    }

    @RequestMapping("/validate")
    public String validate(Model model,
                           @Valid @ModelAttribute("new_entry") Entry entry,
                           BindingResult result,
                           @RequestParam("id") Integer id) {

        System.out.println("Validating ID = "+id);
        System.out.println("Entry ID = "+entry.getID());
        entry.setDate();

        if (result.hasErrors()){
            System.out.println("Errors in validation");
            System.out.println(result.getAllErrors());
            return "new_entry";
        }

        Table table = context.getBean("table", Table.class);
        table.put(id, entry);

        try{
            System.out.println("SAVING");
            table.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("table", table);
        return "table";
    }
}
