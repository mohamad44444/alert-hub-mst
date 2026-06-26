package com.alerthub.actionservice.service;

import com.alerthub.actionservice.entity.Action;
import com.alerthub.actionservice.repository.ActionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionService {

    private final ActionRepository actionRepository;

    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public List<Action> getAllActions() {
        return actionRepository.findByIsDeletedFalse();
    }

    public Action getActionById(Long id) {
        return actionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Action not found with id: " + id));
    }

    public List<Action> getActionsByUserId(Long userId) {
        return actionRepository.findByUserIdAndIsDeletedFalse(userId);
    }

    public Action createAction(Action action) {
        return actionRepository.save(action);
    }

    public Action updateAction(Long id, Action updatedAction) {
        Action existingAction = getActionById(id);

        existingAction.setUserId(updatedAction.getUserId());
        existingAction.setName(updatedAction.getName());
        existingAction.setConditionJson(updatedAction.getConditionJson());
        existingAction.setTo(updatedAction.getTo());
        existingAction.setActionType(updatedAction.getActionType());
        existingAction.setRunOnTime(updatedAction.getRunOnTime());
        existingAction.setRunOnDay(updatedAction.getRunOnDay());
        existingAction.setMessage(updatedAction.getMessage());
        existingAction.setIsEnabled(updatedAction.getIsEnabled());

        return actionRepository.save(existingAction);
    }

    public Action enableAction(Long id) {
        Action action = getActionById(id);
        action.setIsEnabled(true);
        return actionRepository.save(action);
    }

    public Action disableAction(Long id) {
        Action action = getActionById(id);
        action.setIsEnabled(false);
        return actionRepository.save(action);
    }

    public void deleteAction(Long id) {
        Action action = getActionById(id);

        /*
         * Soft delete:
         * לפי המסמך לא מוחקים באמת את ה־Action מה־DB.
         */
        action.setIsDeleted(true);
        actionRepository.save(action);
    }
}