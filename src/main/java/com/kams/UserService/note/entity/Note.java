package com.kams.UserService.note.entity;


import com.kams.UserService.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="notes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String summary;

    @Column(name="content", columnDefinition="CLOB")
    @Lob
    private String content;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch=FetchType.LAZY)
    private User user;
}
