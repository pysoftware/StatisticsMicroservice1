package com.dimasta.learn.statisticsMicroservice.rabbitMqListeners;

import com.dimasta.learn.statisticsMicroservice.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class NewUserRabbitListener {

    private static final Logger logger = LoggerFactory.getLogger(NewToDoRabbitListener.class);

    @RabbitListener(queues = "new_user")
    public void newUserRabbitListener(User user) {
        // TODO implements user statistics logic
        logger.info(user.toString());
    }

}
