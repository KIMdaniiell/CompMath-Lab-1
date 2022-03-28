package io;

public enum DataEntryMode {
    MANUAL,
    FILE,
    GENERATE;

    @Deprecated
    public static boolean contains(String ingenre) {
        for (DataEntryMode mode : DataEntryMode.values()) {
            if (ingenre.equals(mode.name())) {
                return true;
            }
        }
        return false;
    }
}
