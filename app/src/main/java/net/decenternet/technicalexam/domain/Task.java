package net.decenternet.technicalexam.domain;

public class Task {
    
    private Integer id;
    private String isCompleted, description;

    public Integer getId() {
        return id;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
