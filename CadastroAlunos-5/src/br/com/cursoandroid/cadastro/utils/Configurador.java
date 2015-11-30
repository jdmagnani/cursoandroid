package br.com.cursoandroid.cadastro.utils;

import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationRequest;

public class Configurador implements ConnectionCallbacks {

	
	private AtualizaLocalizacao atualizadorDeLocalizacao;

	public Configurador(AtualizaLocalizacao atualizadorDeLocalizacao) {
	
		this.atualizadorDeLocalizacao = atualizadorDeLocalizacao;
	}
	
	@Override
	public void onConnected(Bundle connectionHint) {
		LocationRequest configs = LocationRequest.create();
		configs.setInterval(2000);
		configs.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		configs.setSmallestDisplacement(50);
		
		atualizadorDeLocalizacao.inicializa(configs);
		
	}

	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub
		
	}

		
	
}
