package com.rbaliwal00.todoappusingjsp.model;

public enum Role {
    CUSTOMER("CUSTOMER"),
    EXPERT("EXPERT"),
    ADMIN("ADMIN");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRoleName() {
        return role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + role + '\'' +
                '}';
    }
}
