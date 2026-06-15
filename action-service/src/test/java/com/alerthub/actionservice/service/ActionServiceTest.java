package com.alerthub.actionservice.service;

import com.alerthub.actionservice.entity.Action;
import com.alerthub.actionservice.entity.ActionType;
import com.alerthub.actionservice.repository.ActionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActionServiceTest {

    private ActionRepository actionRepository;
    private ActionService actionService;

    @BeforeEach
    void setUp() {
        actionRepository = mock(ActionRepository.class);
        actionService = new ActionService(actionRepository);
    }

    @Test
    void getAllActions_shouldReturnOnlyNotDeletedActions() {
        Action action1 = createAction(1L);
        Action action2 = createAction(2L);

        when(actionRepository.findByIsDeletedFalse()).thenReturn(List.of(action1, action2));

        List<Action> result = actionService.getAllActions();

        assertEquals(2, result.size());
        verify(actionRepository, times(1)).findByIsDeletedFalse();
    }

    @Test
    void getActionById_whenActionExists_shouldReturnAction() {
        Action action = createAction(1L);

        when(actionRepository.findById(1L)).thenReturn(Optional.of(action));

        Action result = actionService.getActionById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Fix bugs", result.getName());
        verify(actionRepository, times(1)).findById(1L);
    }

    @Test
    void getActionById_whenActionDoesNotExist_shouldThrowException() {
        when(actionRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> actionService.getActionById(99L)
        );

        assertEquals("Action not found with id: 99", exception.getMessage());
        verify(actionRepository, times(1)).findById(99L);
    }

    @Test
    void getActionsByUserId_shouldReturnUserActions() {
        Action action = createAction(1L);

        when(actionRepository.findByUserIdAndIsDeletedFalse(1001L)).thenReturn(List.of(action));

        List<Action> result = actionService.getActionsByUserId(1001L);

        assertEquals(1, result.size());
        assertEquals(1001L, result.get(0).getUserId());
        verify(actionRepository, times(1)).findByUserIdAndIsDeletedFalse(1001L);
    }

    @Test
    void createAction_shouldSaveAction() {
        Action action = createAction(null);
        Action savedAction = createAction(1L);

        when(actionRepository.save(action)).thenReturn(savedAction);

        Action result = actionService.createAction(action);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Fix bugs", result.getName());
        verify(actionRepository, times(1)).save(action);
    }

    @Test
    void updateAction_whenActionExists_shouldUpdateAndSaveAction() {
        Action existingAction = createAction(1L);

        Action updatedAction = createAction(1L);
        updatedAction.setName("Updated action");
        updatedAction.setConditionJson("[[1,2],[3]]");
        updatedAction.setTo("new@gmail.com");
        updatedAction.setMessage("Updated message");
        updatedAction.setIsEnabled(false);

        when(actionRepository.findById(1L)).thenReturn(Optional.of(existingAction));
        when(actionRepository.save(existingAction)).thenReturn(existingAction);

        Action result = actionService.updateAction(1L, updatedAction);

        assertEquals("Updated action", result.getName());
        assertEquals("[[1,2],[3]]", result.getConditionJson());
        assertEquals("new@gmail.com", result.getTo());
        assertEquals("Updated message", result.getMessage());
        assertFalse(result.getIsEnabled());

        verify(actionRepository, times(1)).findById(1L);
        verify(actionRepository, times(1)).save(existingAction);
    }

    @Test
    void enableAction_whenActionExists_shouldSetIsEnabledTrue() {
        Action action = createAction(1L);
        action.setIsEnabled(false);

        when(actionRepository.findById(1L)).thenReturn(Optional.of(action));
        when(actionRepository.save(action)).thenReturn(action);

        Action result = actionService.enableAction(1L);

        assertTrue(result.getIsEnabled());
        verify(actionRepository, times(1)).save(action);
    }

    @Test
    void disableAction_whenActionExists_shouldSetIsEnabledFalse() {
        Action action = createAction(1L);
        action.setIsEnabled(true);

        when(actionRepository.findById(1L)).thenReturn(Optional.of(action));
        when(actionRepository.save(action)).thenReturn(action);

        Action result = actionService.disableAction(1L);

        assertFalse(result.getIsEnabled());
        verify(actionRepository, times(1)).save(action);
    }

    @Test
    void deleteAction_whenActionExists_shouldSoftDeleteAction() {
        Action action = createAction(1L);
        action.setIsDeleted(false);

        when(actionRepository.findById(1L)).thenReturn(Optional.of(action));
        when(actionRepository.save(action)).thenReturn(action);

        actionService.deleteAction(1L);

        assertTrue(action.getIsDeleted());
        verify(actionRepository, times(1)).findById(1L);
        verify(actionRepository, times(1)).save(action);
        verify(actionRepository, never()).delete(action);
    }

    private Action createAction(Long id) {
        Action action = new Action();
        action.setId(id);
        action.setUserId(1001L);
        action.setName("Fix bugs");
        action.setConditionJson("[[1]]");
        action.setTo("ameen@gmail.com");
        action.setActionType(ActionType.EMAIL);
        action.setRunOnTime(LocalTime.of(15, 30));
        action.setRunOnDay("All");
        action.setMessage("Need to fix");
        action.setIsEnabled(true);
        action.setIsDeleted(false);
        return action;
    }
}