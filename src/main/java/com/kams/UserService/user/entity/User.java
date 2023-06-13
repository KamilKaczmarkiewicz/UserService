package com.kams.UserService.user.entity;

import com.kams.UserService.note.entity.Note;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_name")
    private String userName;

    private int age;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdTime;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    @ToString.Exclude
    private List<Note> notes;


}
