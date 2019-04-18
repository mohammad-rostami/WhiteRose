package rosesefid.com.whiterose;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;


public class Webservice {
    EditText txt;
    TextView part1;
    TextView part2;
    String data;
    String[] separated;
    private ProgressDialog progress;

    static class PostClass extends AsyncTask<String, Void, String> {

        private InputStream is;

        @Override
        protected String doInBackground(String... urls) {
            String post_result = null;
            String linkType = urls[0];
            String firstPart = urls[1];
            String secondPart = urls[2];
//                String thirdPart = urls[3];
            String Url = linkType;
//                Integer thirdConvert = Integer.parseInt(thirdPart);
            post_result = null;

            try {
                URL url = new URL(Url);
                post_result = "";
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put(firstPart, Integer.parseInt(secondPart));

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();
                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return post_result;
        }
    }

    static class PostClassID extends AsyncTask<String, Void, String> {

        private InputStream is;

        @Override
        protected String doInBackground(String... urls) {
            String post_result = null;
            String linkType = urls[0];
            String firstPart = urls[1];
            String secondPart = urls[2];
            String thirdPart = urls[3];
            String forthPart = urls[4];
            String type = urls[5];

            String Url = linkType;

            post_result = null;

            try {
                URL url = new URL(Url);
                post_result = "";
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                if (type.toString().equals("token")) {
                    params1.put("webservice_username", firstPart);
                    params1.put("password", secondPart);
                }
                if (type.toString().equals("price")) {
                    params1.put("zone_code", firstPart);
                    params1.put("en_country_name", secondPart);
                    params1.put("duration", thirdPart);
                    params1.put("birthday", forthPart);
                }

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();
                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return post_result;
        }
    }

    static class GetClass extends AsyncTask<String, Void, String> {

        String get_result = null;
        private InputStream is;

        @Override
        protected String doInBackground(String... params) {
            try {

                URL mUrl = new URL(params[0]);
                get_result = "";
                HttpURLConnection httpConnection = (HttpURLConnection) mUrl.openConnection();
                httpConnection.setRequestMethod("GET");
                httpConnection.setRequestProperty("Content-length", "0");
                httpConnection.setUseCaches(false);
                httpConnection.setAllowUserInteraction(false);
                httpConnection.setConnectTimeout(100000);
                httpConnection.setReadTimeout(100000);

                httpConnection.connect();

                int responseCode = httpConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                        get_result += line;
                    }
                    br.close();
                    return sb.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return get_result;
        }
    }

    static class SecondGetClass extends AsyncTask<String, Void, String> {

        String get_result = null;
        private InputStream is;

        @Override
        protected String doInBackground(String... params) {
            try {

                URL mUrl = new URL(params[0]);
                get_result = "";
                HttpURLConnection httpConnection = (HttpURLConnection) mUrl.openConnection();
                httpConnection.setRequestMethod("GET");
                httpConnection.setRequestProperty("Content-length", "0");
                httpConnection.setUseCaches(false);
                httpConnection.setAllowUserInteraction(false);
                httpConnection.setConnectTimeout(100000);
                httpConnection.setReadTimeout(100000);

                httpConnection.connect();

                int responseCode = httpConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                        get_result += line;
                    }
                    br.close();
                    return sb.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return get_result;
        }
    }

}
