//
// Jiggl Core - 2D game development library
// http://github.com/threerings/jiggl/blob/master/LICENSE

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

    /**
     * Returns the width of this visible, including local scaling transformations (but not
     * rotation). Transformations applied by a parent of this visible are also not included.
     */
    public abstract float getWidth ();

    /**
     * Returns the height of this visible, including local scaling transformations (but not
     * rotation). Transformations applied by a parent of this visible are also not included.
     */
    public abstract float getHeight ();

    /**
     * Returns true if this viz is added to a view, false otherwise.
     */
    public abstract boolean isAdded ();

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
    protected abstract void onRemove ();

    /**
     * Instructs this viz to issue the rendering calls needed to visualize itself.
     */
    protected abstract void render ();
}
