//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.rsrc;

/**
 * Handles the loading of tilesets in a GWT/WebGL application.
 */
public class WGLTileSetLoader extends TileSetLoader
{
    public TileSet load (String tileSetId)
    {
        // TEMP
        return new TileSet() {
            public Tile get (int index) {
                return null;
            }
        };
    }
}
