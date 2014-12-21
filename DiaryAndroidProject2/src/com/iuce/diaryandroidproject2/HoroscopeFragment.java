package com.iuce.diaryandroidproject2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.iuce.adapters.HoroscopeListAdapter;
import com.iuce.entity.Horoscope;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HoroscopeFragment extends Fragment {

	private ProgressDialog progDialog;
	private ListView listViewHoroscopes;
	List<Horoscope> horoscopes;
	HoroscopeListAdapter hAdapter;
	protected String HOROSCOPE_TITLE = "title";
	protected String HOROSCOPE_DESCRIPTION = "description";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_horoscope, container,
				false);
		getActivity().getActionBar().setIcon(R.drawable.ic_horoscope);
		horoscopes = new ArrayList<Horoscope>();
		if (isNetworkAvailable(getActivity())) {
			progDialog = new ProgressDialog(getActivity());
			progDialog.setMessage("Loading..");
			String horoscopeXmlUrl = "http://www.astrology.com/horoscopes/daily-horoscope.rss";
			listViewHoroscopes = (ListView) view
					.findViewById(R.id.lstViewHoroscope);
			new XmlHoroscopeTask().execute(horoscopeXmlUrl);
			hAdapter = new HoroscopeListAdapter(getActivity(),
					R.layout.fragment_horoscope, horoscopes);
			listViewHoroscopes.setAdapter(hAdapter);
			listViewHoroscopes.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					// TextView txt = (TextView) view
					// .findViewById(R.id.txtHoroscopeTitle);
					// Toast.makeText(getActivity(), txt.getText(),
					// Toast.LENGTH_LONG)
					// .show();3
					TextView txtTitle = (TextView) view
							.findViewById(R.id.txtHoroscopeTitle);
					TextView txtDescription = (TextView) view
							.findViewById(R.id.txtHoroscopeDescription);

					Bundle b = new Bundle();
					b.putString(HOROSCOPE_TITLE, txtTitle.getText().toString());
					b.putString(HOROSCOPE_DESCRIPTION, txtDescription.getText()
							.toString());
					HoroscopDetailActivity hDetail = new HoroscopDetailActivity();
					hDetail.setArguments(b);
					FragmentTransaction ft = getActivity().getFragmentManager()
							.beginTransaction();

					ft.add(R.id.content_frame, hDetail);
					ft.addToBackStack(null);
					ft.commit();
				}

			});
		}
		else
			Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG).show();
		
		return view;
	}

	public static boolean isNetworkAvailable(Context context) 
	{
	    return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
	}
	public class XmlHoroscopeTask extends
			AsyncTask<String, Void, List<Horoscope>> {
		@Override
		protected List<Horoscope> doInBackground(String... params) {
			// TODO Auto-generated method stub
			HoroscopeParser parser = new HoroscopeParser();
			return parser.GetHoroscope(params[0]);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progDialog.show();
		}

		@Override
		protected void onPostExecute(List<Horoscope> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progDialog.dismiss();
			synchronized (hAdapter) {
				hAdapter.notifyDataSetChanged();

			}
		}

		class HoroscopeParser {
			public List<Horoscope> GetHoroscope(String url) {
				try {

					URL uri = new URL(url);
					DocumentBuilderFactory factory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder build = factory.newDocumentBuilder();
					Document document = build.parse(new InputSource(uri
							.openStream()));
					document.getDocumentElement().normalize();

					NodeList node = document.getElementsByTagName("item");

					System.out.println("Gelen Kur Sayýsý=>" + node.getLength());

					for (int i = 0; i < node.getLength(); i++) {
						Horoscope h = new Horoscope();
						Element horoscope = (Element) node.item(i);

						// for title
						NodeList titleElmntLst = horoscope
								.getElementsByTagName("title");
						Element titleNmElmnt = (Element) titleElmntLst.item(0);
						NodeList title = titleNmElmnt.getChildNodes();

						// for description
						NodeList descriptionElmntLst = horoscope
								.getElementsByTagName("description");
						Element descriptionNmElmnt = (Element) descriptionElmntLst
								.item(0);
						NodeList description = descriptionNmElmnt
								.getChildNodes();

						// String
						String txtTitle = ((Node) title.item(0)).getNodeValue();
						txtTitle  = txtTitle.substring(0,txtTitle.indexOf(' '));

						String txtDescription = ((Node) description.item(0))
								.getNodeValue();
//						txtDescription = txtDescription.substring(0, txtDescription.indexOf("\n"));

						h.setTitle(txtTitle);
						h.setDescription(txtDescription);
						horoscopes.add(h);
					}
					return horoscopes;

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}
		}

	}

}
