package com.udemy.security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "account_number", nullable = false)
    private Long account_number;

    //    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "customer_id", nullable = false)
//    private Customer customer;
    @Column(name = "customer_id", nullable = false)
    private Integer customeId;

    @Column(name = "account_type", nullable = false, length = 100)
    private String accountType;

    @Column(name = "branch_address", nullable = false, length = 200)
    private String branchAddress;

    @Column(name = "create_dt")
    @CreationTimestamp
    private LocalDate createDt;

}