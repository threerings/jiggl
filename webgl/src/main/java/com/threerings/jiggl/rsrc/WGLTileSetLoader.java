//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.rsrc;

/**
 * Handles the loading of tilesets in a GWT/WebGL application.
 */
public class WGLTileSetLoader extends TileSetLoader
{
    public WGLTileSetLoader (String baseURL)
    {
        _baseURL = baseURL + (baseURL.endsWith("/") ? "" : "/");
    }

    @Override // from TileSetLoader
    public TileSet load (String tileSetId)
    {
        int width = 64, height = 64; // TODO
        return new WGLTileSet(_baseURL + tileSetId, width, height);
    }

    protected final String _baseURL;
}
