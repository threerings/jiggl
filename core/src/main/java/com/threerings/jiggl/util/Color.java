//
// Jiggl Core - 2D game development library
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.util;

/**
 * Represents an RGB color.
 */
public class Color
{
    /** The red component of this color. Must be between 0 and 1f. */
    public final Scalar red;

    /** The green component of this color. Must be between 0 and 1f. */
    public final Scalar green;

    /** The blue component of this color. Must be between 0 and 1f. */
    public final Scalar blue;

    /** The alpha component of this color. Must be between 0 and 1f. */
    public final Scalar alpha;

    /**
     * Creates a color with the specified red, green, blue and alpha components.
     */
    public Color (float red, float green, float blue, float alpha)
    {
        this.red = new Scalar(red);
        this.green = new Scalar(green);
        this.blue = new Scalar(blue);
        this.alpha = new Scalar(alpha);
    }

    /**
     * Creates a color with the specified red, green, and blue components, with an alpha of 1.
     */
    public Color (float red, float green, float blue)
    {
        this(red, green, blue, 1f);
    }

    /**
     * Returns a float array suitable for binding to a vec4 shader variable.
     */
    public float[] toVec4fv ()
    {
        return new float[] { red.value, green.value, blue.value, alpha.value };
    }
}
