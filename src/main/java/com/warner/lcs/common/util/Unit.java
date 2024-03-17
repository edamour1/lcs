package com.warner.lcs.common.util;

public enum Unit {
    FEET("Feet", "ft",1),
    INCHES("Inches", "in",2),
    KILOMETER("Kilometer", "km",3),
    METER("Meter", "m",4),
    CENTIMETER("Centimeter", "cm",5),
    MILLIMETER("Millimeter", "mm",6),
    KILOGRAM("Kilogram", "kg",7),
    GRAM("Gram", "g",8),
    MILLIGRAM("Milligram", "mg",9),
    KILOLITRE("Kilolitre", "kL",10),
    LITRE("Litre", "L",11),
    MILLILITER("Milliliter", "mL",12),
    CENTILITRE("Centilitre", "cL",13),
    FLUID_OUNCE("Fluid Ounce", "fl oz",14),
    MINUTE("Minute", "min",15),
    HOUR("Hour", "hr",16),
    SECOND("Second", "sec",17),
    DAYS("Days", "days",18),
    WEEK("Week", "wk",19),
    MONTH("Month", "mo",20),
    YEAR("Year", "yr",21),
    KELVIN("Kelvin", "K",22),
    CELSIUS("Celsius", "°C",23),
    FAHRENHEIT("Fahrenheit", "°F",24),
    SQUARE_FEET("Square Feet", "sqft",25);

    private final String fullName;
    private final String abbreviation;

    private final int id;

    Unit(String fullName, String abbreviation, int id) {
        this.fullName = fullName;
        this.abbreviation = abbreviation;
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int getId() { return id; }

}

