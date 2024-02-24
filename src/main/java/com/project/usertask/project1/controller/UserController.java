package com.project.usertask.project1.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.usertask.project1.entities.Task;
import com.project.usertask.project1.entities.User;
import com.project.usertask.project1.repository.TaskRepository;
import com.project.usertask.project1.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		User user = userRepository.getUserByUserName(userName);
		model.addAttribute("user",user);
	}

    @GetMapping("/index")
    public String dashboard(Model model){
        model.addAttribute("title", "Dashboard");
        return "userindex";
    }

    @GetMapping("/add-task")
    public String addTask(Model model, Task task){
        model.addAttribute("title", "Add Task");
        model.addAttribute("task", new Task());
        return "add_task";
    }

    @PostMapping("/process-task")
    public String processTask(@ModelAttribute Task task, Principal principal, HttpSession session){
        String name  = principal.getName();
        User user = userRepository.getUserByUserName(name);
        task.setUser(user);
        user.getTasks().add(task);
        userRepository.save(user);
        return "redirect:/user/add-task";
    }

    @GetMapping("/show-tasks")
    public String showTasks(Principal principal, Model model){

        String name = principal.getName();
        User user = userRepository.getUserByUserName(name);
        List<Task> tasks = user.getTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("title", "Show User Tasks");
        return "show_tasks";
    }

    @GetMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable("id") int id,Model model, Principal principal){
        String name = principal.getName();
        User user = userRepository.getUserByUserName(name);
        Task task = taskRepository.findById(id).get();
        if (user.getId() == task.getUser().getId()) {
            user.getTasks().remove(task);
            userRepository.save(user);
        }
        return "redirect:/user/show-tasks";
    }

    @PostMapping("/update-task/{id}")
    public String updateTask(@PathVariable int id, Model model){
        Task task = taskRepository.findById(id).get();
        model.addAttribute("task", task);
        return "update_task";
    }

    @PostMapping("/process-update")
    public String processUpdate(@ModelAttribute Task task, Principal principal,Model model){
        User user = userRepository.getUserByUserName(principal.getName());
        task.setUser(user);
        taskRepository.save(task);
        return "redirect:/user/show-tasks";
    }


    
}