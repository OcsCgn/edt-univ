package com.example.univcalendar;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Cours implements Comparable<Cours>{

    private String name;
    private String start;
    private String end;
    private String location;
    private String description;
    private String color;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor(){
        return this.color;
    }

    public void setColor(){
        String nameSplit = this.name.split(" ",2)[1];
        switch(nameSplit){
            case "Méthodologie de la POO":
                this.color = "#29B6F6";
                break;
            case "Introduction aux systèmes d'exploitation":
                this.color = "#9C27B0";
                break;
            case "Théorie des langages":
                this.color = "#4CAF50";
                break;
            case "Outils pour le calcul des prédicats":
                this.color = "#FFEB3B";
                break;
            case "Algorithmique des graphes":
                this.color = "#F44336";
                break;
        }
    }

    public String getOnlyHour(String date){
        OffsetDateTime dateTime = OffsetDateTime.parse(date);
        // On formate juste l'heure et les minutes
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String dateOnly = dateTime.format(formatter);

        return  dateOnly;
    }

    @Override
    public int compareTo(Cours other) {
        OffsetDateTime d1 = OffsetDateTime.parse(this.start);
        OffsetDateTime d2 = OffsetDateTime.parse(other.start);

        return d1.compareTo(d2);
    }
}
