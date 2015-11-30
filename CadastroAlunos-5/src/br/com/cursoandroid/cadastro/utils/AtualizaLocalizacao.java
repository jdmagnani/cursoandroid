package br.com.cursoandroid.cadastro.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import br.com.cursoandroid.fragments.MapaFragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;



public class AtualizaLocalizacao implements LocationListener, ConnectionCallbacks, OnConnectionFailedListener {

	//private LocationClient client;
	private MapaFragment mapa;
	
	private GoogleApiClient mGoogleApiClient;
	
	
	public AtualizaLocalizacao(Context ctx, MapaFragment mapa) {
		this.mapa = mapa;
		
		Configurador configurador = new Configurador(null);
		//client = new LocationClient(ctx, configurador, null);
		
		mGoogleApiClient = new GoogleApiClient.Builder(ctx)
	     .addApi(LocationServices.API)
	     .addConnectionCallbacks(this)
	     .addOnConnectionFailedListener(this)
	     .build();
		
	}

	public void inicializa(LocationRequest configs) {
		
		//mGoogleApiClient.
		//client.requestLocationUpdates(configs,this);
		
	}

	@Override
	public void onLocationChanged(Location location) {
		double latitute = location.getAltitude();
		double longitude = location.getLongitude();
		
		LatLng novoLocal = new LatLng(latitute,longitude);
		
		mapa.centralizaNoLocal(novoLocal);
		
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub
		
	}

}

