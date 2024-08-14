package com.bank;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import com.bank.dto.AccountDTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.bank.dto.AccountStatus;
import com.bank.exception.AccountExists;
import com.bank.exception.AccountNotFound;

import com.bank.model.Account;
import com.bank.model.AccountType;
import com.bank.model.Customer;
import com.bank.repo.AccountRepo;
import com.bank.repo.CustomerRepo;
import com.bank.service.AccountServiceImpli;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

   
    @Mock
    private AccountRepo accountRepo;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private AccountServiceImpli accountService;

    

    @Test
    public void testAddAccount_NewCustomer_Success() {
        Customer newCustomer = new Customer();
        newCustomer.setEmail("test@example.com");
        newCustomer.setName("John Doe");
newCustomer.setCustomerId(1l);
        Account newAccount = new Account();
        newAccount.setAccountType(AccountType.SAVING);
        newAccount.setBalance(1000.0);
        newAccount.setCustomer(newCustomer);
        newAccount.setAccountId(1l);

        when(customerRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(customerRepo.save(ArgumentMatchers.any(Customer.class))).thenReturn(newCustomer);
        when(accountRepo.save(ArgumentMatchers.any(Account.class))).thenReturn(newAccount);

        AccountStatus result = accountService.addAccount(newAccount);
System.out.println(result);
        // Verify the result
        assertNotNull(result);
        assertEquals(AccountType.SAVING + " account created successfully", result.getMsg());
        assertNotNull(result.getCustomerId());
        assertEquals(newCustomer.getName(), "John Doe");

        verify(customerRepo, times(1)).findByEmail("test@example.com");
        verify(customerRepo, times(1)).save(newCustomer);
        verify(accountRepo, times(1)).save(newAccount);
    }

    @Test
    public void testAddAccount_ExistingCustomer_AccountExistsException() {
        Customer existingCustomer = new Customer();
        existingCustomer.setEmail("test@example.com");
        existingCustomer.setName("Jane Doe");
        existingCustomer.setCustomerId(1l);

        Account existingAccount = new Account();
        existingAccount.setAccountType(AccountType.CURRENT);
        existingAccount.setBalance(500.0);
        existingAccount.setCustomer(existingCustomer);
        existingAccount.setAccountId(1l);
        
        Map<AccountType,Account>m=new HashMap<>();
        m.put(AccountType.CURRENT, existingAccount);
        existingCustomer.setAccounts(m);
        when(customerRepo.findByEmail("test@example.com")).thenReturn(Optional.of(existingCustomer));

        assertThrows(AccountExists.class, () -> accountService.addAccount(existingAccount));

        verify(customerRepo, times(1)).findByEmail("test@example.com");
        verify(customerRepo, never()).save(existingCustomer);
        verify(accountRepo, never()).save(existingAccount);
    }

    
    @Test
    public void testGetAllAccounts() {
        List<Account> mockAccounts = new ArrayList<>();
        Account account1 = new Account();
        account1.setAccountId(1L);
        account1.setAccountType(AccountType.SAVING);
        account1.setBalance(1500.0);

        Account account2 = new Account();
        account2.setAccountId(2L);
        account2.setAccountType(AccountType.CURRENT);
        account2.setBalance(2500.0);

        mockAccounts.add(account1);
        mockAccounts.add(account2);

        when(accountRepo.findAll()).thenReturn(mockAccounts);

        when(mapper.map(any(Account.class), eq(AccountDTo.class))).thenAnswer(invocation -> {
            Account source = invocation.getArgument(0);
            AccountDTo target = new AccountDTo();
            target.setAccountId(source.getAccountId());
            target.setAccountType(source.getAccountType().toString());
            target.setBalance(source.getBalance());
            return target;
        });

        List<AccountDTo> result = accountService.getAllAccounts();

        assertNotNull(result);
        assertEquals(2, result.size());
        AccountDTo dto1 = result.get(0);
        System.out.println(dto1);
        assertEquals(1L, dto1.getAccountId());
        assertEquals(1500.0, dto1.getBalance());
        assertEquals(AccountType.SAVING.toString(), dto1.getAccountType());

        AccountDTo dto2 = result.get(1);
        assertEquals(2L, dto2.getAccountId());
        assertEquals(AccountType.CURRENT.toString(), dto2.getAccountType());
        assertEquals(2500.0, dto2.getBalance());

        verify(accountRepo, times(1)).findAll();
       
    }
    @Test
    public void testTransfer_SufficientBalance_Success() {
        Account fromAccount = new Account();
        fromAccount.setAccountId(1L);
        fromAccount.setAccountType(AccountType.SAVING);
        fromAccount.setBalance(2000.0);

        Account toAccount = new Account();
        toAccount.setAccountId(2L);
        toAccount.setAccountType(AccountType.CURRENT);
        toAccount.setBalance(1000.0);

        when(accountRepo.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepo.findById(2L)).thenReturn(Optional.of(toAccount));

        String result = accountService.transfer(1L, 2L, 500.0);

        assertNotNull(result);

        assertEquals(1500.0, fromAccount.getBalance());
        assertEquals(1500.0, toAccount.getBalance());

        verify(accountRepo, times(1)).findById(1L);
        verify(accountRepo, times(1)).findById(2L);
        verify(accountRepo, times(1)).saveAll(any());
    }
	
    @Test
    public void testTransfer_InsufficientBalance_ExceptionThrown() {
        // Mock data
        Account fromAccount = new Account();
        fromAccount.setAccountId(1L);
        fromAccount.setAccountType(AccountType.SAVING);
        fromAccount.setBalance(200.0); 

        Account toAccount = new Account();
        toAccount.setAccountId(2L);
        toAccount.setAccountType(AccountType.CURRENT);
        toAccount.setBalance(1000.0);

        when(accountRepo.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepo.findById(2L)).thenReturn(Optional.of(toAccount));

        assertThrows(RuntimeException.class, () -> accountService.transfer(1L, 2L, 500.0));

        assertEquals(200.0, fromAccount.getBalance());
        assertEquals(1000.0, toAccount.getBalance());

        verify(accountRepo, times(1)).findById(1L);
        verify(accountRepo, times(1)).findById(2L);
        verify(accountRepo, never()).saveAll(any());
    }
    
    @Test
    public void testTransfer_InvalidAccountNumber_AccountNotFoundException() {
        when(accountRepo.findById(1L)).thenReturn(Optional.empty());
        when(accountRepo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFound.class, () -> accountService.transfer(1L, 2L, 500.0));

        verify(accountRepo, times(1)).findById(1L);
        verify(accountRepo, times(1)).findById(2L);
        verify(accountRepo, never()).saveAll(any());
    }
    
    @Test
    public void testGetAccount_InvalidAccountNumber_ThrowsAccountNotFound() {
        Long invalidAccountId = 100L;
        when(accountRepo.findById(invalidAccountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFound.class, () -> accountService.getAccount(invalidAccountId));

        verify(accountRepo, times(1)).findById(invalidAccountId);
    }


}
