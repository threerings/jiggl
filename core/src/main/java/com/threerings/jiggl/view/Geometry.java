//
// $Id$

package com.threerings.jiggl.view;

/**
 * Encapsulates geometry (triangles, triangle strips, etc.) which can be rendered.
 */
public abstract class Geometry
{
    /** The number of vertex components per attribute. */
    public final int compPerAttr;

    /**
     * Binds this geometry's vertices in preparation for rendering. This will be followed almost
     * immediately by a call to draw, with only some shader fiddling in between.
     */
    public abstract void bind (Renderer r);

    /**
     * Draws this geometry.
     */
    public abstract void draw (Renderer r);

    /**
     * Returns the width of this geometry.
     */
    public abstract float getWidth ();

    /**
     * Returns the height of this geometry.
     */
    public abstract float getHeight ();

    protected Geometry (int compPerAttr)
    {
        this.compPerAttr = compPerAttr;
    }
}
