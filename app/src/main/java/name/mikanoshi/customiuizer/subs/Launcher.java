package name.mikanoshi.customiuizer.subs;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.provider.Settings;

import name.mikanoshi.customiuizer.R;
import name.mikanoshi.customiuizer.SubFragment;
import name.mikanoshi.customiuizer.utils.Helpers;

public class Launcher extends SubFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//setupImmersiveMenu();

		Preference.OnPreferenceClickListener openSwipeEdit = new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Bundle args = new Bundle();
				args.putString("key", preference.getKey());
				openSubFragment(new MultiAction(), args, Helpers.SettingsType.Edit, Helpers.ActionBarType.Edit, preference.getTitleRes(), R.layout.prefs_swipe_gestures);
				return true;
			}
		};

		CheckBoxPreference.OnPreferenceChangeListener switchPrivacyAppState = new CheckBoxPreference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				Settings.Secure.putInt(getActivity().getContentResolver(), "is_privacy_apps_enable", (boolean)newValue ? 1 : 0);
				return true;
			}
		};

		Preference.OnPreferenceClickListener openPrivacyAppEdit = new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Bundle args = new Bundle();
				args.putBoolean("privacy", true);
				AppSelector appSelector = new AppSelector();
				appSelector.setTargetFragment(Launcher.this, 0);
				openSubFragment(appSelector, args, Helpers.SettingsType.Edit, Helpers.ActionBarType.HomeUp, R.string.select_app, R.layout.prefs_app_selector);
				return true;
			}
		};

		Preference pref;
		pref = findPreference("pref_key_launcher_swipedown");
		pref.setOnPreferenceClickListener(openSwipeEdit);
		pref = findPreference("pref_key_launcher_swipedown2");
		pref.setOnPreferenceClickListener(openSwipeEdit);
		pref = findPreference("pref_key_launcher_swipeup");
		pref.setOnPreferenceClickListener(openSwipeEdit);
		pref = findPreference("pref_key_launcher_swipeup2");
		pref.setOnPreferenceClickListener(openSwipeEdit);
		pref = findPreference("pref_key_launcher_swiperight");
		pref.setOnPreferenceClickListener(openSwipeEdit);
		pref = findPreference("pref_key_launcher_swipeleft");
		pref.setOnPreferenceClickListener(openSwipeEdit);
		pref = findPreference("pref_key_launcher_shake");
		pref.setOnPreferenceClickListener(openSwipeEdit);

		pref = findPreference("pref_key_launcher_privacyapps_list");
		pref.setOnPreferenceClickListener(openPrivacyAppEdit);
		pref = findPreference("pref_key_launcher_privacyapps_gest");
		pref.setOnPreferenceChangeListener(switchPrivacyAppState);

		PackageManager pm = getActivity().getPackageManager();
		if (!checkPermissions()) {
			pref = findPreference("pref_key_launcher_privacyapps");
			pref.setEnabled(false);
			pref.setTitle(R.string.launcher_privacyapps_fail);
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		PackageManager pm = getActivity().getPackageManager();
		if (checkPermissions()) {
			Preference pref = findPreference("pref_key_launcher_privacyapps_gest");
			((CheckBoxPreference)pref).setChecked(Settings.Secure.getInt(getActivity().getContentResolver(), "is_privacy_apps_enable", 0) == 1);
		}
	}

	private boolean checkPermissions() {
		PackageManager pm = getActivity().getPackageManager();
		return pm.checkPermission(Manifest.permission.WRITE_SECURE_SETTINGS, Helpers.modulePkg) == PackageManager.PERMISSION_GRANTED &&
			   pm.checkPermission("com.miui.securitycenter.permission.ACCESS_SECURITY_CENTER_PROVIDER", Helpers.modulePkg) == PackageManager.PERMISSION_GRANTED;
	}

//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.menu_launcher, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		if (item.getItemId() == R.id.restartlauncher)
//		try {
//			getActivity().sendBroadcast(new Intent("name.mikanoshi.customiuizer.mods.action.RestartLauncher"));
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//		return super.onOptionsItemSelected(item);
//	}
//
//	private void setupImmersiveMenu() {
//		ActionBar actionBar = getActionBar();
//		if (actionBar != null) actionBar.showSplitActionBar(false, false);
//		setImmersionMenuEnabled(true);
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		setupImmersiveMenu();
//	}

}