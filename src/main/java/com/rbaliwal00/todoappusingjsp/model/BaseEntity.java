package com.rbaliwal00.todoappusingjsp.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.util.Objects;

@MappedSuperclass
@Getter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (getClass().isInstance(o) && o instanceof BaseEntity other) {
        return this.id != null && Objects.equals(this.id, other.id);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s [id=%d]", getClass().getSimpleName(), this.id);
    }
}