package br.com.cadastro;

import br.com.cadastro.dao.AlunoDAO;
import br.com.cadastro.modelo.Aluno;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Object[] mensagens = (Object[]) intent.getExtras().get("pdus"); // protocolo de mensagems que o Android usa
		byte[] mensagem = (byte[]) mensagens[0]; // Sempre ultima msg
		
		SmsMessage sms = SmsMessage.createFromPdu(mensagem);
		String telefone = sms.getOriginatingAddress(); 
		Toast.makeText(context, "SMS do aluno : " + telefone, Toast.LENGTH_SHORT).show();
		 
		boolean smsEhDeAluno = new AlunoDAO(context).ehAluno(telefone);
		
		if (smsEhDeAluno) {
			MediaPlayer musica =  MediaPlayer.create(context, R.raw.flaxenhair);
			musica.start();
		}						
	}
}
