//
// $Id$

package com.threerings.jiggl.rsrc;

/**
 * Provides access to tiles which are extracted from a larger image.
 */
public abstract class TileSet
{
    /** Returns the tile at the specified index. */
    public abstract Tile get (int index);
}
