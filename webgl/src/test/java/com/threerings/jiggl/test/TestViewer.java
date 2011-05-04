//
// $Id$

package com.threerings.jiggl.test;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.ui.RootPanel;

import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLCanvas;
import com.googlecode.gwtgl.binding.WebGLProgram;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLShader;
import com.googlecode.gwtgl.binding.WebGLUniformLocation;

import com.threerings.jiggl.Context;
import com.threerings.jiggl.WebGL;

/**
 * The main entry point for our Jiggl/WebGL test viewer.
 */
public class TestViewer implements EntryPoint
{
    private WebGLRenderingContext glContext;
    private WebGLProgram shaderProgram;
    private int vertexPositionAttribute;
    private WebGLBuffer vertexBuffer;

    // from interface EntryPoint
    public void onModuleLoad ()
    {
        Canvas canvas = Canvas.createIfSupported();
        canvas.setWidth("500px");
        canvas.setHeight("500px");
        RootPanel.get("client").add(canvas);
        Context ctx = WebGL.create(canvas);
        new GeomTest().execute(ctx);

//         final WebGLCanvas webGLCanvas = new WebGLCanvas(
//         glContext = webGLCanvas.getGlContext();
//         glContext.viewport(0, 0, 500, 500);
//         start();
    }

    private void start() {
        initShaders();
        glContext.clearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glContext.clearDepth(1.0f);
        glContext.enable(WebGLRenderingContext.DEPTH_TEST);
        glContext.depthFunc(WebGLRenderingContext.LEQUAL);
        initBuffers();

        drawScene();
    }

    public void initShaders() {
        WebGLShader fragmentShader = getShader(
            WebGLRenderingContext.FRAGMENT_SHADER, Shaders.INSTANCE.fragmentShader().getText());
        WebGLShader vertexShader = getShader(
            WebGLRenderingContext.VERTEX_SHADER, Shaders.INSTANCE.vertexShader().getText());

        shaderProgram = glContext.createProgram();
        glContext.attachShader(shaderProgram, vertexShader);
        glContext.attachShader(shaderProgram, fragmentShader);
        glContext.linkProgram(shaderProgram);

        if (!glContext.getProgramParameterb(shaderProgram, WebGLRenderingContext.LINK_STATUS)) {
            throw new RuntimeException("Could not initialise shaders");
        }

        glContext.useProgram(shaderProgram);

        vertexPositionAttribute = glContext.getAttribLocation(shaderProgram, "vertexPosition");
        glContext.enableVertexAttribArray(vertexPositionAttribute);
    }

    private WebGLShader getShader(int type, String source) {
        WebGLShader shader = glContext.createShader(type);
        glContext.shaderSource(shader, source);
        glContext.compileShader(shader);
        if (!glContext.getShaderParameterb(shader, WebGLRenderingContext.COMPILE_STATUS)) {
            throw new RuntimeException(glContext.getShaderInfoLog(shader));
        }
        return shader;
    }

    private void initBuffers() {
        vertexBuffer = glContext.createBuffer();
        glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
        float[] vertices = new float[]{
            0.0f,  1.0f,  -5.0f, // first vertex
            -1.0f, -1.0f,  -5.0f, // second vertex
            1.0f, -1.0f,  -5.0f  // third vertex
        };
        glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER, Float32Array.create(vertices),
                             WebGLRenderingContext.STATIC_DRAW);
    }

    private void drawScene() {
        glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT | WebGLRenderingContext.DEPTH_BUFFER_BIT);
        float[] perspectiveMatrix = createPerspectiveMatrix(45, 1, 0.1f, 1000);
        WebGLUniformLocation uniformLocation = glContext.getUniformLocation(shaderProgram, "perspectiveMatrix");
        glContext.uniformMatrix4fv(uniformLocation, false, perspectiveMatrix);
        glContext.vertexAttribPointer(vertexPositionAttribute, 3, WebGLRenderingContext.FLOAT, false, 0, 0);
        glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, 3);
    }

    private float[] createPerspectiveMatrix(int fieldOfViewVertical, float aspectRatio,
                                            float minimumClearance, float maximumClearance) {
        float top    = minimumClearance * (float)Math.tan(fieldOfViewVertical * Math.PI / 360.0);
        float bottom = -top;
        float left   = bottom * aspectRatio;
        float right  = top * aspectRatio;

        float X = 2*minimumClearance/(right-left);
        float Y = 2*minimumClearance/(top-bottom);
        float A = (right+left)/(right-left);
        float B = (top+bottom)/(top-bottom);
        float C = -(maximumClearance+minimumClearance)/(maximumClearance-minimumClearance);
        float D = -2*maximumClearance*minimumClearance/(maximumClearance-minimumClearance);

        return new float[]{     X, 0.0f, A, 0.0f,
                                0.0f, Y, B, 0.0f,
                                0.0f, 0.0f, C, -1.0f,
                                0.0f, 0.0f, D, 0.0f};
    };

    public interface Shaders extends ClientBundle {
        public static Shaders INSTANCE=GWT.create(Shaders.class);
        
        @Source(value={"fragment-shader.txt"})
        TextResource fragmentShader();
        
        @Source(value={"vertex-shader.txt"})
        TextResource vertexShader();
    }
}
