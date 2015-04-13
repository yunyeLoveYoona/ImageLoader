package com.yun.imageloader.context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yun.imageloader.ImageLoader;
import com.yun.imageloader.R;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			ImageLoader
					.getInstance(getActivity())
					.load((ImageView) rootView.findViewById(R.id.image1),
							"http://img0.bdstatic.com/img/image/f3165146edddf5807b7cb40a426ffaaf1426747348.jpg",
							R.drawable.ic_launcher);
			ImageLoader
					.getInstance(getActivity())
					.load((ImageView) rootView.findViewById(R.id.image2),
							"http://h.hiphotos.baidu.com/image/w%3D230/sign=fcf88e34a41ea8d38a227307a70b30cf/38dbb6fd5266d016c626a7a7952bd40735fa3505.jpg",
							R.drawable.ic_launcher);
			ImageLoader
					.getInstance(getActivity())
					.load((ImageView) rootView.findViewById(R.id.image3),
							"http://b.hiphotos.baidu.com/image/w%3D230/sign=790b1813700e0cf3a0f749f83a46f23d/64380cd7912397dd481d2d055b82b2b7d0a2874f.jpg",
							R.drawable.ic_launcher,true);
			ImageLoader
					.getInstance(getActivity())
					.load((ImageView) rootView.findViewById(R.id.image4),
							"http://a.hiphotos.baidu.com/image/w%3D230/sign=ea9e16090ed79123e0e093779d345917/b58f8c5494eef01f1a375ec4e2fe9925bc317d8b.jpg",
							R.drawable.ic_launcher,true);
			ImageLoader
					.getInstance(getActivity())
					.load((ImageView) rootView.findViewById(R.id.image5),
							"http://h.hiphotos.baidu.com/image/w%3D230/sign=fedd4185d2a20cf44690f9dc46084b0c/9825bc315c6034a8f9807e83c913495409237656.jpg",
							R.drawable.ic_launcher,true);
			ImageLoader
					.getInstance(getActivity())
					.load((ImageView) rootView.findViewById(R.id.image6),
							"http://d.hiphotos.baidu.com/image/w%3D230/sign=7ff1fb19369b033b2c88fbd925cf3620/203fb80e7bec54e79d0afc03bb389b504ec26acd.jpg",
							R.drawable.ic_launcher,true);
			ImageLoader
					.getInstance(getActivity())
					.load((ImageView) rootView.findViewById(R.id.image7),
							"http://img0.bdstatic.com/img/image/f3165146edddf5807b7cb40a426ffaaf1426747348.jpg",
							R.drawable.ic_launcher,true);
			ImageLoader
					.getInstance(getActivity())
					.load((ImageView) rootView.findViewById(R.id.image8),
							"http://img0.bdstatic.com/img/image/f3165146edddf5807b7cb40a426ffaaf1426747348.jpg",
							R.drawable.ic_launcher);
			ImageLoader
					.getInstance(getActivity())
					.load((ImageView) rootView.findViewById(R.id.image9),
							"http://img0.bdstatic.com/img/image/f3165146edddf5807b7cb40a426ffaaf1426747348.jpg",
							R.drawable.ic_launcher);
			ImageLoader
					.getInstance(getActivity())
					.load((ImageView) rootView.findViewById(R.id.image10),
							"http://img0.bdstatic.com/img/image/f3165146edddf5807b7cb40a426ffaaf1426747348.jpg",
							R.drawable.ic_launcher);
			return rootView;
		}
	}

}
