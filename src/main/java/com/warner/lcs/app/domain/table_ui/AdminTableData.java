package com.warner.lcs.app.domain.table_ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AdminTableData {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private String hint;

    private BooleanProperty selected = new SimpleBooleanProperty(false);

    public AdminTableData(Integer id, String username, String password, String role, String hint) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.hint = hint;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}
