package com.dimasta.learn.toDoMicroservice.utilities;

public enum ToDoPriority {
    HIGH("high"), LOW("low");

    private final String priorityType;

    ToDoPriority(String priorityType) {
        this.priorityType = priorityType;
    }

    public String getPriorityType() {
        return priorityType;
    }
}
