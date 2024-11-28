package com.test.account.converter;

import com.test.account.domain.Movement;
import com.test.account.model.MovementDTO;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovementConverter {

    public MovementDTO toMovementDTO(Movement movement) {
        if (movement == null) {
            return null;
        }

        MovementDTO dto = new MovementDTO();
        dto.setId(movement.getId());
        dto.setMovementType(movement.getMovementType());
        dto.setAmount(movement.getAmount());
        dto.setBalance(movement.getBalance());
        dto.setCreatedAt(movement.getCreatedAt());
        dto.setStatus(movement.getStatus());

        if(movement.getAccount() != null) {
            dto.setAccountId(movement.getAccount().getId());
        }

        return dto;
    }

    public Movement fromDTO(MovementDTO movementDTO) {
        if (movementDTO == null) {
            return null;
        }

        Movement movement = new Movement();
        movement.setId(movementDTO.getId());
        movement.setMovementType(movementDTO.getMovementType());
        movement.setAmount(movementDTO.getAmount());
        movement.setBalance(movementDTO.getBalance());
        movement.setCreatedAt(movementDTO.getCreatedAt());
        movement.setStatus(movementDTO.getStatus());

        if(movementDTO.getAccount() != null) {
            movement.setAccount(new AccountConverter().fromDTO(movementDTO.getAccount()));
        }

        return movement;
    }

    public List<MovementDTO> fromListEntityToDTOList(List<Movement> movements) {
        return movements.stream().map(this::toMovementDTO).collect(Collectors.toList());
    }

    public List<Movement> fromListDTOToEntityList(List<MovementDTO> movementDTOS) {
        return movementDTOS.stream().map(this::fromDTO).collect(Collectors.toList());
    }

    private Instant convertStringToLocalInstant(String dateStr){
        try{

            // Define the formatter matching the input format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Parse the date string to a LocalDate
            LocalDate localDate = LocalDate.parse(dateStr, formatter);

            // Convert the LocalDate to an Instant (UTC time)
            return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        }catch (Exception e){
            throw new IllegalArgumentException("Invalid date format");
        }

    }
}
