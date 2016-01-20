package br.com.alura.forca;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;

public class ForcaView extends CartesianoView {

    private enum Membro{braco, perna}
    private enum Lado{direito, esquerdo}

    private int             menorLado       = 0;

    private ForcaController forcaController = null;
    private Path            pathBoneco      = null;

    public ForcaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPathBoneco(new Path());
        getPathBoneco().rewind();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setDesenhaPlanoCartesiano(true);

        plotaArmacaoDaForca();

        if(getForcaController()!=null) {
            switch ( getForcaController().getQntErros() ) {
                case 0:
                    plotaCabeca();
                    break;
                case 1:
                    plotaCorpo();
                    break;
                case 2:
                    plotaMembro(Membro.braco, Lado.direito);
                    break;
                case 3:
                    plotaMembro(Membro.braco, Lado.esquerdo);
                    break;
                case 4:
                    plotaMembro(Membro.perna, Lado.esquerdo);
                    break;
                case 5:
                    plotaMembro(Membro.perna, Lado.direito);
                    break;
            }
        }

        plotaTracos();
        drawLetrasCorretas(canvas);
        canvas.drawPath(getPathBoneco(), getPaintForca());
    }
    private Paint getPaintForca(){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);

        return paint;
    }
    private Paint getPaintTraco(){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        paint.setTextSize(25);

        return  paint;
    }
    private void plotaArmacaoDaForca() {
        getPathBoneco().moveTo( toPixel(1), toPixel(10) );
        getPathBoneco().lineTo( toPixel(3), toPixel(10) );

        getPathBoneco().moveTo( toPixel(2), toPixel(10) );
        getPathBoneco().lineTo( toPixel(2), toPixel(1) );

        getPathBoneco().rLineTo( toPixel(5), 0 );
        getPathBoneco().rLineTo( 0, toPixel(1) );
    }
    private void plotaCabeca() {
        getPathBoneco().addCircle( toPixel(7), toPixel(3), toPixel(1), Path.Direction.CW );
    }
    private void plotaCorpo() {
        getPathBoneco().moveTo(toPixel(7), toPixel(4));
        getPathBoneco().lineTo(toPixel(7), toPixel(7));
    }
    private void plotaMembro(Membro membro, Lado lado) {
        int alturaDoBraco   = 5;//define o y do local onde será desenha o braço
        int alturaDaPerna   = 7;//define o y do local onde será desenhado a perna
        int posDoCorpo      = 7;
        int alturaFinal;

        if(membro==Membro.braco) {
            getPathBoneco().moveTo(toPixel(posDoCorpo), toPixel(alturaDoBraco) );
            alturaFinal = alturaDoBraco + 1;
        }else{
            getPathBoneco().moveTo( toPixel(posDoCorpo), toPixel(alturaDaPerna) );
            alturaFinal = alturaDaPerna + 1;
        }

        if(lado==Lado.direito)
            getPathBoneco().lineTo( toPixel((posDoCorpo+1) ), toPixel( (alturaFinal) ) );
        else
            getPathBoneco().lineTo( toPixel((posDoCorpo-1) ), toPixel( (alturaFinal) ) );
    }

    private void plotaTracos() {
        int eixoX = toPixel(3) ;
        getPathBoneco().moveTo( eixoX+10, toPixel(10) );
        if (getForcaController() != null){
            for (int i = 0; i <= getForcaController().getPalavraAteAgora().length()-1; i++) {
                getPathBoneco().rMoveTo(10, 0);
                getPathBoneco().rLineTo(toPixel(1), 0);
            }
        }
        getPaintTraco().setStyle(Paint.Style.STROKE);
    }
    private void drawLetrasCorretas(Canvas canvas) {
        int eixoX = ( toPixel(3) );
        getPathBoneco().moveTo( eixoX + 10, toPixel(10) );
        eixoX+=35;

        if (getForcaController() != null){
            for (int i = 0; i <= getForcaController().getPalavraAteAgora().length()-1; i++) {
                char c = getForcaController().getPalavraAteAgora().charAt(i);
                canvas.drawText(c + "", eixoX + ((toPixel(1) + 10) * i),(toPixel(10)) - 15,
                                    getPaintTraco());
            }
        }
    }
    public void setForcaController(ForcaController forcaController){
        getPathBoneco().rewind();
        this.forcaController = forcaController;
    }

    public Path getPathBoneco() {
        return pathBoneco;
    }
    private void setPathBoneco(Path pathBoneco) {
        this.pathBoneco = pathBoneco;
    }
    private ForcaController getForcaController() {return forcaController;}
    private int getMenorLado() {return menorLado;}
    private void setMenorLado(int menorLado) { this.menorLado = menorLado; }
}