package br.com.alura.forca;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class Main extends Activity {

    private Button          btJogar;
    private Button          btPlay;
    private EditText        etLetra;
    private ForcaController forcaController;
    private ForcaView       forcaView;

    private final String[] palavras = new String[] {"alura", "caelum"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btJogar = (Button)findViewById( R.id.btJogar );
        btPlay  = (Button)findViewById( R.id.btPlay );
        etLetra = (EditText)findViewById( R.id.etLetra );
        forcaView = (ForcaView)findViewById( R.id.fvJogo );

        init();
    }

    private void init(){
        btJogar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etLetra.getText().toString().trim().length()==0) return;
                getForcaController().joga(
                            etLetra.getText().toString().trim().charAt(0));
                forcaView.invalidate();
                etLetra.getText().clear();
                if (getForcaController().isTerminou()) {
                    btJogar.setEnabled(false);
                    btPlay.setEnabled(true);
                    if (getForcaController().isMorreu())
                        Toast.makeText(getApplicationContext(), "Ops! Você perdeu.", Toast.LENGTH_LONG).show();
                    else if (getForcaController().isGanhou())
                        Toast.makeText(getApplicationContext(), "Parabéns, você ganhou!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btPlay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setForcaController(new ForcaController( palavras[new Random().nextInt(palavras.length)] ));

                forcaView.setForcaController( getForcaController() );
                forcaView.invalidate();

                etLetra.getText().clear();
                etLetra.setEnabled(true);
                btPlay.setEnabled( false );
                btJogar.setEnabled( true );

            }
        });
    }

    public ForcaController getForcaController() {
        return forcaController;
    }
    public void setForcaController(ForcaController forcaController) {
        this.forcaController = forcaController;
    }
}
