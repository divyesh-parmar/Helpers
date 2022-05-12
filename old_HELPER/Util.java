package My_Util;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pipcamera.Constant;
import com.example.pipcamera.R;

public class Util {
	/*
	 * --------------------------------------------------------------------------
	 * Method: This method print text and image in toast using uri and bitmap
	 * ------------------------------------------------------------
	 */
	public static void show_Text(Context context, String Message) {
		Toast.makeText(context, Message, Toast.LENGTH_LONG).show();
	}

	public static void show_Bitmap(Context context, Bitmap bitmap) {
		try
		{
		Toast t = new Toast(context);
		ImageView iv = new ImageView(context);
		iv.setImageBitmap(bitmap);
		t.setView(iv);
		t.show();
		}
		catch(Exception e)
		{
			show_Text(context, e.toString());
		}
	}

	public static void show_Uri(Context context, Uri uri) {
		Toast t = new Toast(context);
		ImageView iv = new ImageView(context);
		iv.setImageURI(uri);
		t.setView(iv);
		t.show();
	}
	public static void show_Drawable(Context context, Drawable d) {
		try
		{
		Toast t = new Toast(context);
		ImageView iv = new ImageView(context);
		iv.setImageDrawable(d);
		t.setView(iv);
		t.show();
		}
		catch(Exception e)
		{
			show_Text(context, e.toString());
		}
	}
	public static void Log(String msg)
	{
		System.out.println("BOOM "+msg );
	}

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method print text and image in toast using uri and bitmap
	 * ------------------------------------------------------------
	 */

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method start camera and gallery using intent
	 * ------------------------------------------------------------
	 */
	public static void start_Camera(Activity activity, int Request_Code) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		activity.startActivityForResult(intent, Request_Code);
	}

	public static void start_Gallary(Activity activity, int Request_Code) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		activity.startActivityForResult(intent, Request_Code);
	}

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method start camera and gallery using intent
	 * ------------------------------------------------------------
	 */

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method return our application name
	 * ------------------------------------------------------------
	 */
	public static String getAppName(Context context) {
		PackageManager packageManager = context.getPackageManager();
		ApplicationInfo applicationInfo = null;
		try {
			applicationInfo = packageManager.getApplicationInfo(
					context.getApplicationInfo().packageName, 0);
		} catch (final NameNotFoundException e) {
		}
		return (String) (applicationInfo != null ? packageManager
				.getApplicationLabel(applicationInfo) : "Unknown");
	}

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method return our application name
	 * ------------------------------------------------------------
	 */

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method return list of all folder from sd card
	 * ------------------------------------------------------------
	 */
	public static void get_folder_list(Context context) {
		File f = new File(Environment.getExternalStorageDirectory().toString());
		File[] files = f.listFiles();
		for (File inFile : files) {
			if (inFile.isDirectory()) {

			}
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method return list of all folder from sd card
	 * ------------------------------------------------------------
	 */

	/*
	 * --------------------------------------------------------------------------
	 * Method: This create file with app name and return this file
	 * ------------------------------------------------------------
	 */
	public static File create_appfolder(Context con) {
		File myDir = null;
		try {
			myDir = new File(Environment.getExternalStorageDirectory()
					.toString() + "/" + getAppName(con));
			if (!myDir.exists())
				myDir.mkdirs();
		} catch (Exception e) {
			show_Text(con, e.toString());
		}
		return myDir;
	}

	/*
	 * --------------------------------------------------------------------------
	 * Method: This create file with app name and return this file
	 * ------------------------------------------------------------
	 */

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method return bitmap which is converted from uri
	 * ------------------------------------------------------------
	 */
	public static Bitmap get_bitmap_from_uri(Context c, Uri u) {
		Bitmap myBitmap = null;
		try {
			myBitmap = MediaStore.Images.Media.getBitmap(
					c.getContentResolver(), u);
		} catch (Exception e) {
			show_Text(c, e.toString());
		}
		return myBitmap;

	}

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method return bitmap which is converted from uri
	 * ------------------------------------------------------------
	 */

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method sets alpha to bitmap
	 * ------------------------------------------------------------
	 */
	public static Bitmap set_alpha_to_bitmap(Bitmap src, int value) {
		int width = src.getWidth();
		int height = src.getHeight();
		Bitmap transBitmap = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(transBitmap);
		canvas.drawARGB(0, 0, 0, 0);
		// config paint
		final Paint paint = new Paint();
		paint.setAlpha(value);
		canvas.drawBitmap(src, 0, 0, paint);
		return transBitmap;
	}

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method sets alpha to bitmap
	 * ------------------------------------------------------------
	 */

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method saves bitmap in sd card
	 * ------------------------------------------------------------
	 */
	public static void save_bitmap(Context con, Bitmap bitmap) {

		File myDir = new File(Environment.getExternalStorageDirectory()
				.toString()
				+ "/"
				+ con.getResources().getString(R.string.app_name));
		myDir.mkdirs();
		File file = new File(myDir, con.getResources().getString(
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
			bitmap.compress(CompressFormat.JPEG, 100, ostream);
			// Editor.FILE_PATH=file.toString();

			ostream.close();

		} catch (Exception e) {

		}
		Constant.SAVE_URI=Uri.parse("file://"+filepath);
		//Util.show_Text(con, filepath);
		Intent mediaScanIntent = new Intent(
				"android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		mediaScanIntent.setData(Uri.fromFile(new File(filepath)));
		con.sendBroadcast(mediaScanIntent);
		show_Text(con, "image successfully saved....");
	}

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method saves bitmap in sdcard
	 * ------------------------------------------------------------
	 */

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method used to share image on social using uri
	 * ------------------------------------------------------------
	 */
	public static void share_image_social(Activity act, Uri u) {
		final Intent emailIntent1 = new Intent(
				android.content.Intent.ACTION_SEND);
		emailIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		emailIntent1.putExtra(Intent.EXTRA_STREAM, u);
		emailIntent1.setType("image/*");
		act.startActivity(emailIntent1);
	}

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method used to share image on social using uri
	 * ------------------------------------------------------------
	 */

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method used to delete file from sdcard
	 * ------------------------------------------------------------
	 */
	public static void delete_file(Activity act, String filename) {

		File fdelete = new File(filename);

		if (fdelete.exists()) {

			if (fdelete.delete()) {
				act.finish();
			} else {

			}
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method used to delete file from sdcard
	 * ------------------------------------------------------------
	 */

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method used to set wallpaper using uri
	 * ------------------------------------------------------------
	 */
	public static void set_wallpaper(Context con, Uri u) {
		WallpaperManager myWallpaperManager = WallpaperManager.getInstance(con
				.getApplicationContext());
		try {
			String file = "file://" + u.toString();
			Uri wallpaper = Uri.parse(file);
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(
					con.getContentResolver(), wallpaper);
			myWallpaperManager.setBitmap(bitmap);
			Util.show_Text(con, "wallpaper set....");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Util.show_Text(con, e.toString());
			e.printStackTrace();
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method used to set wallpaper using uri
	 * ------------------------------------------------------------
	 */

	/*
	 * --------------------------------------------------------------------------
	 * Method: This method used to get uri from name
	 * ------------------------------------------------------------
	 */
	public static Uri getUriFromName(String dName) {
		Uri u = Uri
				.parse("android.resource://com.doepiccoding.facedetection/drawable/"
						+ dName);
		return u;
	}
	/*
	 * --------------------------------------------------------------------------
	 * Method: This method used to get uri from name
	 * ------------------------------------------------------------
	 */
}