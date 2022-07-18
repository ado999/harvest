package pl.azebrow.harvest.model;

public enum JobUnit {
    HOURS("h"),
    HA("ha"),
    PIECE("pcs");

    private final String description;

    JobUnit(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
