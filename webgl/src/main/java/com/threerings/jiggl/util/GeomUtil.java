//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.util;

import com.google.gwt.core.client.GWT;

/**
 * Geometry-related utility methods.
 */
public class GeomUtil
{
    /**
     * Creates an orthographic projection matrix which will display pixels in the ranges:
     * [0, width), [0, height) and [-depth/2, depth/2).
     */
    public static float[] createOrthoMatrix (int width, int height, int depth)
    {
        // see http://www.khronos.org/opengles/documentation/opengles1_0/html/glOrtho.html
        float left = 0, right = width, top = height, bottom = 0, near = -depth/2, far = depth/2;
        float tx = -(right+left)/(right-left);
        float ty = -(top+bottom)/(top-bottom);
        float tz = -(far+near)/(far-near);
        return new float[] { 2/(right-left), 0,              0,            0,
                             0,              2/(top-bottom), 0,            0,
                             0,              0,             -2/(far-near), 0,
                             tx,             ty,             tz,           1 };
    }

    /**
     * Creates a perspective projection matrix. Not used, but here for reference.
     */
    public static float[] createPerspectiveMatrix (
        int fieldOfViewVertical, float aspectRatio, float minimumClearance, float maximumClearance)
    {
        float top    = minimumClearance * (float)Math.tan(fieldOfViewVertical * Math.PI / 360.0);
        float bottom = -top;
        float left   = bottom * aspectRatio;
        float right  = top * aspectRatio;

        float X = 2*minimumClearance/(right-left);
        float Y = 2*minimumClearance/(top-bottom);
        float A = (right+left)/(right-left);
        float B = (top+bottom)/(top-bottom);
        float C = -(maximumClearance+minimumClearance)/(maximumClearance-minimumClearance);
        float D = -2*maximumClearance*minimumClearance/(maximumClearance-minimumClearance);

        return new float[] { X,  0f, A,  0f,
                             0f, Y,  B,  0f,
                             0f, 0f, C, -1f,
                             0f, 0f, D,  0f};
    }
}
