package br.com.cadastro;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FormularioActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		Button botao = (Button) findViewById(R.id.botao);
		botao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Toast.makeText(FormularioActivity.this, "Você clicou no botão", Toast.LENGTH_LONG).show();

                    finish();
			}
		});

		
		
	}
}
