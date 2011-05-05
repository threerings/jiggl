//
// Jiggl Core - 2D game development library
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl;

import com.threerings.jiggl.rsrc.TileSetLoader;
import com.threerings.jiggl.tween.Tweener;
import com.threerings.jiggl.util.Driver;
import com.threerings.jiggl.view.Renderer;
import com.threerings.jiggl.view.View;

/**
 * Provides one-stop shopping for all your Jiggl needs.
 */
public class Context
{
    /** Drives the boat. */
    public final Driver driver;

    /** Provides global renderer stuffs. */
    public final Renderer renderer;

    /** Displays the visibles. */
    public final View view;

    /** Loads the tilesets. */
    public final TileSetLoader tiles;

    /** Jiggles the properties. */
    public final Tweener tweener;

    protected Context (Driver driver, Renderer renderer, View view, TileSetLoader tiles)
    {
        this.driver = driver;
        this.renderer = renderer;
        this.view = view;
        this.tiles = tiles;
        this.tweener = new Tweener.Impl(driver);
    }
}
