//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.view;

import com.googlecode.gwtgl.binding.WebGLUniformLocation;

/**
 * Provides a basic shader with a model view transform (no texture, no color).
 */
public class ModelViewShader extends Shader
{
    /** The vertex position attribute (used by the geometry when rendering). */
    public int vposAttr;

    /** The uniform variable containing our model view matrix. */
    public WebGLUniformLocation mviewMatrix;

    @Override // from Shader
    public void bind (WGLRenderer r)
    {
        super.bind(r);
        r.wctx.uniformMatrix4fv(_projMatrix, false, r.projMatrix);
    }

    @Override // from Shader
    public boolean init (WGLRenderer r)
    {
        if (!super.init(r)) return false; // already initted
        this.vposAttr = r.wctx.getAttribLocation(_program, VPOS_ATTR);
        r.wctx.enableVertexAttribArray(this.vposAttr);
        _projMatrix = r.wctx.getUniformLocation(_program, PMTX_VAR);
        this.mviewMatrix = r.wctx.getUniformLocation(_program, MVMTX_VAR);
        return true;
    }

    @Override // from Shader
    protected String getVertexSource ()
    {
        String program =
            "attribute vec3 VPOS_ATTR;\n" +
            "uniform mat4 PMTX_VAR;\n" +
            "uniform mat4 MVMTX_VAR;\n" +
            "void main (void) {\n" +
            "   gl_Position = PMTX_VAR * MVMTX_VAR * vec4(VPOS_ATTR, 1.0);\n" +
            "}";
        return program.
            replace("VPOS_ATTR", VPOS_ATTR).
            replace("PMTX_VAR", PMTX_VAR).
            replace("MVMTX_VAR", MVMTX_VAR);
    }

    @Override // from Shader
    protected String getFragmentSource ()
    {
        String program = "#ifdef GL_ES\n" +
            "   precision highp float;\n" +
            "#endif\n" +
            "void main (void) {\n" +
            "   gl_FragColor = vec4(1, 1, 1, 1);\n" +
            "}";
        return program;
    }

    protected WebGLUniformLocation _projMatrix;

    protected static final String VPOS_ATTR = "vertexPos";
    protected static final String PMTX_VAR = "projectionMatrix";
    protected static final String MVMTX_VAR = "modelViewMatrix";
}
