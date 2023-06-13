package com.kams.UserService.note.view;

import com.kams.UserService.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class NoteModel extends RepresentationModel<NoteModel> {

    private String title;

    private String summary;

    private String content;

    private long user_id;
}
