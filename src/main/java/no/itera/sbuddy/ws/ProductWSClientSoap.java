package no.itera.sbuddy.ws;

import java.io.IOException;

import no.itera.sbuddy.model.Product;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class ProductWSClientSoap {
	
	private static final String NAMESPACE = "http://service.pservice.andsim.no/";
	private static String URL = //"http://172.16.9.190:9191/ProductService?wsdl";
			"http://vareservice.herokuapp.com/ProductService?wsdl";
	private static final String METHOD_NAME = "sendProduct";
	private static final String SOAP_ACTION = "http://service.pservice.andsim.no/sendProduct";


	public Boolean sendProductToWS(Product vare) {
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		PropertyInfo propInfo = new PropertyInfo();
		propInfo.name = "arg0";
		propInfo.type = vare.getClass();
		propInfo.setValue(vare);
		request.addProperty(propInfo);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);

		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
			String returnMessage = resultsRequestSOAP.toString();
			return new Boolean(returnMessage);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return null;
	}

}
