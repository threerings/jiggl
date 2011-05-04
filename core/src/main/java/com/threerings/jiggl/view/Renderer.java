//
// $Id$

package com.threerings.jiggl.view;

/**
 * Abstracts over rendering backends; handles the creation of geometry and shaders and whatnot.
 */
public abstract class Renderer
{
    /**
     * Creates a quad of the specified width and height.
     */
    public abstract Geometry createQuad (float width, float height);
}
