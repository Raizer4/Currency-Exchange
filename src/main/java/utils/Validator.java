package utils;

import java.util.Arrays;
import java.util.Objects;

public class Validator {

    public static boolean isCodeValid(String code) {
        if (code == null) return false;
        return code.matches("[A-Z]{3}");
    }

    public static boolean isNotNull(String ...args) {
        return Arrays.stream(args).allMatch(Objects::nonNull);
    }

}
