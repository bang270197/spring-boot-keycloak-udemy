package com.udemy.security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "contact_messages")
public class ContactMessage {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
//    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "contact_id", nullable = false, length = 50)
    private String contactId;

    @Column(name = "contact_name", nullable = false, length = 50)
    private String contactName;

    @Column(name = "contact_email", nullable = false, length = 100)
    private String contactEmail;

    @Column(name = "subject", nullable = false, length = 500)
    private String subject;

    @Column(name = "message", nullable = false, length = 2000)
    private String message;

    @Column(name = "create_dt")
    @CreationTimestamp
    private LocalDate createDt;

}