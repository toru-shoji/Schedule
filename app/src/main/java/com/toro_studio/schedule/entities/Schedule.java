package com.toro_studio.schedule.entities;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Schedule extends RealmObject {

    private Date date;
    private RealmList<Todo> todoList;

    public final Date getDate() {
        return date;
    }

    public final void setDate(Date date) {
        this.date = date;
    }

    public final RealmList<Todo> getTodoList() {
        return todoList;
    }

    public final void setTodoList(RealmList<Todo> todoList) {
        this.todoList = todoList;
    }

}
