package com.grup8.OpenEvents.model.entities;

import java.io.Serializable;
import java.util.Calendar;

public class Event implements Serializable {

    private final int id;
    private final String name;
    private final int ownerId;
    private final Calendar creationDate;
    private final String image;
    private final String location;
    private final String description;
    private final Calendar startDate;
    private final Calendar endDate;
    private final int participators;
    private final String slug;
    private final String type;

    public Event(int id, String name, int ownerId, Calendar creationDate, String image, String location, String description, Calendar startDate, Calendar endDate, int participators, String slug, String type) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.creationDate = creationDate;
        this.image = image;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participators = participators;
        this.slug = slug;
        this.type = type;
    }

    public Event(int id, String name, Calendar startDate, Calendar endDate, String location, int participators, String type,  String description) {
        this.id = id;
        this.name = name;
        this.ownerId = 0;
        this.creationDate = null;
        this.image = "";
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participators = participators;
        this.slug = null;
        this.type = type;
    }



    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public int getOwnerId() {
        return ownerId;
    }
    public Calendar getCreationDate() {
        return creationDate;
    }
    public String getImage() {
        return image;
    }
    public String getLocation() {
        return location;
    }
    public String getDescription() {
        return description;
    }
    public Calendar getStartDate() {
        return startDate;
    }
    public Calendar getEndDate() {
        return endDate;
    }
    public int getParticipators() {
        return participators;
    }
    public String getSlug() {
        return slug;
    }
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownerId=" + ownerId +
                ", creationDate=" + creationDate +
                ", image='" + image + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", participators=" + participators +
                ", slug='" + slug + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
