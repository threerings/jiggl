//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.view;

import com.google.gwt.core.client.GWT;
import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLProgram;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLShader;
import com.googlecode.gwtgl.binding.WebGLUniformLocation;

import com.threerings.jiggl.util.GeomUtil;
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
    protected void onAdd (View view)
    {
        WGLRenderer r = ((WGLView)view).renderer;
        _shader.init(r);
    }

    @Override // from Viz
    protected void onRemove (View view)
    {
        WGLRenderer r = ((WGLView)view).renderer;
        _shader.delete(r);
    }

    @Override // from Viz
    protected void render (View view)
    {
        WGLRenderer wr = ((WGLView)view).renderer;

        float[] transform = MatrixUtil.transform4(
            x.value, y.value, 0, scaleX.value, scaleY.value, 1, rotation.value);

        _shader.bind(wr);
        wr.wctx.uniformMatrix4fv(_shader.mviewMatrix, false, transform);
        if (this.color != null) {
            wr.wctx.uniform4fv(_shader.modelColor, this.color.toVec4fv());
        }
        _geom.bind(wr);
        wr.wctx.vertexAttribPointer(
            _shader.vposAttr, _geom.compPerAttr, WebGLRenderingContext.FLOAT, false, 0, 0);
        _geom.draw(wr);
    }

    protected static WebGLShader getShader (WebGLRenderingContext wctx, int type, String source)
    {
        WebGLShader shader = wctx.createShader(type);
        wctx.shaderSource(shader, source);
        wctx.compileShader(shader);
        if (!wctx.getShaderParameterb(shader, WebGLRenderingContext.COMPILE_STATUS)) {
            throw new RuntimeException(wctx.getShaderInfoLog(shader));
        }
        return shader;
    }

    protected Geometry _geom;
    protected ModelViewShader _shader;

    protected static int HACK = 0;
}
