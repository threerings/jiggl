//
// $Id$

package com.threerings.jiggl.view;

import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;

import com.threerings.jiggl.util.GeomUtil;

/**
 * Manages renderer state that may be shared across visibles.
 */
public class WGLRenderer extends Renderer
{
    /** Provides access to OpenGL bits. */
    public final WebGLRenderingContext wctx;

    /** Our orthographic projection matrix. */
    public final float[] projMatrix = GeomUtil.createOrthoMatrix(5, 5, 20);

    public WGLRenderer (WebGLRenderingContext wctx)
    {
        this.wctx = wctx;
    }

    @Override // from Renderer
    public Geometry createQuad (final float width, final float height)
    {
        final WebGLBuffer vertBuf = wctx.createBuffer();
        wctx.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertBuf);
        float[] vertices = new float[] {
            0f,    0f,     0f,
            0f,    height, 0f,
            width, 0f,     0f,
            width, height, 0f,
        };
        wctx.bufferData(WebGLRenderingContext.ARRAY_BUFFER, Float32Array.create(vertices),
                        WebGLRenderingContext.STATIC_DRAW);
        final int compPerAttr = 3;
        final int count = vertices.length / compPerAttr;
        return new Geometry(compPerAttr) {
            public float getWidth () {
                return width;
            }
            public float getHeight () {
                return height;
            }
            public void bind (Renderer r) {
                ((WGLRenderer)r).wctx.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertBuf);
            }
            public void draw (Renderer r) {
                ((WGLRenderer)r).wctx.drawArrays(WebGLRenderingContext.TRIANGLE_STRIP, 0, count);
            }
        };
    }
}
