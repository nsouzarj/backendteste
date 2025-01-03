package com.example.backend.enums;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ProfileType {
    ADMINISTRADOR("ADMINISTRADOR"),
    USUARIO("USUARIO");

    private final String displayName;

    ProfileType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    private static final Map<String, ProfileType> BY_DISPLAY_NAME =
            Arrays.stream(values()).collect(Collectors.toMap(ProfileType::getDisplayName, e -> e));


    public static ProfileType fromDisplayName(String displayName) {
        if (displayName == null) {
            return null;
        }
        return BY_DISPLAY_NAME.get(displayName);
    }

}