package pl.azebrow.harvest.constants;

public enum RoleEnum {
    ADMIN(Constants.ADMIN),
    STAFF(Constants.STAFF),
    USER(Constants.USER),
    TEST(Constants.TEST);


    private final String name;

    private RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static final class Constants {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String STAFF = "ROLE_STAFF";
        public static final String USER = "ROLE_USER";
        public static final String TEST = "ROLE_TEST";
    }

}
