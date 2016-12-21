package yelp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class GoogleMapAPI {

	private static final String API_HOST = "maps.googleapis.com";
	private static final String SEARCH_PATH = "/maps/api/geocode";
	private static final String OUTPUT = "/json";
	private static final String API_KEY = "AIzaSyAta4SHowJFGKQ78248FYFbhT64Ii3A4q8";

	OAuthService service;
	Token accessToken;

	/**
	 * Setup the Yelp API OAuth credentials.
	 */
	public GoogleMapAPI() {

	}

	/**
	 * Creates and sends a request to the Search API by term and location.
	 */
	public String searchForBusinessesByLocation(String address) {
		OAuthRequest request = new OAuthRequest(Verb.GET, "https://" + API_HOST + SEARCH_PATH + OUTPUT);

		request.addQuerystringParameter("address", address);
		request.addQuerystringParameter("key", API_KEY);

		System.out.println(request);
		return sendRequestAndGetResponse(request);
	}

	/**
	 * Sends an {@link OAuthRequest} and returns the {@link Response} body.
	 */
	private String sendRequestAndGetResponse(OAuthRequest request) {
		System.out.println("Querying " + request.getCompleteUrl() + " ...");
		Response response = request.send();
		return response.getBody();
	}

	/**
	 * Queries the Search API based on the command line arguments and takes the
	 * first result to query the Business API.
	 */
	private static void queryAPI(GoogleMapAPI googleMapAPI, String address) {
		String searchResponseJSON = googleMapAPI.searchForBusinessesByLocation(address);
		JSONObject response = null;
		try {
			response = new JSONObject(searchResponseJSON);
			JSONArray results = (JSONArray) response.get("results");
			for (int i = 0; i < results.length(); i++) {
				JSONObject result = (JSONObject) results.get(i);
				JSONObject geometry = (JSONObject) result.get("geometry");
				JSONObject location = (JSONObject) geometry.get("location");
				System.out.println(location);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main entry for sample Yelp API requests.
	 */
	public static void main(String[] args) {
		GoogleMapAPI googleMapAPI = new GoogleMapAPI();
		queryAPI(googleMapAPI, "1600 Amphitheatre Parkway, Mountain View, CA");
		queryAPI(googleMapAPI, "2650 Keystone Ave., Santa Clara, CA");
	}
}


