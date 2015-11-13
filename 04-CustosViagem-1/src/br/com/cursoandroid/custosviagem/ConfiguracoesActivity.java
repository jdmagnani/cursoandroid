package br.com.cursoandroid.custosviagem;

import br.com.cursoandroid.custosviagem.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ConfiguracoesActivity extends PreferenceActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencias);
	}
}