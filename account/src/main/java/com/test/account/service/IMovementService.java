package com.test.account.service;

import com.test.account.model.MovementDTO;

import java.util.List;

public interface IMovementService {
    List<MovementDTO> getAllMovements();
    MovementDTO getMovementById(Long id);
    MovementDTO createMovement(MovementDTO movementDTO);
    MovementDTO updateMovement(Long id, MovementDTO movementDTO);
    void deleteMovement(Long id);
}
