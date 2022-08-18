package pl.azebrow.harvest.model;

public enum JobUnit {
    TIME("h"),
    AREA("m2"),
    QUANTITY("szt"),
    WEIGHT("kg");

    private final String suffix;

    JobUnit(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getName() {
        return name();
    }
}

