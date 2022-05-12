package com.example.photocalling;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class FirstAct2 extends Activity {

	ListView list;
	public static ArrayList<File> photofiles = new ArrayList<>();
	public static int live;
	Bitmap thumb;
	public static ArrayList<Bitmap> nawab = new ArrayList<>();
	ProgressDialog pDialog;
	public static File pFile;
	MediaMetadataRetriever metaRetriever;
	public static ArrayList<String> totaltimes = new ArrayList<>();
	MediaPlayer player;
	static int l = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acti_first2);

		list = (ListView) findViewById(R.id.listView1);
		ImageButton backy = (ImageButton) findViewById(R.id.back);
		player = new MediaPlayer();
//		pd = new ProgressDialog(FirstAct.this);

		backy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});


	}

	private void findallvideo() {

		try {
			photofiles.clear();
			ArrayList<File> alphotofiiles = getallphoto(Environment.getExternalStorageDirectory(), 1);
			int size = alphotofiiles.size();
			if (size > 0) {
				ArrayList<String> alfilesstring = new ArrayList<>();
				for (int i = 0; i < alphotofiiles.size(); i++) {
					alfilesstring.add(alphotofiiles.get(i).getPath());
				}

			} else {

				Toast.makeText(FirstAct.this, "audio is not found", Toast.LENGTH_SHORT).show();
			}
		}catch (Exception e){

		}

	}

	class loadvideos extends AsyncTask<String, String, Boolean> {

		 protected void onPreExecute() {
	            super.onPreExecute();
	           pDialog = new ProgressDialog(FirstAct.this);
	           pDialog.setMessage("Please wait...");
	           pDialog.setIndeterminate(false);
	           pDialog.setCancelable(false);
	           pDialog.show();
	        }
		
		 @Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			NOTEPAD_GalleryAdapter adapter = new NOTEPAD_GalleryAdapter(FirstAct.this, photofiles, totaltimes, new Playinterface() {
				@Override
				public void audiosong(int position, int x) {
					Utilsa.CURRENT_p = position;

					if (x == -1) {
						if (player != null) {
							player.pause();

						}
						if(player.isPlaying()){
							if (player != null) {
								player.pause();

							}
						}

						l = 0;

						return;
					}

					stopPlaying();
					try {
						player = new MediaPlayer();
						player = MediaPlayer.create(FirstAct.this, Uri.parse(photofiles.get(position).getPath()));

						player.start();
//							player.prepare();
//							player.start();


					} catch (final Exception e) {
						e.printStackTrace();
					}

					l = 1;
				}
			});
    		list.setAdapter(adapter);
			 
		}
		
		@Override
		protected Boolean doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			findallvideo();
			
//			nawab.clear();

			for (int j = 0; j < photofiles.size(); j++) {

				File faltu = photofiles.get(j);
				String dattusatelite = faltu.getPath();

				String dura=getduration(photofiles.get(j).getPath());

				totaltimes.add(dura);

			}
			
			return null;
		}
		
		 protected void onPostExecute(Boolean paramBoolean) {
	            if (pDialog.isShowing()) {
	               pDialog.dismiss();
	            }
//	            grid_video.setAdapter(new AlbumAdapter(MyVideoActivity.this, MyVideoActivity.this.flLst));
	            NOTEPAD_GalleryAdapter adapter = new NOTEPAD_GalleryAdapter(FirstAct.this, photofiles,totaltimes, new Playinterface() {
					@Override
					public void audiosong(int position, int x) {
						Utilsa.CURRENT_p = position;

						if (x == -1) {
							if (player != null) {
								player.pause();

							}
							if(player.isPlaying()){
								if (player != null) {
									player.pause();

								}
							}

							l = 0;

							return;
						}

						stopPlaying();
						try {
							player = new MediaPlayer();
							player = MediaPlayer.create(FirstAct.this, Uri.parse(photofiles.get(position).getPath()));

							player.start();
//							player.prepare();
//							player.start();


						} catch (final Exception e) {
							e.printStackTrace();
						}

						l = 1;
					}
				});
	    		list.setAdapter(adapter);

	    		list.setOnItemClickListener(new OnItemClickListener() {

	    			@Override
	    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	    				// TODO Auto-generated method stub
	    				live = arg2;
	    				 pFile=photofiles.get(arg2);


	    			}
	    		});
	           
	        }

	}


	public String getduration(String path) {
		String duration;
		// load data file

		try {
			metaRetriever = new MediaMetadataRetriever();
			metaRetriever.setDataSource(path);
		} catch (RuntimeException ex) {
			// something went wrong with the file, ignore it and continue
		}

		// convert duration to minute:seconds
		duration =
				metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
//	        Log.v("time", duration);
		long dur = Long.parseLong(duration);
		String seconds = String.valueOf((dur % 60000) / 1000);

		Log.v("seconds", seconds);
		String minutes = String.valueOf(dur / 60000);
		if (seconds.length() == 1) {
			duration = "0" + minutes + ":0" + seconds;
//	            txtTime.setText("0" + minutes + ":0" + seconds);
		} else {
			duration = "0" + minutes + ":" + seconds;
//	            txtTime.setText("0" + minutes + ":" + seconds);
		}
		Log.v("minutes", minutes);
		// close object
		metaRetriever.release();
		return duration;
	}

	public ArrayList<File> getallphoto(File directery, int type) {
		File[] files = directery.listFiles();
		int i = 0;
		File[] fi;
		for (File inFile : files) {
			if (inFile.isDirectory()) {
				String andpath = inFile.getPath();
				int pos = andpath.indexOf("/Android");
				int pos1 = andpath.indexOf("/.");
				if (pos >= 0 || pos1 >= 0) {
					continue;
				}
				getallphoto(inFile, type);
			} else if (inFile.isFile() || inFile.canRead() || inFile.getTotalSpace() > 0) {

				switch (type) {
				case 0:
					if (inFile.getName().endsWith(".jpg") || inFile.getName().endsWith(".png")
							|| inFile.getName().endsWith(".jpeg")) {
						photofiles.add(inFile);
					}
					break;

				case 1:
					if (inFile.getName().endsWith(".mp3") || inFile.getName().endsWith(".m4a")
							|| inFile.getName().endsWith(".wav") || inFile.getName().endsWith(".acc")) {
						photofiles.add(inFile);
					}
					break;

				case 2:
					if (inFile.getName().endsWith(".mp4") || inFile.getName().endsWith(".mkv")
							|| inFile.getName().endsWith(".3gp")) {
						photofiles.add(inFile);
					}
					break;
				}
			}
		}
		return photofiles;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (player != null) {
			if (player.isPlaying()) {
				stopPlaying();
			}
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		new loadvideos().execute(new String[0]);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (pDialog!=null && pDialog.isShowing()){
			pDialog.dismiss();
		}

		if (player != null) {
			if (player.isPlaying()) {
				stopPlaying();
			}
		}
	}

	private void stopPlaying() {
		if (player != null) {
			player.stop();
			player.release();
			player = null;
		}

	}




}
