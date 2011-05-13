//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.view;

import com.googlecode.gwtgl.binding.WebGLUniformLocation;

import com.threerings.jiggl.util.MatrixUtil;

/**
 * Provides a basic shader with a model view transform and a texture.
 */
public class TextureShader extends ModelViewShader
{
    /** The texture coordinates attribute (used by the geometry when rendering). */
    public int tcoordAttr;

    @Override // from Shader
    public boolean init (WGLRenderer r)
    {
        if (!super.init(r)) return false; // already initted
        this.tcoordAttr = r.wctx.getAttribLocation(_program, TCOORD_ATTR);
        r.wctx.enableVertexAttribArray(this.tcoordAttr);
//         _textureUnit = r.wctx.getUniformLocation(_program, TUNIT_VAR);
        return true;
    }

    @Override // from Shader
    public void bind (WGLRenderer r)
    {
        super.bind(r);
//        r.wctx.uniform1i(_textureUnit, 0);
    }

    @Override // from Shader
    protected String getVertexSource ()
    {
        String program =
            "attribute vec3 VPOS_ATTR;\n" +
            "attribute vec2 TCOORD_ATTR;\n" +
            "uniform mat4 PMTX_VAR;\n" +
            "uniform mat4 MVMTX_VAR;\n" +
            "varying vec2 vTextureCoord;\n" +
            "void main (void) {\n" +
            "   gl_Position = PMTX_VAR * MVMTX_VAR * vec4(VPOS_ATTR, 1.0);\n" +
            "}";
        return program.
            replace("VPOS_ATTR", VPOS_ATTR).
            replace("TCOORD_ATTR", TCOORD_ATTR).
            replace("PMTX_VAR", PMTX_VAR).
            replace("MVMTX_VAR", MVMTX_VAR);
    }

    @Override // from Shader
    protected String getFragmentSource ()
    {
        String program = "#ifdef GL_ES\nprecision highp float;\n#endif\n" +
            "varying vec2 vTextureCoord;\n" +
//             "uniform sampler2D TUNIT_VAR;\n" +
            "void main (void) {\n" +
//             "   gl_FragColor = texture2D(TUNIT_VAR, vec2(vTextureCoord.s, vTextureCoord.t));\n" +
            "   gl_FragColor = texture2D(0, vec2(vTextureCoord.s, vTextureCoord.t));\n" +
            "}";
        return program.replace("TUNIT_VAR", TUNIT_VAR);
    }

//     protected WebGLUniformLocation _textureUnit;

    protected static final String TCOORD_ATTR = "textureCoord";
    protected static final String TUNIT_VAR = "textureUnit";
}
