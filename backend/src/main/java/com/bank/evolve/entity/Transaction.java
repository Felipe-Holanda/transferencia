package com.bank.evolve.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {

    public Transaction () {}

    public Transaction (Double amount, String description, double taxes,
            User sender, User recipient, LocalDate targetDate, String transactionHash) {
        this.amount = amount;
        this.description = description;
        this.taxes = taxes;
        this.sender = sender;
        this.recipient = recipient;
        this.targetDate = targetDate;
        this.transactionHash = transactionHash;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double taxes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @Column
    private LocalDate targetDate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false, updatable = false)
    private String transactionHash;
}

