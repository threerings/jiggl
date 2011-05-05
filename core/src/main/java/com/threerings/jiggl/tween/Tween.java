//
// Jiggl Core - 2D game development library
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.tween;

import com.threerings.jiggl.util.Scalar;

/**
 * Manages the animation of a particular property of a visible.
 */
public abstract class Tween
{
    /** A tween that animates a single scalar value. */
    public static class One extends Interped
    {
        public One (Interpolator interp, Scalar value) {
            super(interp);
            _value = value;
        }

        /** Configures the starting value. Default: the value of the scalar at the time that the
         * tween begins. */
        public One from (float value) {
            _from = value;
            return this;
        }

        /** Configures the ending value. Default: 0. */
        public One to (float value) {
            _to = value;
            return this;
        }

        @Override
        protected void init (float time) {
            super.init(time);
            if (_from == Float.MIN_VALUE) _from = _value.value;
        }

        @Override
        protected boolean apply (float time) {
            float dt = time-_start;
            if (dt < _duration) {
                _value.value = _interp.apply(_from, _to-_from, dt, _duration);
                return false;
            } else {
                _value.value = _to;
                return true;
            }
        }

        protected final Scalar _value;
        protected float _from = Float.MIN_VALUE;
        protected float _to;
    }

    /** A tween that animates a pair of scalar values (usually a position). */
    public static class Two extends Interped
    {
        public Two (Interpolator interp, Scalar x, Scalar y) {
            super(interp);
            _x = x;
            _y = y;
        }

        /** Configures the starting values. Default: the values of the scalar at the time that the
         * tween begins. */
        public Two from (float fromx, float fromy)
        {
            _fromx = fromx;
            _fromy = fromy;
            return this;
        }

        /** Configures the ending values. Default: (0, 0). */
        public Two to (float tox, float toy)
        {
            _tox = tox;
            _toy = toy;
            return this;
        }

        @Override
        protected void init (float time) {
            super.init(time);
            if (_fromx == Float.MIN_VALUE) _fromx = _x.value;
            if (_fromy == Float.MIN_VALUE) _fromy = _y.value;
        }

        @Override
        protected boolean apply (float time) {
            float dt = time-_start;
            if (dt < _duration) {
                _x.value = _interp.apply(_fromx, _tox-_fromx, dt, _duration);
                _y.value = _interp.apply(_fromy, _toy-_fromy, dt, _duration);
                return false;
            } else {
                _x.value = _tox;
                _y.value = _toy;
                return true;
            }
        }

        protected final Scalar _x, _y;
        protected float _fromx = Float.MIN_VALUE, _fromy = Float.MIN_VALUE;
        protected float _tox, _toy;
    }

    /** A tween that simply delays a specified number of seconds. */
    public static class Delay extends Tween
    {
        public Delay (float duration) {
            _duration = duration;
        }

        @Override
        protected boolean apply (float time) {
            return (time-_start >= _duration);
        }

        @Override
        protected float getOverrun (float time) {
            return (time - _start) - _duration;
        }

        protected final float _duration;
    }

    /** A tween that executes an action and completes immediately. */
    public static class Action extends Tween
    {
        public Action (Runnable action) {
            _action = action;
        }

        @Override
        protected boolean apply (float time) {
            _action.run();
            return true;
        }

        protected Runnable _action;
    }

    /** A tween that repeats its underlying tween over and over again (until removed). */
    public static class Repeat extends Tween
    {
        /** Cancels this repeating tween. */
        public void cancel () {
            _cancelled = true;
        }

        @Override
        protected void init (float time) {
            // a normal tween will have _current initialized to itself; we want to skip ourselves
            // and go right to our to-be-repeated tween, and initialize it immediately
            _current = _next;
            _current.init(time);
        }

        @Override
        protected boolean apply (float time) {
            return false; // not used
        }

        @Override
        protected boolean apply (Tweener tweener, float time)
        {
            // if we're canceled, stop now
            if (_cancelled) return true;

            // if our current chain of tweens is still running, keep going
            if (!super.apply(tweener, time)) return false;

            // otherwise, reset to the head of the chain and keep going
            float overrun = _current.getOverrun(time);
            _current = _next;
            _current.init(time-overrun);
            return false;
        }

        protected boolean _cancelled = false;
    }

    /**
     * Returns a tween factory for constructing a tween that will be queued up for execution when
     * the current tween is completes.
     */
    public Tweener then ()
    {
        if (_next != null) {
            throw new IllegalStateException("This tween already has a 'then' tween.");
        }
        return new Tweener() {
            protected <T extends Tween> T register (T tween) {
                _next = tween;
                return tween;
            }
        };
    }

    protected Tween ()
    {
    }

    protected void init (float time)
    {
        _start = time;
    }

    protected boolean apply (Tweener tweener, float time)
    {
        // if the current tween is still running, keep going
        if (!_current.apply(time)) return false;

        // if the current tween is finished and we have no next tween, then we're done
        if (_current._next == null) return true;

        // otherwise initialize our next tween (accounting for any overrun on our current tween)
        // and keep going
        float overrun = _current.getOverrun(time);
        _current = _current._next;
        _current.init(time-overrun);
        return false;
    }

    protected abstract boolean apply (float time);

    /**
     * Returns the amount of time this tween has overrun its duration, given the supplied current
     * timestamp. The result may be negative if the tween is not complete.
     */
    protected float getOverrun (float time)
    {
        return 0f;
    }

    protected static abstract class Interped extends Tween
    {
        /**
         * Configures the duration over which this animation takes place (in seconds). Default: 1.
         */
        public Tween in (float duration) {
            _duration = duration;
            return this;
        }

        protected Interped (Interpolator interp) {
            _interp = interp;
        }

        @Override
        protected float getOverrun (float time) {
            return (time - _start) - _duration;
        }

        protected final Interpolator _interp;
        protected float _duration = 1;
    }

    protected float _start;
    protected Tween _current = this;
    protected Tween _next;
}
