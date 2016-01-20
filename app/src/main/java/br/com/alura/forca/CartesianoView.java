package br.com.alura.forca;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class CartesianoView extends View {

    private int             menorLadoDisplay = 0;

    private int unidade                     = 0;
    private boolean desenhaPlanoCartesiano  = false;

    public CartesianoView(Context context) {
        super(context);
    }
    public CartesianoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected int toPixel(int vezes_u) {
        return this.unidade * vezes_u ;
    }
    protected void setUnidade(int u) {
        this.unidade = u;
    }
    public void drawPlanoCartesiano(Canvas canvas) {

        Path path = new Path();

        int max = toPixel(10);
        for (int n = 0; n <= 10; n++){
            path.moveTo(toPixel(n), 1);
            path.lineTo(toPixel(n), max);
            path.moveTo(1, toPixel(n));
            path.lineTo(max, toPixel(n));
        }

        Paint paint = new Paint();

        paint.setAntiAlias( true  );
        paint.setColor(Color.BLACK);
        paint.setStyle( Paint.Style.STROKE );
        paint.setStrokeWidth(1);

        canvas.drawPath( path, paint );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (getHeight() > getWidth())
            setmenorLadoDisplay(getWidth());
        else
            setmenorLadoDisplay(getHeight());
        setUnidade(getmenorLadoDisplay() / 10);

        if(isDesenhaPlanoCartesiano())
            drawPlanoCartesiano(canvas);
    }

    public boolean isDesenhaPlanoCartesiano() {
        return desenhaPlanoCartesiano;
    }
    public void setDesenhaPlanoCartesiano(boolean desenhaPlanoCartesiano) {
        this.desenhaPlanoCartesiano = desenhaPlanoCartesiano;
    }
    private int getmenorLadoDisplay() {return menorLadoDisplay;}
    private void setmenorLadoDisplay(int menorLado) { this.menorLadoDisplay = menorLado; }
}
