//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.view;

import com.googlecode.gwtgl.binding.WebGLUniformLocation;

/**
 * Shades model-view transformed geometry with a color.
 */
public class ColorShader extends ModelViewShader
{
    /** The uniform variable containing our model color. */
    public WebGLUniformLocation modelColor;

    @Override // from Shader
    public boolean init (WGLRenderer r)
    {
        if (!super.init(r)) return false; // already initted
        this.modelColor = r.wctx.getUniformLocation(_program, MCOLOR_VAR);
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
            "uniform vec4 MCOLOR_VAR;\n" +
            "void main (void) {\n" +
            "   gl_FragColor = MCOLOR_VAR;\n" +
            "}";
        return program.replace("MCOLOR_VAR", MCOLOR_VAR);
    }

    protected static final String MCOLOR_VAR = "modelColor";
}
