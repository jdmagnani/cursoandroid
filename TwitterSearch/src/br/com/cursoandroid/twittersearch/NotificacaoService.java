package br.com.cursoandroid.twittersearch;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.cursoandroid.twittersearch.R;

import com.github.kevinsawicki.http.HttpRequest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;


/**
 * 
 * @author cursoandroid
 * 
 * O SERVICE é um componente da plataforma Android para executar tarefas
 * de longa duração em plano de fundo e que não possui interface gráfica. 
 * 
 * Ele pode ser iniciado por qualquer outro tipo de componente (uma Activity ou outro
 * Service, por exemplo) e pode continuar em execução mesmo que o componente
 * que o iniciou seja destruído. Alguns exemplos de uso de Service incluem fazer
 * downloads, tocar uma música e acessar um serviço remoto.
 * 
 * ATENÇÃO: o Service é executado no mesmo processo e na thread principipal
 * da aplicação. Portanto, se ele realiza operações bloqueantes, o desempenho da
 * aplicação pode ficar comprometido, sendo necessário criar no Service uma nova
 * thread para executar essas operações.
 * 
 * Um Service pode assumir duas formas, STARTED e BOUND
 * 
 * 		- STARTED: quando foi iniciado explicitamente por algum outro
 * 				   componente através do método Context.startService. Neste caso, o 
 * 				   Service é executado em plano de fundo indefinidamente mesmo que o componente
 * 				   que o iniciou seja destruído.
 * 
 * 		- BOUND: quando foi iniciado por um componente através do método
 * 		  		 Context.bindService. Esta forma de Service permite a interação entre componentes,
 *				 como o envio de requisições e obtenção de respostas, numa espécie de
 *				 cliente-servidor.
 * 
 * 
 */


public class NotificacaoService extends Service {
	
	private String accessToken = "3727782196-OhXIVO9Ap0SgvgcVRLKEdoUlqorSUna7AgMUzLO";
	
	private class AutenticacaoTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Map<String, String> data = new HashMap<String, String>();
				data.put("grant_type", "client_credentials");
				String json = HttpRequest
						.post("https://api.twitter.com/oauth2/token")
						.authorization("Basic "+ gerarChave())
						.form(data)
						.body();

				JSONObject token = new JSONObject(json);
				accessToken = token.getString("access_token");
			} catch (Exception e) {
				return null;
			}
			return null;
		}
		
		private String gerarChave() throws UnsupportedEncodingException{
			String key = " ePM56fJF2Z6zMWxcCoJLhj7TJ";
			String secret = " GP6rO4DzEGtdIkipGbnYW3ZlkNUcPSKlpCEtZjgM9vImOFtL11";
			String token = key + ":" + secret;
			String base64 = Base64.encodeToString(token.getBytes(), Base64.NO_WRAP);
			return base64;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
			long delayInicial = 0;
			long periodo = 2;
			TimeUnit unit = TimeUnit.MINUTES;
			pool.scheduleAtFixedRate(new NotificacaoTask(), delayInicial, periodo, unit);
		}
	}

	/**
	 * Retorna um int que indica como o serviço deve ser reiniciado pelo 
	 * Android quando, por algummotivo, ele for encerrado. OAndroid 
	 * pode encerrar (kill) um Service, caso o sistema esteja com falta de recursos, e 
	 * recriá-lo posteriormente. A flag START_STICKY indica que o Service deve ser 
	 * reiniciado e o método onStartCommand deve ser invocado novamente, 
	 * mesmo que não haja nenhuma Intent pendente de processamento.
	 * 
	 * Existem outras flags como a START_NOT_STICKY, que indica que 
	 * o Service só deve ser reiniciado se houver Intents pendentes, e a 
	 * START_REDELIVER_INTENT para que o Service seja reiniciado com a última 
	 * Intent enviada.
	 */
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new AutenticacaoTask().execute();
		ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
		long delayInicial = 0;
		long periodo = 2;
		TimeUnit unit = TimeUnit.MINUTES;
		pool.scheduleAtFixedRate(new NotificacaoTask(), delayInicial, periodo, unit);
		return START_STICKY;
	}

	
	/**
	 * O método onBind deve ser implementado. Esse método é invocado quando
	 * algum componente deseja realizar o bind com este Service e deve retornar uma
	 * implementação de IBinder que será a interface utilizada para a comunicação entre
	 * os componentes. Como no nosso caso não permitiremos o bind, pois nosso
	 * Service é started, o método onBind retorna null.
	 */
		
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	private boolean estaConectado() {
		ConnectivityManager manager = 
			(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		Log.i(getPackageName(), "conectado " + info.isConnected());
		return info.isConnected();
	}

	private class NotificacaoTask implements Runnable {
		
		
		
		private String baseUrl = "https://api.twitter.com/1.1/search/tweets.json";							     
		private String refreshUrl = "?q=@jdmagnani1";

		@Override
		public void run() {
			
			Log.i("NotificacaoTask","Nova Notificação!");
			
			
			if (!estaConectado()) {
				return;
			}
			try {
				String conteudo = HttpRequest.get(baseUrl + refreshUrl)
						.authorization("Bearer " + accessToken)
						.body();

				JSONObject jsonObject = new JSONObject(conteudo);
				refreshUrl = jsonObject.getJSONObject("search_metadata")
									   .getString("refresh_url");

				JSONArray resultados = jsonObject.getJSONArray("statuses");
				
				Log.i("Status",resultados.toString());

				for (int i = 0; i < resultados.length(); i++) {
					JSONObject tweet = resultados.getJSONObject(i);
					String texto = tweet.getString("text");
					String usuario = tweet.getJSONObject("user").getString("screen_name");
					criarNotificacao(usuario, texto, i);
				}
			} catch (Exception e) {
				Log.e(getPackageName(), e.getMessage(), e);
			}
		}

		@SuppressWarnings("deprecation")
		private void criarNotificacao(String usuario, String texto, int id) {
			
			Log.i("criarNotificacao",usuario);
			
			int icone = R.drawable.ic_launcher;
			String aviso = getString(R.string.aviso);
			long data = System.currentTimeMillis();
			String titulo = usuario + " " + getString(R.string.titulo);
			
			Context context = getApplicationContext();
			Intent intent = new Intent(context, TweetActivity.class);
			intent.putExtra(TweetActivity.USUARIO, usuario.toString());
			intent.putExtra(TweetActivity.TEXTO, texto.toString());
			
			PendingIntent pendingIntent = 
					PendingIntent.getActivity(context, id, intent, 
									Intent.FLAG_ACTIVITY_NEW_TASK);

			Notification notification = new Notification(icone, aviso, data);
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			notification.defaults |= Notification.DEFAULT_VIBRATE; 
			notification.defaults |= Notification.DEFAULT_LIGHTS; 
			notification.defaults |= Notification.DEFAULT_SOUND;
						
			
			notification.setLatestEventInfo(context, titulo, 
											texto, pendingIntent);
			
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager notificationManager = 
					(NotificationManager) getSystemService(ns);
			notificationManager.notify(id, notification);
			
			Log.i("criarNotificacao",notification.toString());
		}
	}
}