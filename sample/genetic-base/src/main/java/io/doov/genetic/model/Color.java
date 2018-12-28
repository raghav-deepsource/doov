/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.genetic.model;

import java.util.Objects;

import io.doov.genetic.field.GeneticFieldId;
import io.doov.genetic.field.GeneticPath;

public class Color {

    @GeneticPath(field = GeneticFieldId.RED, readable = "red")
    private int red;

    @GeneticPath(field = GeneticFieldId.GREEN, readable = "green")
    private int green;

    @GeneticPath(field = GeneticFieldId.BLUE, readable = "blue")
    private int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color() {}

    public final static Color BLACK = new Color(0x00,0x00,0x00);
    public final static Color WHITE = new Color(0xFF,0xFF,0xFF);
    public final static Color RED   = new Color(0xFF,0x00,0x00);
    public final static Color GREEN = new Color(0x00,0xFF,0x00);
    public final static Color BLUE  = new Color(0x00,0x00,0xFF);

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public static String hexify(int it) {
        return String.format("0x%08X",it);
    }

    @Override
    public String toString() {
        return "Color{" +
                "red=" + hexify(red) +
                ", green=" + hexify(green) +
                ", blue=" + hexify(blue) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Color))
            return false;
        Color color = (Color) o;
        return red == color.red &&
                green == color.green &&
                blue == color.blue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue);
    }
}