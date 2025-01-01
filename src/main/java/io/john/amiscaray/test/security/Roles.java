package io.john.amiscaray.test.security;

import io.john.amiscaray.quak.security.auth.principal.role.Role;

public class Roles {

    public static Role user() {
        return () -> "USER";
    }

    public static Role admin() {
        return () -> "ADMIN";
    }

}
