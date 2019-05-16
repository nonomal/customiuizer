package name.mikanoshi.customiuizer.subs;

import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;

import java.util.Objects;

import name.mikanoshi.customiuizer.R;
import name.mikanoshi.customiuizer.SubFragment;
import name.mikanoshi.customiuizer.prefs.ListPreferenceEx;
import name.mikanoshi.customiuizer.utils.Helpers;

public class System extends SubFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		findPreference("pref_key_system_expandnotifs_apps").setEnabled(!Objects.equals(Helpers.prefs.getString("pref_key_system_expandnotifs", "1"), "1"));
		findPreference("pref_key_system_expandnotifs").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				findPreference("pref_key_system_expandnotifs_apps").setEnabled(!newValue.equals("1"));
				return true;
			}
		});

		findPreference("pref_key_system_expandnotifs_apps").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				openApps("pref_key_system_expandnotifs_apps");
				return true;
			}
		});

		findPreference("pref_key_system_hidefromrecents_apps").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				openApps("pref_key_system_hidefromrecents_apps");
				return true;
			}
		});

		findPreference("pref_key_system_popupnotif_cat").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				openSubFragment(new System_PopupNotif(), null, Helpers.SettingsType.Preference, Helpers.ActionBarType.HomeUp, R.string.system_popupnotif_title, R.xml.prefs_system_popupnotif);
				return true;
			}
		});

		findPreference("pref_key_system_detailednetspeed_cat").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				openSubFragment(new SubFragment(), null, Helpers.SettingsType.Preference, Helpers.ActionBarType.HomeUp, R.string.system_detailednetspeed_title, R.xml.prefs_system_detailednetspeed);
				return true;
			}
		});

		findPreference("pref_key_system_noscreenlock_cat").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				openSubFragment(new System_NoScreenLock(), null, Helpers.SettingsType.Preference, Helpers.ActionBarType.HomeUp, R.string.system_noscreenlock_title, R.xml.prefs_system_noscreenlock);
				return true;
			}
		});

		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
			((ListPreferenceEx)findPreference("pref_key_system_autogroupnotif")).setUnsupported(true);
		}
	}

}