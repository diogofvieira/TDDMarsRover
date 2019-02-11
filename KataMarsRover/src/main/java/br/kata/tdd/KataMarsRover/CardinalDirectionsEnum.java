package br.kata.tdd.KataMarsRover;

public enum CardinalDirectionsEnum {
    NORTH("W", "E", "N"),
    EAST("N", "S", "E"),
    SOUTH("E", "W", "S"),
    WEST("S", "N", "W");

    public String left;
    public String right;
    public String value;

    CardinalDirectionsEnum(String left, String right, String value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public static CardinalDirectionsEnum of(String value) {
        if (value == "N") return NORTH;
        if (value == "W") return WEST;
        if (value == "S") return SOUTH;
        if (value == "E") return EAST;
        throw new IllegalArgumentException("Invalid value");
    }
}
