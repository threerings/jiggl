//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.view;

import com.googlecode.gwtgl.binding.WebGLRenderingContext;

import com.threerings.jiggl.rsrc.Tile;

/**
 * Renders a tile (a textured quad).
 */
public class TileViz extends WGLViz
{
    public TileViz (WGLRenderer renderer, Tile tile)
    {
        // TODO: reuse quads since we'll likely have many of equal size?
        super(renderer.createQuad(tile.getWidth(), tile.getHeight()), new TextureShader());
        _tile = tile;
    }

    @Override // from WGLViz
    protected void bindAttributes (WGLRenderer wr)
    {
        super.bindAttributes(wr);
        wr.wctx.vertexAttribPointer(((TextureShader)_shader).tcoordAttr, TODO,
                                    WebGLRenderingContext.FLOAT, false, 0, 0);
    }

    protected Tile _tile;
}
