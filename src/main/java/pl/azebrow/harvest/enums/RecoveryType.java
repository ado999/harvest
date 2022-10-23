package pl.azebrow.harvest.enums;

import lombok.Getter;

public enum RecoveryType {
    PASSWORD_RECOVERY("Password recovery"),
    PASSWORD_CREATION("Password creation");

    @Getter
    private final String subject;

    RecoveryType(String subject) {
        this.subject = subject;
    }
}
