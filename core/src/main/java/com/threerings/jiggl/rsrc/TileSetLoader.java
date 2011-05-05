//
// Jiggl Core - 2D game development library
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.rsrc;

/**
 * Abstracts away the machinations of loading tilesets over the network or from resource bundles
 * included with the application or in whatever other way a platform may provide image data.
 */
public abstract class TileSetLoader
{
    /**
     * Loads the tileset with the supplied identifier. Concrete implementations of this class
     * determine the concrete tileset representation, based on the structure of the id.
     */
    public abstract TileSet load (String tileSetId);
}
