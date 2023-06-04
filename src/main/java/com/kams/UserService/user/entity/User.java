package com.kams.UserService.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for the users.
 */
@Entity
@Table(name="users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * User's login and he's representation in app.
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * User's age.
     */
    private int age;
}
