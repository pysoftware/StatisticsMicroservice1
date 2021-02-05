package com.dimasta.learn.statisticsMicroservice.services;

import com.dimasta.learn.statisticsMicroservice.entities.Statistics;

import java.util.List;

public interface StatisticsService {
    List<Statistics> getStatistics(String jwt, String email);
}
