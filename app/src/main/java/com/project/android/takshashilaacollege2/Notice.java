package com.project.android.takshashilaacollege2;

/**
 * Created by HP on 24-05-2018.
 */

public class Notice {
    private String subject,description;
    private int id;

    public Notice(String subject, String description, int id) {
        this.setSubject(subject);
        this.setDescription(description);
        this.setId(id);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
}
