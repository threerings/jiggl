//
// Jiggl Core - 2D game development library
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.tween;

import java.util.ArrayList;
import java.util.List;

import com.threerings.jiggl.util.Driver;
import com.threerings.jiggl.util.Scalar;

/**
 * Handles the animation of viz properties (position, rotation, etc.).
 */
public abstract class Tweener
{
    /**
     * Creates a tween on the specified value using the supplied interpolator.
     */
    public Tween.One tween (Interpolator interp, Scalar value)
    {
        return register(new Tween.One(interp, value));
    }

    /**
     * Creates a linear tween on the specified value.
     */
    public Tween.One linear (Scalar value)
    {
        return tween(Interpolator.LINEAR, value);
    }

    /**
     * Creates an {@link Interpolator#EASE_IN} tween on the specified value.
     */
    public Tween.One easeIn (Scalar value)
    {
        return tween(Interpolator.EASE_IN, value);
    }

    /**
     * Creates an {@link Interpolator#EASE_OUT} tween on the specified value.
     */
    public Tween.One easeOut (Scalar value)
    {
        return tween(Interpolator.EASE_OUT, value);
    }

    /**
     * Creates an {@link Interpolator#EASE_INOUT} tween on the specified value.
     */
    public Tween.One easeInOut (Scalar value)
    {
        return tween(Interpolator.EASE_INOUT, value);
    }

    /**
     * Creates a tween on the specified values using the supplied interpolator.
     */
    public Tween.Two tween (Interpolator interp, Scalar x, Scalar y)
    {
        return register(new Tween.Two(interp, x, y));
    }

    /**
     * Creates a linear tween on the specified values.
     */
    public Tween.Two linear (Scalar x, Scalar y)
    {
        return tween(Interpolator.LINEAR, x, y);
    }

    /**
     * Creates an {@link Interpolator#EASE_IN} tween on the specified values.
     */
    public Tween.Two easeIn (Scalar x, Scalar y)
    {
        return tween(Interpolator.EASE_IN, x, y);
    }

    /**
     * Creates an {@link Interpolator#EASE_OUT} tween on the specified values.
     */
    public Tween.Two easeOut (Scalar x, Scalar y)
    {
        return tween(Interpolator.EASE_OUT, x, y);
    }

    /**
     * Creates an {@link Interpolator#EASE_INOUT} tween on the specified values.
     */
    public Tween.Two easeInOut (Scalar x, Scalar y)
    {
        return tween(Interpolator.EASE_INOUT, x, y);
    }

    /**
     * Creates a tween that delays for the specified number of seconds.
     */
    public Tween.Delay delay (float seconds)
    {
        return register(new Tween.Delay(seconds));
    }

    /**
     * Returns a tweener which can be used to construct a tween that will be repeated until
     * canceled.
     */
    public Tweener repeat ()
    {
        return register(new Tween.Repeat()).then();
    }

    /**
     * Creates a tween that executes the supplied runnable and immediately completes.
     */
    public Tween.Action action (Runnable action)
    {
        return register(new Tween.Action(action));
    }

    protected abstract <T extends Tween> T register (T tween);

    /** Implementation details, avert your eyes. */
    public static class Impl extends Tweener implements Driver.Tickable {
        public Impl (Driver driver) {
            driver.addTickable(this);
        }

        @Override // from Tweener
        protected <T extends Tween> T register (T tween) {
            _ntweens.add(tween);
            return tween;
        }

        // from interface Driver.Tickable
        public boolean tick (float time) {
            if (!_ntweens.isEmpty()) {
                for (int ii = 0, ll = _ntweens.size(); ii < ll; ii++) {
                    _ntweens.get(ii).init(time);
                }
                _tweens.addAll(_ntweens);
                _ntweens.clear();
            }
            for (int ii = 0, ll = _tweens.size(); ii < ll; ii++) {
                if (_tweens.get(ii).apply(this, time)) {
                    _tweens.remove(ii--);
                    ll -= 1;
                }
            }
            return true;
        }

        protected List<Tween> _tweens = new ArrayList<Tween>();
        protected List<Tween> _ntweens = new ArrayList<Tween>();
    }
}
