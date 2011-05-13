//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.view;

import com.google.gwt.core.client.GWT;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLUniformLocation;

import com.threerings.jiggl.util.MatrixUtil;

/**
 * A base class for WebGL visibles.
 */
public class WGLViz extends Viz
{
    public WGLViz (Geometry geom, ModelViewShader shader)
    {
        _geom = geom;
        _shader = shader;
    }

    @Override // from Viz
    public float getWidth ()
    {
        return _geom.getWidth() * scaleX.value;
    }

    @Override // from Viz
    public float getHeight ()
    {
        return _geom.getHeight() * scaleY.value;
    }

    @Override // from Viz
    public boolean isAdded ()
    {
        return (_view != null);
    }

    @Override // from Viz
    protected void onAdd (View view)
    {
        _view = (WGLView)view;
        _shader.init(_view.renderer);
    }

    @Override // from Viz
    protected void onRemove ()
    {
        _shader.delete(_view.renderer);
        _view = null;
    }

    @Override // from Viz
    protected void render ()
    {
        WGLRenderer wr = _view.renderer;

        _shader.bind(wr);
        bindUniforms(wr);
        _geom.bind(wr);
        bindAttributes(wr);
        _geom.draw(wr);
    }

    protected void bindUniforms (WGLRenderer wr)
    {
        float[] transform = MatrixUtil.transform4(
            x.value, y.value, 0, scaleX.value, scaleY.value, 1, rotation.value);
        wr.wctx.uniformMatrix4fv(_shader.mviewMatrix, false, transform);
    }

    protected void bindAttributes (WGLRenderer wr)
    {
        wr.wctx.vertexAttribPointer(
            _shader.vposAttr, _geom.compPerAttr, WebGLRenderingContext.FLOAT, false, 0, 0);
    }

    protected WGLView _view;
    protected Geometry _geom;
    protected ModelViewShader _shader;

    protected static int HACK = 0;
}
