package no.itera.sbuddy.util;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import no.itera.sbuddy.model.Product;

import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class ProductSAXMarshaller extends DefaultHandler {

	private Product vare;

	private List<Product> varer = new ArrayList<Product>();

	private String field;

	public List<Product> unmarshall(String input) throws Exception {

		XMLReader xr = new SAXParser();
		xr.setContentHandler(this);
		xr.setErrorHandler(this);
		Reader reader = new StringReader(input);
		InputSource source = new InputSource(reader);
		xr.parse(source);
		return varer;
	}

	@Override
	public void startElement(String uri, String name, String qName, Attributes atts) {
		if ("".equals(uri)) {
			field = qName;
			if (qName.equals(Product.class.getSimpleName())) {
				vare = new Product();
			}
		}
	}

	@Override
	public void endElement(String uri, String name, String qName) {
		if ("".equals(uri)) {
			if (qName.equals(Product.class.getSimpleName())) {
				varer.add(vare);
			}
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {
		StringBuilder builder = new StringBuilder();
		for (int i = start; i < start + length; i++) {
			builder.append(ch[i]);
		}
		try {
			Class<?> c = vare.getClass();
			Field cfield = c.getDeclaredField(field);
			if (c != null) {
				cfield.setAccessible(true);
				cfield.set(vare, builder.toString());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

	}

}
