package io.john.amiscaray.test.security.encryption;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.john.amiscaray.quak.core.di.provider.annotation.ManagedType;

@ManagedType
public class BcryptService {

    private final BCrypt.Hasher hasher;
    private final BCrypt.Verifyer verifyer;

    public BcryptService() {
        hasher = BCrypt.withDefaults();
        verifyer = BCrypt.verifyer();
    }

    public String hash(String password) {
        return hasher.hashToString(10, password.toCharArray());
    }

    public boolean verify(String password, String hashedPassword) {
        return verifyer.verify(password.toCharArray(), hashedPassword.toCharArray()).verified;
    }

}
