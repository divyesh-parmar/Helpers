package com.example.tourch.Helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourch.R;

/**
 * Created by jignesh(jc) on 12/12/2017.
 */

public class UtilMini {

	Context mContext;

	public UtilMini(Context mContext) {
		this.mContext = mContext;
	}

	public void TOAST(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}

	public void TOAST(Float msg) {
		Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
	}

	public void TOAST(boolean msg) {
		Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
	}

	public void TOAST(int msg) {
		Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
	}

	public void TOAST(Bitmap msg) {
		Toast t = new Toast(mContext);
		ImageView iv = new ImageView(mContext);
		iv.setImageBitmap(msg);
		t.setView(iv);
		t.show();
	}

	public void TOAST(Uri msg) {
		Toast t = new Toast(mContext);
		ImageView iv = new ImageView(mContext);
		iv.setImageURI(msg);
		t.setView(iv);
		t.show();
	}

	public void TOAST(Drawable msg) {
		Toast t = new Toast(mContext);
		ImageView iv = new ImageView(mContext);
		iv.setImageDrawable(msg);
		t.setView(iv);
		t.show();
	}

	public void LOG(String TAG, String MESSAGE) {
		System.out.println(TAG + " " + MESSAGE);
	}

	public void LOG(String TAG, int MESSAGE) {
		System.out.println(TAG + " " + MESSAGE);
	}

	public void LOG(String TAG, boolean MESSAGE) {
		System.out.println(TAG + " " + MESSAGE);
	}

	public void LOG(String TAG, float MESSAGE) {
		System.out.println(TAG + " " + MESSAGE);
	}

	public String getApplicationName() {
		PackageManager packageManager = mContext.getPackageManager();
		ApplicationInfo applicationInfo = null;
		try {
			applicationInfo = packageManager.getApplicationInfo(
					mContext.getApplicationInfo().packageName, 0);
		} catch (final PackageManager.NameNotFoundException e) {
		}
		return (String) (applicationInfo != null ? packageManager
				.getApplicationLabel(applicationInfo) : "Unknown");
	}

	public Bitmap getBitmapFromDrawable(int RESOURC) {
		return BitmapFactory.decodeResource(mContext.getResources(), RESOURC);
	}

	public Bitmap getBitmapFromDrawable(Drawable d) {
		return ((BitmapDrawable) d).getBitmap();
	}

	public Drawable getDrawableFromBitmap(Bitmap bitmap) {
		return new BitmapDrawable(mContext.getResources(), bitmap);
	}

	public Bitmap getDrawingCache(View v) {
		v.setDrawingCacheEnabled(true);
		v.buildDrawingCache(true);
		Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
		v.setDrawingCacheEnabled(false);
		return b;
	}

	public ArrayList<String> getAssetFolderFiles(String path) {
		String[] list;
		ArrayList<String> files = new ArrayList<String>();
		try {
			list = mContext.getAssets().list(path);
			if (list.length > 0) {
				for (String file : list) {
					files.add(file);
				}
			}
		} catch (Exception e) {
		}
		return files;
	}

	public ArrayList<Bitmap> getAssetFolderImage(Context con, String folderName) {
		ArrayList<Bitmap> LIST = new ArrayList<Bitmap>();
		try {
			String[] images = con.getAssets().list(folderName);
			ArrayList<String> listImages = new ArrayList<String>(
					Arrays.asList(images));
			for (int i = 0; i < listImages.size(); i++) {
				InputStream inputstream = con.getAssets().open(
						folderName + "/" + listImages.get(i));

				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inDensity = 100;

				Drawable drawable = Drawable.createFromResourceStream(null,
						null, inputstream, null, opts);

				Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

				LIST.add(bitmap);
			}
		} catch (Exception e) {
		}
		return LIST;

	}

