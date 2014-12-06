package com.iuce.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.iuce.entity.Horoscope;

import android.os.AsyncTask;

public class HoroscopeService extends AsyncTask<String, Void, Horoscope[]> {

	@Override
	protected Horoscope[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		HoroscopeParser parser = new HoroscopeParser();
		return parser.GetHoroscope(params[0]);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Horoscope[] result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	class HoroscopeParser {
		public Horoscope[] GetHoroscope(String url) {
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
				Horoscope[] horoscopes = new Horoscope[node.getLength()];
				int counter = 0;
				for (int i = 0; i < node.getLength(); i++) {
					Horoscope h = new Horoscope();
					Element horoscope = (Element) node.item(i);

					// String
					String title = horoscope.getElementsByTagName("title")
							.toString();
					String description = horoscope.getElementsByTagName(
							"description").toString();
					System.out.println("title:" + title + "description: "
							+ description);
					h.setTitle(title);
					h.setDescription(description);
					horoscopes[counter] = h;
					counter++;
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
