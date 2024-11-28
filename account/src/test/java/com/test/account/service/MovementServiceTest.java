package com.test.account.service;

import com.test.account.converter.AccountConverter;
import com.test.account.converter.MovementConverter;
import com.test.account.domain.Account;
import com.test.account.domain.Movement;
import com.test.account.model.AccountDTO;
import com.test.account.model.MovementDTO;
import com.test.account.repository.MovementRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

class MovementServiceTest {

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private MovementConverter movementConverter;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountConverter accountConverter;

    @InjectMocks
    private MovementService movementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMovements() {
        MovementDTO movementDTO = new MovementDTO();
        movementDTO.setId(1L);
        movementDTO.setMovementType("DEPOSIT");
        movementDTO.setAmount(100.0);
        movementDTO.setBalance(100.0);
        movementDTO.setStatus(true);
        movementDTO.setAccountNumber("123456");

        MovementDTO movementDTO2 = new MovementDTO();
        movementDTO2.setId(2L);
        movementDTO2.setMovementType("WITHDRAW");
        movementDTO2.setAmount(50.0);
        movementDTO2.setBalance(50.0);
        movementDTO2.setStatus(true);
        movementDTO2.setAccountNumber("123456");


        Movement movement = new Movement();
        movement.setId(1L);
        movement.setMovementType("DEPOSIT");
        movement.setAmount(100.0);
        movement.setBalance(100.0);
        movement.setStatus(true);
        movement.setAccount(this.getAccount());

        Movement movement2 = new Movement();
        movement2.setId(2L);
        movement2.setMovementType("WITHDRAW");
        movement2.setAmount(50.0);
        movement2.setBalance(50.0);
        movement2.setStatus(true);
        movement2.setAccount(this.getAccount());

        List<MovementDTO> expectedMovements = List.of(movementDTO, movementDTO2);

        Mockito.when(movementRepository.findAll()).thenReturn(Arrays.asList(movement, movement2));
        Mockito.when(movementConverter.fromListEntityToDTOList(Arrays.asList(movement, movement2))).thenReturn(Arrays.asList(movementDTO, movementDTO2));

        List<MovementDTO> response = movementService.getAllMovements();

        Assertions.assertThat(response).isEqualTo(expectedMovements).hasSize(2);

        Mockito.verify(movementRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetMovementById() {
        MovementDTO movementDTO = new MovementDTO();
        movementDTO.setId(1L);
        movementDTO.setMovementType("DEPOSIT");
        movementDTO.setAmount(100.0);
        movementDTO.setBalance(100.0);
        movementDTO.setStatus(true);
        movementDTO.setAccountNumber("123456");

        Movement movement = new Movement();
        movement.setId(1L);
        movement.setMovementType("DEPOSIT");
        movement.setAmount(100.0);
        movement.setBalance(100.0);
        movement.setStatus(true);
        movement.setAccount(this.getAccount());

        Mockito.when(movementRepository.findById(1L)).thenReturn(java.util.Optional.of(movement));
        Mockito.when(movementConverter.toMovementDTO(movement)).thenReturn(movementDTO);

        MovementDTO response = movementService.getMovementById(1L);

        Assertions.assertThat(response).isEqualTo(movementDTO);

        Mockito.verify(movementRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testCreateMovement() {
        MovementDTO movementDTO = new MovementDTO();
        movementDTO.setId(1L);
        movementDTO.setMovementType("DEPOSIT");
        movementDTO.setAmount(100.0);
        movementDTO.setBalance(100.0);
        movementDTO.setStatus(true);
        movementDTO.setAccountNumber("123456");

        Movement movement = new Movement();
        movement.setId(1L);
        movement.setMovementType("DEPOSIT");
        movement.setAmount(100.0);
        movement.setBalance(100.0);
        movement.setStatus(true);
        movement.setAccount(this.getAccount());

        Mockito.when(accountService.getByAccountNumber("123456")).thenReturn(this.getAccountDTO());
        Mockito.when(accountService.getBalance("123456")).thenReturn(100.0);
        Mockito.when(movementConverter.fromDTO(movementDTO)).thenReturn(movement);
        Mockito.when(movementRepository.save(movement)).thenReturn(movement);
        Mockito.when(movementConverter.toMovementDTO(movement)).thenReturn(movementDTO);

        MovementDTO response = movementService.createMovement(movementDTO);

        Assertions.assertThat(response).isEqualTo(movementDTO);

        Mockito.verify(accountService, Mockito.times(1)).getByAccountNumber("123456");
        Mockito.verify(accountService, Mockito.times(1)).getBalance("123456");
        Mockito.verify(movementConverter, Mockito.times(1)).fromDTO(movementDTO);
        Mockito.verify(movementRepository, Mockito.times(1)).save(movement);
    }

    @Test
    void testUpdateMovement() {
        MovementDTO movementDTO = new MovementDTO();
        movementDTO.setId(1L);
        movementDTO.setMovementType("DEPOSIT");
        movementDTO.setAmount(100.0);
        movementDTO.setBalance(100.0);
        movementDTO.setStatus(true);
        movementDTO.setAccountNumber("123456");

        Movement movement = new Movement();
        movement.setId(1L);
        movement.setMovementType("DEPOSIT");
        movement.setAmount(100.0);
        movement.setBalance(100.0);
        movement.setStatus(true);
        movement.setAccount(this.getAccount());

        Mockito.when(movementRepository.existsById(1L)).thenReturn(true);
        Mockito.when(movementConverter.fromDTO(movementDTO)).thenReturn(movement);
        Mockito.when(movementRepository.save(movement)).thenReturn(movement);
        Mockito.when(movementConverter.toMovementDTO(movement)).thenReturn(movementDTO);

        MovementDTO response = movementService.updateMovement(1L, movementDTO);

        Assertions.assertThat(response).isEqualTo(movementDTO);

        Mockito.verify(movementRepository, Mockito.times(1)).existsById(1L);
        Mockito.verify(movementConverter, Mockito.times(1)).fromDTO(movementDTO);
        Mockito.verify(movementRepository, Mockito.times(1)).save(movement);
    }

    @Test
    void testDeleteMovement() {
        Mockito.when(movementRepository.existsById(1L)).thenReturn(true);

        movementService.deleteMovement(1L);

        Mockito.verify(movementRepository, Mockito.times(1)).existsById(1L);
        Mockito.verify(movementRepository, Mockito.times(1)).deleteById(1L);
    }


    private Account getAccount(){
        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("123456");
        account.setAccountType("SAVINGS");
        account.setInitialBalance(100.0);
        account.setStatus(true);
        account.setCustomerId(1L);
        return account;
    }

    private AccountDTO getAccountDTO(){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccountNumber("123456");
        accountDTO.setAccountType("SAVINGS");
        accountDTO.setInitialBalance(100.0);
        accountDTO.setStatus(true);
        accountDTO.setCustomerId(1L);
        return accountDTO;
    }

}
