package mink.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Todo {
    public int id;
    public int user_id;
    public String title;
    public Date due_on;
    public String status;

    public Todo () {}
    public Todo(int id, int user_id, String title, Date due_on, String status) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.due_on = due_on;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDue_on() {
        return due_on;
    }

    public void setDue_on(Date due_on) {
        this.due_on = due_on;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return title;
    }
}
