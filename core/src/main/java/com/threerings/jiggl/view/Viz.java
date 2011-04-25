//
// $Id$

package com.threerings.jiggl.view;

import com.threerings.jiggl.util.Scalar;

/**
 * A visible entity, which has base dimensions and a transform matrix, and which knows how to
 * render itself. The lifecycle of a viz is a call to {@link #onAdd}, zero or more calls to {@link
 * #render}, followed by a call to {@link #onRemove}. This lifecycle may be repeated for a viz that
 * is added to and removed from a view repeatedly.
 */
public abstract class Viz
{
    /** The x-component of our (container relative) translation. */
    public final Scalar x = new Scalar(0);

    /** The y-component of our (container relative) translation. */
    public final Scalar y = new Scalar(0);

    /** The x-component of our scale. */
    public final Scalar scaleX = new Scalar(1);

    /** The y-component of our scale. */
    public final Scalar scaleY = new Scalar(1);

    /** The rotation of this visible, in radians. */
    public final Scalar rotation = new Scalar(0);

    /** Returns the base width of this visible (in pixels). The displayed width may be modified by
     * the transform matrix. */
    public abstract int getWidth ();

    /** Returns the base height of this visible (in pixels). The displayed height may be modified
     * by the transform matrix. */
    public abstract int getHeight ();

    /**
     * Called when this viz is added to a view. The viz should prepare any buffers or shader
     * programs it will need to render itself. Any exception thrown by this method will result in
     * the viz not being added to the view, so the viz should ensure that partially created
     * resources are cleaned up if it throws such an exception.
     */
    protected abstract void onAdd (View view);

    /**
     * Called when this viz is removed from a view. The viz should destroy any buffers or shader
     * programs it created in {@link #onAdd}.
     */
    protected abstract void onRemove (View view);

    /**
     * Instructs this viz to issue the rendering calls needed to visualize itself.
     */
    protected abstract void render (View view);
}
