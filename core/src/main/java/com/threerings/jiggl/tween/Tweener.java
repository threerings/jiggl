//
// $Id$

package com.threerings.jiggl.tween;

import java.util.ArrayList;
import java.util.List;

import com.threerings.jiggl.util.Driver;
import com.threerings.jiggl.util.Scalar;

/**
 * Handles the animation of viz properties (position, rotation, etc.).
 */
public interface Tweener
{
    /**
     * Creates a tween on the specified value using the supplied interpolator.
     */
    Tween.One tween (Interpolator interp, Scalar value);

    /**
     * Creates a linear tween on the specified value.
     */
    Tween.One linear (Scalar value);

    /**
     * Creates an {@link Interpolator#EASE_IN} tween on the specified value.
     */
    Tween.One easeIn (Scalar value);

    /**
     * Creates an {@link Interpolator#EASE_OUT} tween on the specified value.
     */
    Tween.One easeOut (Scalar value);

    /**
     * Creates an {@link Interpolator#EASE_INOUT} tween on the specified value.
     */
    Tween.One easeInOut (Scalar value);

    /**
     * Creates a tween on the specified values using the supplied interpolator.
     */
    Tween.Two tween (Interpolator interp, Scalar x, Scalar y);

    /**
     * Creates a linear tween on the specified values.
     */
    Tween.Two linear (Scalar x, Scalar y);

    /**
     * Creates an {@link Interpolator#EASE_IN} tween on the specified values.
     */
    Tween.Two easeIn (Scalar x, Scalar y);

    /**
     * Creates an {@link Interpolator#EASE_OUT} tween on the specified values.
     */
    Tween.Two easeOut (Scalar x, Scalar y);

    /**
     * Creates an {@link Interpolator#EASE_INOUT} tween on the specified values.
     */
    Tween.Two easeInOut (Scalar x, Scalar y);

    /** Implementation details, avert your eyes. */
    public static class Impl implements Tweener, Driver.Tickable {
        public Impl (Driver driver) {
            driver.addTickable(this);
        }

        public Tween.One tween (Interpolator interp, Scalar value) {
            Tween.One tween = new Tween.One(interp, value);
            _ntweens.add(tween);
            return tween;
        }
        public Tween.One linear (Scalar value) {
            return tween(Interpolator.LINEAR, value);
        }
        public Tween.One easeIn (Scalar value) {
            return tween(Interpolator.EASE_IN, value);
        }
        public Tween.One easeOut (Scalar value) {
            return tween(Interpolator.EASE_OUT, value);
        }
        public Tween.One easeInOut (Scalar value) {
            return tween(Interpolator.EASE_INOUT, value);
        }

        public Tween.Two tween (Interpolator interp, Scalar x, Scalar y) {
            Tween.Two tween = new Tween.Two(interp, x, y);
            _ntweens.add(tween);
            return tween;
        }
        public Tween.Two linear (Scalar x, Scalar y) {
            return tween(Interpolator.LINEAR, x, y);
        }
        public Tween.Two easeIn (Scalar x, Scalar y) {
            return tween(Interpolator.EASE_IN, x, y);
        }
        public Tween.Two easeOut (Scalar x, Scalar y) {
            return tween(Interpolator.EASE_OUT, x, y);
        }
        public Tween.Two easeInOut (Scalar x, Scalar y) {
            return tween(Interpolator.EASE_INOUT, x, y);
        }

        public boolean tick (float time) {
            if (!_ntweens.isEmpty()) {
                for (int ii = 0, ll = _ntweens.size(); ii < ll; ii++) {
                    _ntweens.get(ii).init(time);
                }
                _tweens.addAll(_ntweens);
                _ntweens.clear();
            }
            for (int ii = 0, ll = _tweens.size(); ii < ll; ii++) {
                if (_tweens.get(ii).apply(time)) {
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
