package com.warner.lcs.app.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {

    private int id;
    private String username;
    private String password;
    private String role;
    private String hint;
    private boolean isDeleted;

    public Admin()  {}

    public Admin(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.username = resultSet.getString("username");
        this.password = resultSet.getString("password");
        this.role = resultSet.getString("role");
        this.hint = resultSet.getString("hint");
        int isDeletedIntValue = resultSet.getInt("is_deleted");
        this.isDeleted = isDeletedIntValue == 0 ? false : true;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
