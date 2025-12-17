package fr.univtln.pegliasco.tp.model;

public enum Value {
    DEUX(2, "Deux"),
    TROIS(3, "Trois"),
    QUATRE(4, "Quatre"),
    CINQ(5, "Cinq"),
    SIX(6, "Six"),
    SEPT(7, "Sept"),
    HUIT(8, "Huit"),
    NEUF(9, "Neuf"),
    DIX(10, "Dix"),
    VALET(11, "Valet"),
    DAME(12, "Dame"),
    ROI(13, "Roi"),
    AS(14, "As");

    private final int value;
    private  final String name;

    Value(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public  String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
