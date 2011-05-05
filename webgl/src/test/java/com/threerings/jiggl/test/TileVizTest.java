//
// Jiggl WebGL - GWT + WebGL backend for 2D game framework
// http://github.com/threerings/jiggl/blob/master/LICENSE

package com.threerings.jiggl.test;

import com.google.gwt.core.client.GWT;
import com.threerings.jiggl.Context;
import com.threerings.jiggl.rsrc.TileSet;
import com.threerings.jiggl.util.Color;
import com.threerings.jiggl.view.Viz;

/**
 * Displays some tiles and moves them around.
 */
public class TileVizTest
{
    public void execute (final Context ctx)
    {
        final TileSet tiles = ctx.tiles.load("testtiles.png");
        final Viz tile0 = ctx.view.add(ctx.view.newTileViz(tiles.get(0)));
        tile0.color = new Color(1, 0, 0);
        final Viz tile1 = ctx.view.add(ctx.view.newTileViz(tiles.get(1)));
        tile1.color = new Color(0, 1, 0);
        final Viz tile2 = ctx.view.add(ctx.view.newTileViz(tiles.get(2)));
        tile2.color = new Color(0, 0, 1);
        final Viz tile3 = ctx.view.add(ctx.view.newTileViz(tiles.get(3)));
        tile3.color = new Color(0, 1, 1);
        final Viz tile4 = ctx.view.add(ctx.view.newTileViz(tiles.get(4)));
        tile4.color = new Color(1, 1, 0);

        ctx.tweener.linear(tile0.x).to(2).in(2).then().delay(1).then().action(new Runnable() {
            public void run () {
                ctx.view.remove(tile0);
            }
        });
        ctx.tweener.easeOut(tile1.x, tile1.y).to(1, 1).in(2);
        ctx.tweener.easeInOut(tile2.y).to(2).in(1).
            then().easeInOut(tile2.x).to(2).in(1);
        ctx.tweener.linear(tile2.scaleX, tile2.scaleY).to(0.5f, 0.5f).in(2);

        tile3.x.value = 3;
        tile3.y.value = 3;
        ctx.tweener.linear(tile3.rotation).to((float)Math.PI).in(2);

        tile4.x.value = 3;
        tile4.y.value = 3;
    }
}
