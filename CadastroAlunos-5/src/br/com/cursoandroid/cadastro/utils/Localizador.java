package br.com.cursoandroid.cadastro.utils;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

public class Localizador {
	
	private Geocoder geocoder;
	
	public Localizador (Context ctx){
		geocoder = new Geocoder(ctx);
	}
	
	public LatLng getCoordenadas(String endereco){
		try {
			List<Address> listaDeEnderecos = geocoder.getFromLocationName(endereco, 1);
			if (!listaDeEnderecos.isEmpty()) {
				Address address = listaDeEnderecos.get(0);
				return new LatLng(address.getLatitude(), address.getLongitude());
			}
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}				
	}
}
