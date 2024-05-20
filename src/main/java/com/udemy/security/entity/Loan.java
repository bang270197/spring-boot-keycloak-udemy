package com.udemy.security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "loan_number", nullable = false)
    private Integer id;

    //    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "customer_id", nullable = false)
//    private Customer customer;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "start_dt", nullable = false)
    private LocalDate startDt;

    @Column(name = "loan_type", nullable = false, length = 100)
    private String loanType;

    @Column(name = "total_loan", nullable = false)
    private Integer totalLoan;

    @Column(name = "amount_paid", nullable = false)
    private Integer amountPaid;

    @Column(name = "outstanding_amount", nullable = false)
    private Integer outstandingAmount;

    @Column(name = "create_dt")
    private LocalDate createDt;

}