//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.rsrc;

import com.google.gwt.user.client.ui.Image;

/**
 * Handles loading images via the browser and making them available as tile textures.
 */
public class WGLTileSet extends TileSet
{
    public WGLTileSet (String imageURL, int width, int height)
    {
        _image = new Image(imageURL);
        _width = width;
        _height = height;
    }

    @Override // from TileSet
    public Tile get (final int index)
    {
        return new Tile() {
            @Override public int getWidth () {
                return _width;
            }
            @Override public int getHeight () {
                return _height;
            }
        };
    }

    protected final Image _image;
    protected final int _width, _height;
}
