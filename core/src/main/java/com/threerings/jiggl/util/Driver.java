//
// $Id$

package com.threerings.jiggl.util;

import java.util.ArrayList;
import java.util.List;

import com.threerings.jiggl.view.View;

/**
 * Drives the time-based elements of a Jiggl application (animations and whatnot).
 */
public abstract class Driver
{
    /** An interface implemented by things that wish to tick. */
    public interface Tickable {
        /** Called on every frame tick with the time elapsed since app start, in seconds.
         * @return true if the tickable should remain registered, false otherwise. */
        boolean tick (float time);
    }

    /**
     * Registers a new tickable with the driver. The tickable may only be removed by returning
     * false from its {@link Tickable#tick} method.
     */
    public void addTickable (Tickable tickable)
    {
        _tickables.add(tickable);
    }

    /**
     * Ticks all registered tickables, then re-renders the view.
     */
    protected void tick (float time)
    {
        for (int ii = 0, ll = _tickables.size(); ii < ll; ii++) {
            if (!_tickables.get(ii).tick(time)) {
                _tickables.remove(ii--);
                ll -= 1;
            }
        }
        _view.render();
    }

    protected Driver (View view)
    {
        _view = view;
    }

    protected final View _view;
    protected final List<Tickable> _tickables = new ArrayList<Tickable>();
}
