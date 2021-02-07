package com.dimasta.learn.statisticsMicroservice.models;

import com.dimasta.learn.statisticsMicroservice.utilities.ToDoPriority;
import lombok.Data;
import java.util.Date;

@Data
public class ToDo {

    private Long id;

    private String description;

    private Date createdAt;

    private ToDoPriority priority;

    private User user;
}
