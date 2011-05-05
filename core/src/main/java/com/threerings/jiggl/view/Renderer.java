//
// Jiggl Core - 2D game development library
// http://github.com/threerings/jiggl/blob/master/LICENSE

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
