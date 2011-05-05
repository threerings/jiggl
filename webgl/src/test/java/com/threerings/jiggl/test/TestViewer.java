//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.test;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

import com.threerings.jiggl.Context;
import com.threerings.jiggl.WebGL;

/**
 * The main entry point for our Jiggl/WebGL test viewer.
 */
public class TestViewer implements EntryPoint
{
    // from interface EntryPoint
    public void onModuleLoad ()
    {
        Canvas canvas = Canvas.createIfSupported();
        canvas.setWidth("500px");
        canvas.setHeight("500px");
        RootPanel.get("client").add(canvas);
        Context ctx = WebGL.create(canvas);
        new GeomTest().execute(ctx);
    }
}
