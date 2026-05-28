package com.winbank.controller;

import com.winbank.entity.Transaction;
import com.winbank.entity.User;
import com.winbank.repository.TransactionRepository;
import com.winbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/banking")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BankingController {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @GetMapping("/users")
    public List<User> users() {
        return userRepository.findAll();
    }

    @PostMapping("/transfer")
    public Transaction transfer(@RequestBody Transaction transaction) {

        User sender = userRepository.findByEmail(transaction.getSender()).orElseThrow();
        User receiver = userRepository.findByEmail(transaction.getReceiver()).orElseThrow();

        sender.setBalance(sender.getBalance() - transaction.getAmount());
        receiver.setBalance(receiver.getBalance() + transaction.getAmount());

        userRepository.save(sender);
        userRepository.save(receiver);

        transaction.setStatus("SUCCESS");
        transaction.setCreatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    @GetMapping("/transactions")
    public List<Transaction> transactions() {
        return transactionRepository.findAll();
    }
}