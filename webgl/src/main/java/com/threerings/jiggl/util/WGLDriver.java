//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.util;

import com.google.gwt.user.client.Timer;

import com.threerings.jiggl.view.WGLView;

/**
 * Drives a Jiggl/WebGL application.
 */
public class WGLDriver extends Driver
{
    public WGLDriver (WGLView view, int freqMillis)
    {
        super(view);
        new Timer() {
            public void run () {
                tick((System.currentTimeMillis()-_start)/1000f);
            }
        }.scheduleRepeating(freqMillis);
    }

    protected long _start = System.currentTimeMillis();
}
