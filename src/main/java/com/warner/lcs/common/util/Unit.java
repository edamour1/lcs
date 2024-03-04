package com.warner.lcs.common.util;

public enum Unit {
    FEET("Feet", "ft"),
    INCHES("Inches", "in"),
    KILOMETER("Kilometer", "km"),
    METER("Meter", "m"),
    CENTIMETER("Centimeter", "cm"),
    MILLIMETER("Millimeter", "mm"),
    KILOGRAM("Kilogram", "kg"),
    GRAM("Gram", "g"),
    MILLIGRAM("Milligram", "mg"),
    KILOLITRE("Kilolitre", "kL"),
    LITRE("Litre", "L"),
    MILLILITER("Milliliter", "mL"),
    CENTILITRE("Centilitre", "cL"),
    FLUID_OUNCE("Fluid Ounce", "fl oz"),
    MINUTE("Minute", "min"),
    HOUR("Hour", "hr"),
    SECOND("Second", "sec"),
    DAYS("Days", "days"),
    WEEK("Week", "wk"),
    MONTH("Month", "mo"),
    YEAR("Year", "yr"),
    KELVIN("Kelvin", "K"),
    CELSIUS("Celsius", "°C"),
    FAHRENHEIT("Fahrenheit", "°F");

    private final String fullName;
    private final String abbreviation;

    Unit(String fullName, String abbreviation) {
        this.fullName = fullName;
        this.abbreviation = abbreviation;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}

