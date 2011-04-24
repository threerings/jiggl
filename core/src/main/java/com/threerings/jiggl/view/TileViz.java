//
// $Id$

package com.threerings.jiggl.view;

import com.threerings.jiggl.rsrc.Tile;

/**
 * A visible entity that displays a single tile on the screen.
 */
public abstract class TileViz extends Viz
{
    @Override public int getWidth ()
    {
        return _tile.getWidth();
    }

    @Override public int getHeight ()
    {
        return _tile.getHeight();
    }

    protected TileViz (Tile tile)
    {
        _tile = tile;
    }

    protected Tile _tile;
}
