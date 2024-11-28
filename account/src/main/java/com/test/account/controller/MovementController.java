package com.test.account.controller;

import com.test.account.model.MovementDTO;
import com.test.account.service.IMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movements")
public class MovementController {

    private final IMovementService movementService;

    @Autowired
    public MovementController(IMovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping
    public ResponseEntity<List<MovementDTO>> getAllMovements() {
        return ResponseEntity.ok(movementService.getAllMovements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovementDTO> getMovementById(@PathVariable Long id) {
        MovementDTO movement = movementService.getMovementById(id);
        return ResponseEntity.ok(movement);
    }

    @PostMapping
    public ResponseEntity<MovementDTO> createMovement(@RequestBody MovementDTO movementDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movementService.createMovement(movementDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovementDTO> updateMovement(@PathVariable Long id, @RequestBody MovementDTO movementDTO) {
        return ResponseEntity.ok(movementService.updateMovement(id, movementDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Long id) {
        movementService.deleteMovement(id);
        return ResponseEntity.noContent().build();
    }


}
