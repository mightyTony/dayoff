package kr.tony.dayoff.user.domain;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

    ADMIN(Authority.ADMIN),
    EMPLOYEE(Authority.EMPLOYEE),
    TEAM_LEADER(Authority.TEAM_LEADER),
    MASTER(Authority.MASTER);

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String EMPLOYEE = "ROLE_EMPLOYEE";
        public static final String TEAM_LEADER = "ROLE_TEAM_LEADER";
        public static final String MASTER = "ROLE_MASTER";
    }
}
