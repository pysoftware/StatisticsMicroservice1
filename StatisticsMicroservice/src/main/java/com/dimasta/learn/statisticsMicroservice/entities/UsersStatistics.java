package com.dimasta.learn.statisticsMicroservice.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "latest_statistics")
@AllArgsConstructor
@NoArgsConstructor
public class UsersStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name = "created_at")
    @Getter
    @Setter
    private Date createdAt;

    @Column(name = "user_id")
    @Getter
    @Setter
    private Long userId;

    @PrePersist
    private void getTimeOperation() {
        this.createdAt = new Date();
    }

}
