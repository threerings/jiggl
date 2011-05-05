//
// Jiggl Core - 2D game development library
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.util;

/**
 * Provides a first-class reference to a mutable scalar value.
 */
public class Scalar
{
    /** The current value of this scalar. */
    public float value;

    /**
     * Creates a scalar with the specified initial value.
     */
    public Scalar (float init)
    {
        this.value = init;
    }
}
