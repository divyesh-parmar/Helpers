package com.example.tourch.Async;

import java.util.ArrayList;
import java.util.List;

import com.example.tourch.Adapter.InstallAdapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;

public class AsyncHelper extends AsyncTask<Void, Void, Void> {
	Context mContext;
	ArrayList<String> InstallAppName;
	ArrayList<String> PACKAGE;
	ArrayList<Integer> ICON;
	ArrayList<String> APPNAME;
	ListView listView;
	WebView webView;

	public AsyncHelper(Context mContext, ArrayList<String> PACKAGE,
			ArrayList<Integer> ICON, ArrayList<String> APPNAME,
			ListView listView, WebView webView) {
		this.mContext = mContext;
		this.APPNAME = APPNAME;
		this.PACKAGE = PACKAGE;
		this.ICON = ICON;
		this.APPNAME = APPNAME;
		this.listView = listView;
		this.webView = webView;
		this.webView.loadUrl("file:///android_asset/index.html");
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		webView.setVisibility(View.VISIBLE);

	}

	@Override
	protected Void doInBackground(Void... voids) {
		PackageManager pm = mContext.getPackageManager();
		List<ApplicationInfo> installedApps = pm.getInstalledApplications(0);

		for (ApplicationInfo aInfo : installedApps) {

			if ((aInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				// system application
			} else {
				// user application
				PACKAGE.add(aInfo.packageName);
				ICON.add(aInfo.icon);
				APPNAME.add(aInfo.loadLabel(pm).toString());

			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
		listView.setAdapter(new InstallAdapter(mContext, APPNAME, ICON, PACKAGE));
		webView.setVisibility(View.GONE);
	}
}