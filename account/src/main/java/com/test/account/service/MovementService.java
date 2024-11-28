package com.test.account.service;

import com.test.account.converter.AccountConverter;
import com.test.account.converter.MovementConverter;
import com.test.account.domain.Movement;
import com.test.account.exception.AccountNotFoundException;
import com.test.account.exception.MovementNotFoundException;
import com.test.account.model.AccountDTO;
import com.test.account.model.MovementDTO;
import com.test.account.model.MovementType;
import com.test.account.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovementService implements IMovementService {

    private final MovementRepository movementRepository;
    private final MovementConverter movementConverter;
    private final  IAccountService accountService;
    private final AccountConverter accountConverter;

    @Autowired
    public MovementService(MovementRepository movementRepository, MovementConverter movementConverter, AccountService accountService, AccountConverter accountConverter) {
        this.movementRepository = movementRepository;
        this.movementConverter = movementConverter;
        this.accountService = accountService;
        this.accountConverter = accountConverter;
    }

    public List<MovementDTO> getAllMovements() {
        List<Movement> movements = movementRepository.findAll();
        return movementConverter.fromListEntityToDTOList(movements);
    }

    public MovementDTO getMovementById(Long id) {
        Optional<Movement> movement = movementRepository.findById(id);
        return movement.map(movementConverter::toMovementDTO).orElseThrow(() -> new AccountNotFoundException(
                "Movement not found with id: " + id)
        );
    }

    public MovementDTO createMovement(MovementDTO movementDTO) {

        String movementType = Optional.ofNullable(movementDTO.getMovementType()).orElseThrow(() -> new IllegalArgumentException(
                "Movement type is required")
        );
        Double amount = Optional.ofNullable(movementDTO.getAmount()).orElseThrow(() -> new IllegalArgumentException(
                "Amount is required")
        );

        String accountNumber = Optional.ofNullable(movementDTO.getAccountNumber()).orElseThrow(() -> new IllegalArgumentException(
                "Account number is required")
        );

//        Optional.ofNullable(movementDTO.getCreatedAt()).orElseThrow(() -> new IllegalArgumentException(
//                "Created at is required")
//        );



        AccountDTO accountDTO = this.accountService.getByAccountNumber(accountNumber);
        Double accountBalance = this.accountService.getBalance(accountNumber);
        Double value = this.getValue(movementType, amount);


        if(Boolean.FALSE.equals(this.validateBalance(accountBalance, value, movementType)))
            throw new IllegalArgumentException(
                    "Movement balance is invalid"
            );

        Movement movement = movementConverter.fromDTO(movementDTO);
        movement.setBalance(accountBalance + value);
        movement.setAccount(accountConverter.fromDTO(accountDTO));

        return movementConverter.toMovementDTO(movementRepository.save(movement));
    }

    private Double getValue(String movementType, Double value) {
        if (movementType.equals(MovementType.DEPOSIT.toString())) {
            return value;
        } else if (movementType.equals(MovementType.WITHDRAWAL.toString())) {
            return -value;
        } else {
            throw new IllegalArgumentException("Movement type is invalid");
        }
    }

    private Boolean validateBalance(Double accountBalance, Double value, String movementType) {
        if (movementType.equals(MovementType.WITHDRAWAL.toString())) {
            return accountBalance + value >= 0;
        }
        return true;
    }



    public MovementDTO updateMovement(Long id, MovementDTO movementDTO) {
        if (movementRepository.existsById(id)) {
            Movement movement = movementConverter.fromDTO(movementDTO);
            movement.setId(id);
            return movementConverter.toMovementDTO(movementRepository.save(movement));
        } else {
            throw new MovementNotFoundException("Movement not found with id: " + id);
        }
    }

    public void deleteMovement(Long id) {
        if (movementRepository.existsById(id)) {
            movementRepository.deleteById(id);
        } else {
            throw new MovementNotFoundException("Movement not found with id: " + id);
        }
    }
}
