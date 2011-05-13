//
// Jiggl Core - 2D game development library
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.view;

import java.util.ArrayList;
import java.util.List;

import com.threerings.jiggl.rsrc.Tile;
import com.threerings.jiggl.util.Color;

/**
 * Coordinates the display and animation of a collection of {@link Viz} instances.
 */
public abstract class View
{
    /**
     * Adds the supplied viz to this view. Returns the added viz for convenient chaining.
     */
    public <T extends Viz> T add (T viz)
    {
        viz.onAdd(this);
        _vizs.add(viz);
        return viz;
    }

    /**
     * Removes the specified viz from this view.
     */
    public void remove (Viz viz)
    {
        _vizs.remove(viz);
        viz.onRemove();
    }

    /**
     * Removes all registered visibles from this view.
     */
    public void clear ()
    {
        for (Viz v : _vizs) {
            v.onRemove();
        }
        _vizs.clear();
    }

    /**
     * Executes this view's rendering commands.
     */
    public abstract void render ();

    /**
     * Creates a visible that renders the supplied geometry using the supplied color.
     */
    public abstract Viz newGeomViz (Geometry geom, Color color);

    /**
     * Creates a visible to render the supplied tile.
     */
    public abstract Viz newTileViz (Tile tile);

    /**
     * Renders the visibles registered with this view.
     */
    protected void renderVisibles ()
    {
        for (int ii = 0, ll = _vizs.size(); ii < ll; ii++) {
            _vizs.get(ii).render();
        }
    }

    /** A list of our active visibles. */
    protected List<Viz> _vizs = new ArrayList<Viz>();
}
