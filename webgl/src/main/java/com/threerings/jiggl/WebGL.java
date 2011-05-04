//
// $Id$

package com.threerings.jiggl;

import com.google.gwt.canvas.client.Canvas;

import com.threerings.jiggl.rsrc.WGLTileSetLoader;
import com.threerings.jiggl.util.WGLDriver;
import com.threerings.jiggl.view.WGLView;

/**
 * The main factory for WebGL views and visibles.
 */
public class WebGL
{
    public static Context create (Canvas canvas)
    {
        WGLView view = new WGLView(canvas, 500, 500);
        return new Context(new WGLDriver(view, FRAME_DELAY), view.renderer, view,
                           new WGLTileSetLoader());
    }

    protected static final int FRAME_DELAY = 15; // shoot for 66fps
}
