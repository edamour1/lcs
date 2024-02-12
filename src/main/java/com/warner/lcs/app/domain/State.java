package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class State {
    private int id;
    private String state;

    public State() {}

    public State(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.state = resultSet.getString("state");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
