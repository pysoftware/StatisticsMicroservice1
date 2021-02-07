package com.dimasta.learn.statisticsMicroservice.rabbitMqListeners;

import com.dimasta.learn.statisticsMicroservice.models.ToDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class NewToDoRabbitListener {

    private static final Logger logger = LoggerFactory.getLogger(NewToDoRabbitListener.class);

    @RabbitListener(queues = "new_todo")
    public void newUserRabbitListener(ToDo toDo) {
        logger.info("New todo added " + toDo);
    }

}
