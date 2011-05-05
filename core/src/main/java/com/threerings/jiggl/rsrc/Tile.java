//
// Jiggl Core - 2D game development library
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.rsrc;

/**
 * Provides access to a single tile from a {@link TileSet}.
 */
public abstract class Tile
{
    /** Returns the width of this tile, in pixels. */
    public abstract int getWidth ();

    /** Returns the height of this tile, in pixels. */
    public abstract int getHeight ();
}
