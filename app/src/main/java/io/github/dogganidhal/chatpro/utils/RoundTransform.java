package io.github.dogganidhal.chatpro.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

public class RoundTransform implements Transformation {

  private float roundedCorner;

  public RoundTransform(float roundedCorner) {
    this.roundedCorner = roundedCorner;
  }

  @Override
  public Bitmap transform(Bitmap source) {

    Bitmap imageRounded = Bitmap.createBitmap(source.getWidth(), source.getHeight(), source.getConfig());
    Canvas canvas = new Canvas(imageRounded);
    Paint mpaint = new Paint();
    mpaint.setAntiAlias(true);
    mpaint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    canvas.drawRoundRect((new RectF(0, 0, source.getWidth(), source.getHeight())), this.roundedCorner, this.roundedCorner, mpaint);
    source.recycle();

    return imageRounded;
  }

  @Override
  public String key() {
    return "circle";
  }

}
