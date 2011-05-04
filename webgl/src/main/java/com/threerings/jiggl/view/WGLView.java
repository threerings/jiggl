//
// $Id$

package com.threerings.jiggl.view;

import com.google.gwt.canvas.client.Canvas;
import com.threerings.jiggl.rsrc.Tile;

import com.googlecode.gwtgl.binding.WebGLRenderingContext;

/**
 * Provides a view implementation based on a GWT {@link Canvas} and the WebGL context.
 */
public class WGLView extends View
{
    /** Provides access to shared rendering bits. */
    public final WGLRenderer renderer;

    public WGLView (Canvas canvas, int width, int height)
    {
        _wctx = (WebGLRenderingContext)canvas.getContext("experimental-webgl");

        canvas.setCoordinateSpaceHeight(width);
        canvas.setCoordinateSpaceWidth(height);
        _wctx.viewport(0, 0, width, height);

        // create our renderer now that the basic viewport is configured
        renderer = new WGLRenderer(_wctx);

        // temp: initialize the view
        _wctx.clearColor(0.0f, 0.0f, 0.0f, 1.0f);
        _wctx.clearDepth(1.0f);
        _wctx.enable(WebGLRenderingContext.DEPTH_TEST);
        _wctx.depthFunc(WebGLRenderingContext.LEQUAL);
    }

    @Override // from View
    public void render ()
    {
        // clear the view
        _wctx.clear(WebGLRenderingContext.COLOR_BUFFER_BIT | WebGLRenderingContext.DEPTH_BUFFER_BIT);

        // render all registered visibles
        renderVisibles();
    }

    @Override // from View
    public Viz newGeomViz (Geometry geom)
    {
        return new WGLViz(geom, new ModelViewShader());
    }

    @Override // from View
    public Viz newTileViz (Tile tile)
    {
        throw new RuntimeException("TODO");
    }

    protected final WebGLRenderingContext _wctx;
}