	public int getDisplayHieght() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.heightPixels;
	}

	public int getDisplayWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}

	public void saveBitmap(Bitmap bitmap) {

		File myDir = new File(Environment.getExternalStorageDirectory()
				.toString()
				+ "/"
				+ mContext.getResources().getString(R.string.app_name));
		myDir.mkdirs();
		File file = new File(myDir, mContext.getResources().getString(
				R.string.app_name)
				+ System.currentTimeMillis() + ".jpg");
		String filepath = file.getPath();
		if (file.exists()) {
			file.delete();
		}
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream ostream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);

			ostream.close();

		} catch (Exception e) {

		}
		Intent mediaScanIntent = new Intent(
				"android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		mediaScanIntent.setData(Uri.fromFile(new File(filepath)));
		mContext.sendBroadcast(mediaScanIntent);
		TOAST("Image succesfully save....");
	}

	public Bitmap setAlphaToBitmap(Bitmap src, int value) {
		int width = src.getWidth();
		int height = src.getHeight();
		Bitmap transBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(transBitmap);
		canvas.drawARGB(0, 0, 0, 0);
		// config paint
		final Paint paint = new Paint();
		paint.setAlpha(value);
		canvas.drawBitmap(src, 0, 0, paint);
		return transBitmap;
	}

	public void setTYPEFACE(String name, TextView tv) {
		Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/"
				+ name);
		tv.setTypeface(tf);
	}

	public void shareImage(Uri u, PACKAGE pck) {
		try {
			if (pck.equals(PACKAGE.WHATSAAP)) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtra(Intent.EXTRA_STREAM, u);
				i.setPackage("com.whatsapp");
				i.setType("image/*");
				mContext.startActivity(i);
			} else if (pck.equals(PACKAGE.FACEBOOK)) {

			} else if (pck.equals(PACKAGE.INSTAGRAM)) {

			} else {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtra(Intent.EXTRA_STREAM, u);
				i.setType("image/*");
			}
		} catch (Exception e) {
			TOAST(e.toString());
		}

	}

	public void shareImage(Bitmap bitmap, PACKAGE pck) {
		try {
			String bitmapPath = MediaStore.Images.Media.insertImage(
					mContext.getContentResolver(), bitmap, "title", null);
			Uri bitmapUri = Uri.parse(bitmapPath);
			if (pck.equals(PACKAGE.WHATSAAP)) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtra(Intent.EXTRA_STREAM, bitmapUri);
				i.setPackage("com.whatsapp");
				i.setType("image/*");
				mContext.startActivity(i);
			} else if (pck.equals(PACKAGE.FACEBOOK)) {

			} else if (pck.equals(PACKAGE.INSTAGRAM)) {

			} else {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtra(Intent.EXTRA_STREAM, bitmapUri);
				i.setType("image/*");
			}
		} catch (Exception e) {
			TOAST(e.toString());
		}

	}

	public void startCamera(int Request_Code) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		((Activity) mContext).startActivityForResult(intent, Request_Code);
	}

	public void startGallary(int Request_Code) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		((Activity) mContext).startActivityForResult(intent, Request_Code);
	}

	public Bitmap getBitmapFromUri(Uri u) {
		Bitmap myBitmap = null;
		try {
			myBitmap = MediaStore.Images.Media.getBitmap(
					mContext.getContentResolver(), u);
		} catch (Exception e) {
		}
		return myBitmap;
	}

	public Bitmap getShadow(Bitmap mainImage) {
		int width = mainImage.getWidth();
		int height = mainImage.getHeight();
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflectionImage = Bitmap.createBitmap(mainImage, 0, 0, width,
				height, matrix, false);

		Bitmap reflectedBitmap = Bitmap.createBitmap(width, (height + height),
				Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(reflectedBitmap);
		canvas.drawBitmap(mainImage, 0, 0, null);
		Paint defaultPaint = new Paint();
		canvas.drawRect(0, height, width, height, defaultPaint);
		canvas.drawBitmap(reflectionImage, 0, height - 6, null);
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, mainImage.getHeight(), 0,
				reflectedBitmap.getHeight(), 0x70ffffff, 0x00ffffff,
				Shader.TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		canvas.drawRect(0, height, width, reflectedBitmap.getHeight(), paint);
		return reflectedBitmap;
	}

	public boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public Bitmap rotateBitmap(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
				source.getHeight(), matrix, true);
	}

	public Bitmap flipBitmapHorizontally(Bitmap bInput) {
		Matrix matrix = new Matrix();
		matrix.preScale(-1.0f, 1.0f);
		return Bitmap.createBitmap(bInput, 0, 0, bInput.getWidth(),
				bInput.getHeight(), matrix, true);
	}

	public Bitmap flipBitmapVertically(Bitmap bInput) {
		Matrix matrix = new Matrix();
		matrix.preScale(1.0f, -1.0f);
		return Bitmap.createBitmap(bInput, 0, 0, bInput.getWidth(),
				bInput.getHeight(), matrix, true);
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public void shareTextIntent(String shareBody) {
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				"Subject Here");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		mContext.startActivity(Intent.createChooser(sharingIntent, "Share Via"));
	}

	public void copyStringInClipBoard(String text) {
		ClipboardManager clipboard = (ClipboardManager) mContext
				.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText("Link", text);
		clipboard.setPrimaryClip(clip);
		TOAST("Text copied in clipboard..");

	}

	public void openLinkInBrowser(String url) {
		Intent openHashURLinBrowser = new Intent(Intent.ACTION_VIEW);
		openHashURLinBrowser.setData(Uri.parse(url));
		mContext.startActivity(openHashURLinBrowser);
	}

	public enum PACKAGE {
		WHATSAAP, FACEBOOK, INSTAGRAM;
	}

	public void VibrateOnLight() {
		// TOAST("inside vibrate");
		Vibrator v = (Vibrator) mContext
				.getSystemService(Context.VIBRATOR_SERVICE);
		// Vibrate for 500 milliseconds
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			v.vibrate(VibrationEffect.createOneShot(1000,
					VibrationEffect.DEFAULT_AMPLITUDE));
		} else {
			// deprecated in API 26
			v.vibrate(1000);
		}
	}

	public boolean setBluetooth(boolean enable) {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		boolean isEnabled = bluetoothAdapter.isEnabled();
		if (enable && !isEnabled) {
			return bluetoothAdapter.enable();
		} else if (!enable && isEnabled) {
			return bluetoothAdapter.disable();
		}
		// No need to change bluetooth state
		return true;
	}

	public void openDnD() {
		if (Build.VERSION.SDK_INT > 23) {
			NotificationManager mNotificationManager = (NotificationManager) mContext
					.getSystemService(Context.NOTIFICATION_SERVICE);

			if (!mNotificationManager.isNotificationPolicyAccessGranted()) {
				Intent intent = new Intent(
						android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
				mContext.startActivity(intent);
			}
		}
	}

	public void onoffBlutooth() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();

		if (mBluetoothAdapter.isEnabled()) {
			mBluetoothAdapter.disable();
		} else {
			mBluetoothAdapter.enable();
		}
	}

	public boolean checkBluttoth() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();

		if (mBluetoothAdapter.isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	public void onoffWifi() {
		WifiManager wifiManager = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
		if (checkWifi())
			wifiManager.setWifiEnabled(false);
		else
			wifiManager.setWifiEnabled(true);
	}

	public boolean checkWifi() {
		WifiManager wifiManager = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.isWifiEnabled();
	}

	public void onoffMobileData() {
		try {
			TelephonyManager telephonyService = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);

			Method setMobileDataEnabledMethod = telephonyService.getClass()
					.getDeclaredMethod("setDataEnabled", boolean.class);

			if (null != setMobileDataEnabledMethod) {
				if (getMobileDataState())
					setMobileDataEnabledMethod.invoke(telephonyService, false);
				else
					setMobileDataEnabledMethod.invoke(telephonyService, true);
			}
		} catch (Exception ex) {
			TOAST("Sim detection failed");
		}
	}

	public boolean getMobileDataState() {
		try {
			TelephonyManager telephonyService = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);

			Method getMobileDataEnabledMethod = telephonyService.getClass()
					.getDeclaredMethod("getDataEnabled");

			if (null != getMobileDataEnabledMethod) {
				boolean mobileDataEnabled = (Boolean) getMobileDataEnabledMethod
						.invoke(telephonyService);

				return mobileDataEnabled;
			}
		} catch (Exception ex) {

		}

		return false;
	}

	public boolean isRotationEnabled() {

		int result = 0;
		try {
			result = Settings.System.getInt(mContext.getContentResolver(),
					Settings.System.ACCELEROMETER_ROTATION);
		} catch (Settings.SettingNotFoundException e) {
			e.printStackTrace();
		}

		return result == 1;
	}

	public void onoffScreenOrrientation() {
		try {
			if (Settings.System.getInt(mContext.getContentResolver(),
					Settings.System.ACCELEROMETER_ROTATION) == 1) {
				Display defaultDisplay = ((WindowManager) mContext
						.getSystemService(Context.WINDOW_SERVICE))
						.getDefaultDisplay();
				Settings.System.putInt(mContext.getContentResolver(),
						Settings.System.USER_ROTATION,
						defaultDisplay.getRotation());
				Settings.System.putInt(mContext.getContentResolver(),
						Settings.System.ACCELEROMETER_ROTATION, 0);
			} else {
				Settings.System.putInt(mContext.getContentResolver(),
						Settings.System.ACCELEROMETER_ROTATION, 1);
			}

			if (isRotationEnabled())
				Settings.System.putInt(mContext.getContentResolver(),
						Settings.System.ACCELEROMETER_ROTATION, 1);
			else
				Settings.System.putInt(mContext.getContentResolver(),
						Settings.System.ACCELEROMETER_ROTATION, 0);

		} catch (Settings.SettingNotFoundException e) {
			e.printStackTrace();
		}
	}

}
