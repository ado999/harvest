package pl.azebrow.harvest.model;

public enum AccountStatus {
    ACCOUNT_CREATED,
    CONFIRMATION_EMAIL_SENT,
    EMAIL_CONFIRMED,
    EMAIL_CHANGED_NOT_CONFIRMED,
    EMAIL_NONEXISTENT,
    ERROR_SENDING_EMAIL
}
