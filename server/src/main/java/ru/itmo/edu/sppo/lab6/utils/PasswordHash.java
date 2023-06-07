package ru.itmo.edu.sppo.lab6.utils;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import ru.itmo.edu.sppo.lab6.document.ReadProperties;

public class PasswordHash {
    private static final String SALT;
    private static final String PROPERTY_PATH_SALT = "hashing.salt";

    static {
        SALT = ReadProperties.read(PROPERTY_PATH_SALT);
    }

    private PasswordHash() {
    }

    public static String generateHashSha384(String password) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, SALT).hmacHex(password);
    }
}
