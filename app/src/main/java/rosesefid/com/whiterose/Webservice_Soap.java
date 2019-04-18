package rosesefid.com.whiterose;

import android.os.AsyncTask;
import android.util.Log;

import org.joda.time.DateTime;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Webservice_Soap {

    public static SoapObject result = null;
    public static SoapObject result1 = null;

    static abstract class getData extends AsyncTask<String, Void, String> {

        private InputStream is;

        @Override
        protected String doInBackground(String... urls) {
            final String SOAP_URL = urls[0];
            final String username = urls[1];
            final String password = urls[2];
            final String method = "getCountries";
            final String nameSpace = "http://tempuri.org/";
            final String action = "http://tempuri.org/add";

            SoapObject request;

            try {
                request = new SoapObject(nameSpace, method);
                request.addProperty("username", username);
                request.addProperty("password", password);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                //Web method call
                HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
                androidHttpTransport.call(nameSpace + method, envelope);

                //get the response
                result = (SoapObject) envelope.getResponse();
            } catch (Exception ex) {
                Log.e("mohammad", "Error: " + ex.getMessage());
            }
            return String.valueOf(result);
        }

        protected abstract void onPostExecute(String result);
    }

    static abstract class getPriceData extends AsyncTask<String, Void, String> {

        private Date formatedDate;
        private String formatedDateString;
        private Date formatedDate1;
        private DateTime dt;

        @Override
        protected String doInBackground(String... urls) {
            final String SOAP_URL = urls[0];
            final String username = urls[1];
            final String password = urls[2];
            Long country_code = Long.parseLong(urls[3]);

            int duration_code = Integer.parseInt(urls[5]);
            final String plan = urls[6];

            String time = "1980 11 23 00:00:00";
//            try {
//                SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
//                formatedDate = format.parse(time);
//
//                SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//                formatedDateString = serverFormat.format(formatedDate);
//
//                formatedDate1 = serverFormat.parse(formatedDateString);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }]
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd MM yyyy");
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String birth = null;
            try {
                Date date = originalFormat.parse("21 6 2013");
                birth = targetFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

//            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss");
//            LocalDateTime birth = LocalDateTime.parse("2011-10-06T03:35:05",formatter);
//            DateTime birth = formatter.parseDateTime(time);

            final Long code = country_code;
            final int duration = duration_code;


            final String methodName = plan;
            final String soapNamespace = "http://tempuri.org/";
            final String soapAction = "http://tempuri.org/add";

            SoapObject request;

            try {
                request = new SoapObject(soapNamespace, methodName);
                request.addProperty("username", username);
                request.addProperty("password", password);
                request.addProperty("countryCode", code);
                request.addProperty("birthdate", birth);
                request.addProperty("durationOfStay", duration);
//                request.addProperty("planCode", plan);
//                Log.d("request", String.valueOf(formatedDate) + "\n" + String.valueOf(formatedDateString) + "\n" + String.valueOf(formatedDate1));
                Log.d("mohammad", String.valueOf(birth));
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                //Web method call
                HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
                androidHttpTransport.call(soapNamespace + methodName, envelope);

                //get the response
                result1 = (SoapObject) envelope.getResponse();
            } catch (Exception ex) {
                Log.e("mohammad", "Error: " + ex.getMessage());
            }
            return String.valueOf(result1);
        }

        protected abstract void onPostExecute(String result);
    }
}


