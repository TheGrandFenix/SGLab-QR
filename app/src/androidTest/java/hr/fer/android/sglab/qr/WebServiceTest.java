package hr.fer.android.sglab.qr;

/*
 * Created by lracki on 02.10.2018..
 */

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Base64;

public final class WebServiceTest {

    private static final String DEFAULT_ENDPOINT = "http://enter_ip_here:8080/api/sglab/machines/";
    private static final String USERNAME = "sglabadmin";
    private static final String PASSWORD = "sglabadmin";

    private static String encoded;

    @BeforeClass
    public static void setUpClass() throws Exception {
        encoded = Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(), Base64.NO_WRAP).trim();
    }

    @Test
    public void testGettingInfoFromWebServiceUsingMachineID1() throws Exception {

        //Given
        final String machineID = "1";
        final URL url = new URL(DEFAULT_ENDPOINT + machineID);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();

        //When
        con.setRequestProperty("Authorization", "Basic " + encoded);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("GET");


        StringBuilder result = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        //Then
        Assert.assertNotNull(result.toString());
    }

    @Test
    public void testGettingInfoFromWebServiceUsingMachineID2() throws Exception {

        //Given
        final String machineID = "2";
        final URL url = new URL(DEFAULT_ENDPOINT + machineID);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();

        //When
        con.setRequestProperty("Authorization", "Basic " + encoded);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("GET");


        StringBuilder result = new StringBuilder();
        int responseCode = con.getResponseCode();
        BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        //Then
        Assert.assertNotNull(result.toString());
    }

}
