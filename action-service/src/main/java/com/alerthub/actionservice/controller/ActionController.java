package com.alerthub.actionservice.controller;

import com.alerthub.actionservice.entity.Action;
import com.alerthub.actionservice.service.ActionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actions")
public class ActionController {

    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping
    public List<Action> getAllActions() {
        return actionService.getAllActions();
    }

    @GetMapping("/{id}")
    public Action getActionById(@PathVariable Long id) {
        return actionService.getActionById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Action> getActionsByUserId(@PathVariable Long userId) {
        return actionService.getActionsByUserId(userId);
    }

    @PostMapping
    public Action createAction(@Valid @RequestBody Action action) {
        return actionService.createAction(action);
    }

    @PutMapping("/{id}")
    public Action updateAction(@PathVariable Long id, @Valid @RequestBody Action action) {
        return actionService.updateAction(id, action);
    }

    @PatchMapping("/{id}/enable")
    public Action enableAction(@PathVariable Long id) {
        return actionService.enableAction(id);
    }

    @PatchMapping("/{id}/disable")
    public Action disableAction(@PathVariable Long id) {
        return actionService.disableAction(id);
    }

    @DeleteMapping("/{id}")
    public String deleteAction(@PathVariable Long id) {
        actionService.deleteAction(id);
        return "Action deleted successfully";
    }
}