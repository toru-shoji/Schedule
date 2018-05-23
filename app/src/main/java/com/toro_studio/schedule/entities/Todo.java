package com.toro_studio.schedule.entities;

import java.util.Date;

import io.realm.RealmObject;

public class Todo extends RealmObject {

    private Date date;
    private String todoName;

    public final Date getDate() {
        return date;
    }

    public final void setDate(Date date) {
        this.date = date;
    }

    public final String getTodoName() {
        return todoName;
    }

    public final void setTodoName(String todoName) {
        this.todoName = todoName;
    }

}
