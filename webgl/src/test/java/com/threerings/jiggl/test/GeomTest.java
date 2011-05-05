//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.test;

import com.google.gwt.core.client.GWT;
import com.threerings.jiggl.Context;
import com.threerings.jiggl.util.Color;
import com.threerings.jiggl.view.Geometry;
import com.threerings.jiggl.view.Viz;

/**
 * Tests the display of simple geometries.
 */
public class GeomTest
{
    public void execute (final Context ctx)
    {
        final Geometry quad = ctx.renderer.createQuad(1, 1);
        final Viz quad0 = ctx.view.add(ctx.view.newGeomViz(quad));
        quad0.color = new Color(1, 0, 0);
        final Viz quad1 = ctx.view.add(ctx.view.newGeomViz(quad));
        quad1.color = new Color(0, 1, 0);
        final Viz quad2 = ctx.view.add(ctx.view.newGeomViz(quad));
        quad2.color = new Color(0, 0, 1);
        final Viz quad3 = ctx.view.add(ctx.view.newGeomViz(quad));
        quad3.color = new Color(0, 1, 1);
        final Viz quad4 = ctx.view.add(ctx.view.newGeomViz(quad));
        quad4.color = new Color(1, 1, 0);

        ctx.tweener.linear(quad0.x).to(2).in(2).then().delay(1).then().action(new Runnable() {
            public void run () {
                ctx.view.remove(quad0);
            }
        });
        ctx.tweener.easeOut(quad1.x, quad1.y).to(1, 1).in(2);
        ctx.tweener.easeInOut(quad2.y).to(3).in(1).
            then().easeInOut(quad2.x).to(2).in(1);
        ctx.tweener.linear(quad2.scaleX, quad2.scaleY).to(0.5f, 0.5f).in(2);

        quad3.x.value = 3;
        quad3.y.value = 3;
        ctx.tweener.repeat().linear(quad3.rotation).to(2*(float)Math.PI).in(2).
            then().linear(quad3.rotation).to(0).in(0);

        quad4.x.value = 3;
        quad4.y.value = 3;
    }
}
