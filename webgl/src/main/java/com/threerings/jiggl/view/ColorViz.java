//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.view;

import com.threerings.jiggl.util.Color;

/**
 * Renders geometry with a color.
 */
public class ColorViz extends WGLViz
{
    public ColorViz (Geometry geom, Color color)
    {
        super(geom, new ColorShader());
        _color = color;
    }

    @Override // from WGLViz
    protected void bindUniforms (WGLRenderer wr)
    {
        super.bindUniforms(wr);
        wr.wctx.uniform4fv(((ColorShader)_shader).modelColor, _color.toVec4fv());
    }

    protected Color _color;
}
