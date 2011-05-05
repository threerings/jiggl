//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.util;

/**
 * Matrix-related utilities. Because WebGL prefers its matrices in flattened column-major
 * one-dimensional float arrays, that's what we use here.
 */
public class MatrixUtil
{
    /**
     * Returns the 3x3 identity matrix.
     */
    public static float[] identity3 ()
    {
        return new float[] { 1, 0, 0,
                             0, 1, 0,
                             0, 0, 1 };
    }

    /**
     * Returns the 4x4 identity matrix.
     */
    public static float[] identity4 ()
    {
        return new float[] { 1, 0, 0, 0,
                             0, 1, 0, 0,
                             0, 0, 1, 0,
                             0, 0, 0, 1 };
    }

    /**
     * Computes a transformation matrix with the supplied translation, scale and rotation factors.
     */
    public static float[] transform4 (float dx, float dy, float dz,
                                      float sx, float sy, float sz, float theta)
    {
        float cos = (float)Math.cos(theta), sin = (float)Math.sin(theta);
        return new float[] { cos*sx, sin,    0,  0,
                             -sin,   cos*sy, 0,  0,
                             0,      0,      sz, 0,
                             dx,     dy,     dz, 1 };
    }

    /**
     * Returns a 4x4 translation matrix.
     */
    public static float[] translation (float dx, float dy, float dz)
    {
        return translate(identity4(), dx, dy, dz);
    }

    /**
     * Adds the supplied translations to the supplied 4x4 matrix. Returns the same matrix for
     * convenience.
     */
    public static float[] translate (float[] matrix, float dx, float dy, float dz)
    {
        matrix[M03] += dx;
        matrix[M13] += dy;
        matrix[M23] += dz;
        return matrix;
    }

    /**
     * Returns a 4x4 scaling matrix.
     */
    public static float[] scale (float sx, float sy)
    {
        return new float[] { sx, 0, 0, 0,
                             0, sy, 0, 0,
                             0,  0, 1, 0,
                             0,  0, 0, 1 };
    }

    /**
     * Returns a 4x4 rotation matrix (around the z axis).
     */
    public static float[] rotation (float theta)
    {
        float cos = (float)Math.cos(theta), sin = (float)Math.sin(theta);
        return new float[] { cos,  sin, 0, 0,
                             -sin, cos, 0, 0,
                             0,    0,   1, 0,
                             0,    0,   0, 1 };
    }

    // Position constants for 4x4 matrix of the form MRC where R = row and C = column
    protected static final int M00 = 4*0+0, M01 = 4*1+0, M02 = 4*2+0, M03 = 4*3+0;
    protected static final int M10 = 4*0+1, M11 = 4*1+1, M12 = 4*2+1, M13 = 4*3+1;
    protected static final int M20 = 4*0+2, M21 = 4*1+2, M22 = 4*2+2, M23 = 4*3+2;
    protected static final int M30 = 4*0+3, M31 = 4*1+3, M32 = 4*2+3, M33 = 4*3+3;
}
