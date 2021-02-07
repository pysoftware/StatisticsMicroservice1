package com.dimasta.learn.toDoMicroservice.entities;

import com.dimasta.learn.toDoMicroservice.utilities.ToDoPriority;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "todos")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"user"})
public class ToDo {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    @Getter
    @Setter
    @NotEmpty
    private String description;

    @Column(name = "created_at")
    @Getter
    @Setter
    private Date createdAt;

    @Column(name = "priority", nullable = false)
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private ToDoPriority priority;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    @Getter
    private User user;

    @PrePersist
    void getTimeOperation() {
        this.createdAt = new Date();
    }


}
