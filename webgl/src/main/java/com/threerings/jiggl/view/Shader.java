//
// $Id$

package com.threerings.jiggl.view;

import com.googlecode.gwtgl.binding.WebGLProgram;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLShader;

import com.threerings.jiggl.util.MatrixUtil;

/**
 * Manages fragment and vertex shader programs, their compilation and the binding of their uniform
 * variables.
 */
public abstract class Shader
{
    /**
     * Initializes this shader.
     * @return true if initialization took place, false if this shader was already initialized and
     * a reference count was increased.
     */
    public boolean init (WGLRenderer r)
    {
        if (_refcount++ > 0) return false; // already initialized

        _program = r.wctx.createProgram();

        _vertShader = getShader(r.wctx, WebGLRenderingContext.VERTEX_SHADER, getVertexSource());
        r.wctx.attachShader(_program, _vertShader);

        _fragShader = getShader(r.wctx, WebGLRenderingContext.FRAGMENT_SHADER, getFragmentSource());
        r.wctx.attachShader(_program, _fragShader);

        r.wctx.linkProgram(_program);
        if (!r.wctx.getProgramParameterb(_program, WebGLRenderingContext.LINK_STATUS)) {
            delete(r);
            throw new RuntimeException("Could not initialise shaders");
        }

        return true;
    }

    /**
     * Deletes the resources associated with this shader program.
     */
    public void delete (WGLRenderer r)
    {
        if (--_refcount > 0) return; // still in use by another viz
        if (_program != null) r.wctx.deleteProgram(_program);
        if (_vertShader != null) r.wctx.deleteShader(_vertShader);
        if (_fragShader != null) r.wctx.deleteShader(_fragShader);
    }

    protected void bind (WGLRenderer r)
    {
        r.wctx.useProgram(_program);
    }

    /**
     * Returns the source to the vertex shader.
     */
    protected abstract String getVertexSource ();

    /**
     * Returns the source to the fragment shader.
     */
    protected abstract String getFragmentSource ();

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

    protected int _refcount;
    protected WebGLShader _vertShader, _fragShader;
    protected WebGLProgram _program;
}
