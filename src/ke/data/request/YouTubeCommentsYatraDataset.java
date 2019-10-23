package ke.data.request;

/*
 * 
 * */

import org.json.JSONException;
import org.json.JSONObject;

import emoji4j.EmojiUtils;

import org.apache.tika.language.LanguageIdentifier;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.*;


public class YouTubeCommentsYatraDataset {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	  InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }
  
  @SuppressWarnings("deprecation")
  public static String detectLanguage(String text) {
	 
	  LanguageIdentifier identifier = new LanguageIdentifier(text);
	    return identifier.getLanguage();
  }
  
  public static String obtainHashtag (String parameterString) {
	  String YourString = parameterString;

	  String REG_EX_TAG = ".*?\\s(#\\w+).*?";
	  
	  String tag;

	  Pattern tagMatcher = Pattern.compile(REG_EX_TAG);
	  Matcher m = tagMatcher.matcher(YourString);
	  if(m.find())
	  {
	      tag = m.group(1);
	      
	  } else {
		  tag=null;
	  }
	  return tag;
  }
  /*
  public static String obtainMinuteVideo (String parameterStringM) {
	  String YourString = parameterStringM;

	  String REG_EX_MINUTE = "([\\d]{1,2}):?([\\d]{1,2})?\\:([\\d]{1,2})";
	  
	  String minute;

	  Pattern tagMatcherM = Pattern.compile(REG_EX_MINUTE);
	  Matcher mTwo = tagMatcherM.matcher(YourString);
	  if(mTwo.find())
	  {
	      minute = mTwo.group(1);
	  } else {
		  minute = null;
	  }
	  
	  return minute;
  }
  */
  public static void main(String[] args) throws IOException, JSONException, FileNotFoundException {
	  //Apache Tika
	  
	  
	  //Old API Key (crmiguez@esei.uvigo.es)
	  String oldAPIKey = "AIzaSyDO5D4za_p88J8F-mi4nkBd_9zrDhRz2yM";
	  String oldAPIKeyTwo = "AIzaSyC8mpko6rAnwEF6xtMivc1UPL5YqnpWLf0";
	  
	  //New API Key
	  String newAPIKey = "AIzaSyBxBN0H9dc_fWGUzDroMspcQAvLoae9NJM";
	
     //Create a String List for videoId, some Sebastian Yatra's music videos
	  List<Video> videoList = new ArrayList<Video>();
	  
	  /*
	  // Vuelve (ft. Beret)
	  videoList.add(new Video("Vuelve (ft. Beret)",
			  					"dNa5gs24gis","vuelve"));
	  // Ya No Tiene Novio (ft. Mau y Ricky)
	  videoList.add(new Video("Ya No Tiene Novio (ft. Mau y Ricky)",
			  					"-qGbUNQqVNc","yanotienenovio"));
	  */
	  
	  /*
	  // My Only One (English Version No Hay Nadie Más, ft. Isabela Moner)
	  videoList.add(new Video("My Only One (English Version No Hay Nadie Más, ft. Isabela Moner)",
			  				  	"Y82H_pgqKdY","myonlyone"));
	  */
	
	// Robarte Un Beso (featuring with Carlos Vives)
	  videoList.add(new Video("Carlos Vives, Sebastian Yatra - Robarte un Beso",
				"Mtau4v6foHA","robarteunbeso"));
	  
	  
	  //A Partir De Hoy (featuring with David Bisbal)
	  videoList.add(new Video("David Bisbal, Sebastian Yatra - A Partir De Hoy",
				"Iwz4P8HfGVM","apartirdehoy"));
	  
	  
	  /*
	  
	  //Quiero Volver (featuring with TINI)
	  videoList.add(new Video("TINI, Sebastian Yatra - Quiero Volver",
				"u9nYCHkphnY","quierovolver"));
	  */

	  
	  //For-each loop to analyze every video
		for (Video v : videoList) {
			// JSON for the first time of requesting data
			JSONObject json = readJsonFromUrl("https://www.googleapis.com/youtube"
					+ "/v3/commentThreads?key="+ newAPIKey + "&textFormat=plainText&part=snippet%2Creplies" 
					+ "&videoId=" + v.getVideoId()
					+ "&maxResults=100");

			// Counter for pages of comments
			long pagesCounter = 1;
			// Counter of total comments
			long totalComments = 0;
			long totalCommentsEN = 0;
			long totalCommentsES = 0;
			//String for check separation in the comment/reply
			String newline = System.getProperty("line.separator");
			String textOriginalCommentOneString;
			String textOriginalReplyOneString;

			// Local variables for getting data from YouTube
			String replyUser;
			String channelReplyUser;
			String textDisplayReply;
			String textOriginalReply;
			int likesReplyUser;
			String publishedDateReplyUser;
			String updatedDateReplyUser;
			String mainUser;
			String channelUser;
			String textDisplayComment;
			String textOriginalComment;
			int likesMainUser;
			String publishedDateMainUser;
			String updatedDateMainUser;

			// Local variables for getting results from parsing text
			String reactionUser;
			String emojiUser;
			String countryUser;
			String languageUserC;
			String languageUserR;
			String elementVideo;
			String minuteVideoC;
			String minuteVideoR;
			String suggestUser;
			String artistMentioned;
			String songMentioned;
			String linkSocialMedia;
			String commercialComment;
			String plagiarismComment;
			String hashTagC;
			String hashTagR;
			
			String textWithoutEmojisC;
			String textWithoutEmojisR;
			/*
			 * Testing basic JSON gets
			 * 
			 */
			// System.out.println(json.toString());
			// System.out.println(json.get("items"));

			// Basic CSV file
			Date date = new Date(); // Using a Date object to make historical CSV files

			PrintWriter pw = new PrintWriter(new File("test_" + v.getVideoAlias() + "_" + date.getTime() + ".csv"));
			StringBuilder sb = new StringBuilder();
			
			PrintWriter pwEN = new PrintWriter(new File("EN_" + v.getVideoAlias() + "_" + date.getTime() + ".csv"));
			StringBuilder sbEN = new StringBuilder();
			
			PrintWriter pwES = new PrintWriter(new File("ES_" + v.getVideoAlias() + "_" + date.getTime() + ".csv"));
			StringBuilder sbES = new StringBuilder();
			
			
			// Header Main CSV
			sb.append("id");
			sb.append(';');
			sb.append("Name_User");
			sb.append(';');
			//sb.append("Channel_User");
			//sb.append(';');
			sb.append("Date_Comment");
			sb.append(';');
			sb.append("Type_Comment");
			sb.append(';');
			sb.append("Number_Likes");
			sb.append(';');
			sb.append("Text");
			sb.append(';');
			//sb.append("Text without emojis");
			//sb.append(';');
			sb.append("Hashtag");
			sb.append(';');
			//sb.append("Minute_Video");
			//sb.append(';');
			sb.append("Language");
			sb.append('\n');
			
			//Header English CSV
			sbEN.append("id");
			sbEN.append(';');
			sbEN.append("Name_User");
			sbEN.append(';');
			//sbEN.append("Channel_User");
			//sbEN.append(';');
			sbEN.append("Date_Comment");
			sbEN.append(';');
			sbEN.append("Type_Comment");
			sbEN.append(';');
			sbEN.append("Number_Likes");
			sbEN.append(';');
			sbEN.append("Text");
			sbEN.append(';');
			//sbEN.append("Text without emojis");
			//sbEN.append(';');
			sbEN.append("Hashtag");
			sbEN.append(';');
			//sbEN.append("Minute_Video");
			//sbEN.append(';');
			sbEN.append("Language");
			sbEN.append('\n');
			
			//Header Spanish CSV
			sbES.append("id");
			sbES.append(';');
			sbES.append("Name_User");
			sbES.append(';');
			//sbES.append("Channel_User");
			//sbES.append(';');
			sbES.append("Date_Comment");
			sbES.append(';');
			sbES.append("Type_Comment");
			sbES.append(';');
			sbES.append("Number_Likes");
			sbES.append(';');
			sbES.append("Text");
			sbES.append(';');
			//sbES.append("Text without emojis");
			//sbES.append(';');
			sbES.append("Hashtag");
			sbES.append(';');
			//sbES.append("Minute_Video");
			//sbES.append(';');
			sbES.append("Language");
			sbES.append('\n');
			

			/*
			 * Testing nextPageToken
			 * 
			 */
			// System.out.println("Next Page Token: "+ json.get("nextPageToken"));
			String pageToken = json.getString("nextPageToken");
			try {
				while (pageToken != null) {
					JSONArray array = json.getJSONArray("items");
					JSONArray arrayTwo = json.getJSONArray("items"); // There is a copy JSON to analyze the replies
					// org.json is not iterable, so it is necessary to do a for loop
					for (int i = 0; i < array.length(); ++i) {
						JSONObject rec = array.getJSONObject(i);
						JSONObject recTwo = arrayTwo.getJSONObject(i);
						// It checks if the main comment has replies
						if (recTwo.has("replies")) {
							// Data from user' comment
							JSONObject snippet = rec.getJSONObject("snippet");
							JSONObject topLevel = snippet.getJSONObject("topLevelComment");
							JSONObject snippetComment = topLevel.getJSONObject("snippet");
							mainUser = snippetComment.getString("authorDisplayName");
							channelUser = snippetComment.getString("authorChannelUrl");
							textDisplayComment = snippetComment.getString("textDisplay");
							textOriginalComment = snippetComment.getString("textOriginal");
							likesMainUser = snippetComment.getInt("likeCount");
							publishedDateMainUser = snippetComment.getString("publishedAt");
							updatedDateMainUser = snippetComment.getString("updatedAt");
							/*
							 * Testing comment
							 * 
							 */
							/*
							 * // Display data from main user System.out.println("-- COMMENT # " + (i + 1) +
							 * " --"); System.out.println("User: " + mainUser);
							 * System.out.println("Channel User: " + channelUser);
							 * System.out.println("Text Display: " + textDisplayComment);
							 * System.out.println("Text Original: " + textOriginalComment);
							 * System.out.println("Likes: " + likesMainUser);
							 * System.out.println("Date Published: " + publishedDateMainUser);
							 * System.out.println("Date Updated: " + updatedDateMainUser);
							 */

							++totalComments; // Count comments

							// Parsing comment text
							/*
							List<Result> resultsC = DetectLanguage.detect(textOriginalComment);

							Result result1 = resultsC.get(0);

							//System.out.println("Language: " + result1.language);
							languageUserC = result1.language;
							//System.out.println("Is reliable: " + result1.isReliable);
							//System.out.println("Confidence: " + result1.confidence);
							 * 
							 */
							
							textWithoutEmojisC = EmojiUtils.removeAllEmojis(textOriginalComment);
							languageUserC = detectLanguage(textWithoutEmojisC);
							hashTagC = obtainHashtag (textOriginalComment);
							//minuteVideoC = obtainMinuteVideo (textOriginalComment);
							
								textOriginalCommentOneString=textOriginalComment.trim().replaceAll("\\t+", "");

							// Binding to CSV file
							sb.append(totalComments);
							sb.append(';');
							sb.append(mainUser);
							sb.append(';');
							//sb.append(channelUser);
							//sb.append(';');
							sb.append(publishedDateMainUser);
							sb.append(';');
							sb.append("Comment");
							sb.append(';');
							sb.append(likesMainUser);
							sb.append(';');
							sb.append(textOriginalCommentOneString);
							sb.append(';');
							//sb.append(textWithoutEmojisC);
							//sb.append(';');
							sb.append(hashTagC);
							sb.append(';');
							//sb.append(minuteVideoC);
							//sb.append(';');
							sb.append(languageUserC);
							sb.append('\n');
							
							//ENGLISH
							if(languageUserC.equalsIgnoreCase("en")) {
								++totalCommentsEN; // Count comments English
								
								sbEN.append(totalCommentsEN);
								sbEN.append(';');
								sbEN.append(mainUser);
								sbEN.append(';');
								//sbEN.append(channelUser);
								//sbEN.append(';');
								sbEN.append(publishedDateMainUser);
								sbEN.append(';');
								sbEN.append("Comment");
								sbEN.append(';');
								sbEN.append(likesMainUser);
								sbEN.append(';');
								sbEN.append(textOriginalCommentOneString);
								sbEN.append(';');
								//sbEN.append(textWithoutEmojisC);
								//sbEN.append(';');
								sbEN.append(hashTagC);
								sbEN.append(';');
								//sbEN.append(minuteVideoC);
								//sbEN.append(';');
								sbEN.append(languageUserC);
								sbEN.append('\n');
							}
							
							//SPANISH
							if(languageUserC.equalsIgnoreCase("es") || languageUserC.equalsIgnoreCase("ca") || 
									languageUserC.equalsIgnoreCase("gl")) {
								++totalCommentsES; // Count comments Spanish
								
								sbES.append(totalCommentsES);
								sbES.append(';');
								sbES.append(mainUser);
								sbES.append(';');
								//sbES.append(channelUser);
								//sbES.append(';');
								sbES.append(publishedDateMainUser);
								sbES.append(';');
								sbES.append("Comment");
								sbES.append(';');
								sbES.append(likesMainUser);
								sbES.append(';');
								sbES.append(textOriginalCommentOneString);
								sbES.append(';');
								//sbES.append(textWithoutEmojisC);
								//sbES.append(';');
								sbES.append(hashTagC);
								sbES.append(';');
								//sbES.append(minuteVideoC);
								//sbES.append(';');
								sbES.append(languageUserC);
								sbES.append('\n');
							}

							// Data from replies
							JSONObject replies = recTwo.getJSONObject("replies");
							JSONArray arrayReplies = replies.getJSONArray("comments");
							for (int j = 0; j < arrayReplies.length(); ++j) {
								JSONObject recThree = arrayReplies.getJSONObject(j);
								JSONObject replyComment = recThree.getJSONObject("snippet");
								replyUser = replyComment.getString("authorDisplayName");
								channelReplyUser = replyComment.getString("authorChannelUrl");
								textDisplayReply = replyComment.getString("textDisplay");
								textOriginalReply = replyComment.getString("textOriginal");
								likesReplyUser = replyComment.getInt("likeCount");
								publishedDateReplyUser = replyComment.getString("publishedAt");
								updatedDateReplyUser = replyComment.getString("updatedAt");
								/*
								 * Testing replies from comment
								 * 
								 */
								// Display data from replies
								/*
								 * System.out.println("-- REPLY # " + (j + 1) + " --");
								 * System.out.println("User: " + replyUser); System.out.println("Channel User: "
								 * + channelReplyUser); System.out.println("Text Display: " + textDisplayReply);
								 * System.out.println("Text Original: " + textOriginalReply);
								 * System.out.println("Likes: " + likesReplyUser);
								 * System.out.println("Date Published: " + publishedDateReplyUser);
								 * System.out.println("Date Updated: " + updatedDateReplyUser);
								 */

								++totalComments; // Count replies

								// Parsing reply text
								/*
								List<Result> resultsR = DetectLanguage.detect(textOriginalReply);

								Result result2 = resultsC.get(0);

								//System.out.println("Language: " + result2.language);
								languageUserR = result2.language;
								//System.out.println("Is reliable: " + result2.isReliable);
								//System.out.println("Confidence: " + result2.confidence);
								 * 
								 */
								textWithoutEmojisR = EmojiUtils.removeAllEmojis(textOriginalReply);
								languageUserR = detectLanguage(textWithoutEmojisR);
								hashTagR = obtainHashtag (textOriginalReply);
								//minuteVideoR = obtainMinuteVideo (textOriginalReply);
								
								
									textOriginalReplyOneString=textOriginalReply.trim().replaceAll("\\t+", "");
								

								// Binding to CSV file
								sb.append(totalComments);
								sb.append(';');
								sb.append(replyUser);
								sb.append(';');
								//sb.append(channelReplyUser);
								//sb.append(';');
								sb.append(publishedDateReplyUser);
								sb.append(';');
								sb.append("Reply to " + mainUser);
								sb.append(';');
								sb.append(likesReplyUser);
								sb.append(';');
								sb.append(textOriginalReplyOneString);
								sb.append(';');
								//sb.append(textWithoutEmojisR);
								//sb.append(';');
								sb.append(hashTagR);
								sb.append(';');
								//sb.append(minuteVideoR);
								//sb.append(';');
								sb.append(languageUserR);
								sb.append('\n');
								
								//ENGLISH
								if(languageUserR.equalsIgnoreCase("en")) {
									++totalCommentsEN; // Count comments English
									
									sbEN.append(totalCommentsEN);
									sbEN.append(';');
									sbEN.append(replyUser);
									sbEN.append(';');
									//sbEN.append(channelReplyUser);
									//sbEN.append(';');
									sbEN.append(publishedDateReplyUser);
									sbEN.append(';');
									sbEN.append("Reply to " + mainUser);
									sbEN.append(';');
									sbEN.append(likesReplyUser);
									sbEN.append(';');
									sbEN.append(textOriginalReplyOneString);
									sbEN.append(';');
									//sbEN.append(textWithoutEmojisR);
									//sbEN.append(';');
									sbEN.append(hashTagR);
									sbEN.append(';');
									//sbEN.append(minuteVideoR);
									//sbEN.append(';');
									sbEN.append(languageUserR);
									sbEN.append('\n');
								}
								
								//SPANISH
								if(languageUserC.equalsIgnoreCase("es") || languageUserC.equalsIgnoreCase("ca") || 
										languageUserC.equalsIgnoreCase("gl")) {
									++totalCommentsES; // Count comments Spanish
									
									sbES.append(totalCommentsES);
									sbES.append(';');
									sbES.append(replyUser);
									sbES.append(';');
									//sbES.append(channelReplyUser);
									//sbES.append(';');
									sbES.append(publishedDateReplyUser);
									sbES.append(';');
									sbES.append("Reply to " + mainUser);
									sbES.append(';');
									sbES.append(likesReplyUser);
									sbES.append(';');
									sbES.append(textOriginalReplyOneString);
									sbES.append(';');
									//sbES.append(textWithoutEmojisR);
									//sbES.append(';');
									sbES.append(hashTagR);
									sbES.append(';');
									//sbES.append(minuteVideoR);
									//sbES.append(';');
									sbES.append(languageUserR);
									sbES.append('\n');
								}

							}
							// If the main comment has no reply
						} else {
							// Data from user' comment
							JSONObject snippet = rec.getJSONObject("snippet");
							JSONObject topLevel = snippet.getJSONObject("topLevelComment");
							JSONObject snippetComment = topLevel.getJSONObject("snippet");
							mainUser = snippetComment.getString("authorDisplayName");
							channelUser = snippetComment.getString("authorChannelUrl");
							textDisplayComment = snippetComment.getString("textDisplay");
							textOriginalComment = snippetComment.getString("textOriginal");
							likesMainUser = snippetComment.getInt("likeCount");
							publishedDateMainUser = snippetComment.getString("publishedAt");
							updatedDateMainUser = snippetComment.getString("updatedAt");

							/*
							 * Testing comment
							 * 
							 */
							// Display data from main user
							/*
							 * System.out.println("-- COMMENT # " + (i + 1) + " --");
							 * System.out.println("User: " + mainUser); System.out.println("Channel User: "
							 * + channelUser); System.out.println("Text Display: " + textDisplayComment);
							 * System.out.println("Text Original: " + textOriginalComment);
							 * System.out.println("Likes: " + likesMainUser);
							 * System.out.println("Date Published: " + publishedDateMainUser);
							 * System.out.println("Date Updated: " + updatedDateMainUser);
							 */

							++totalComments; // Count comments

							// Parsing comment text
							/*
							List<Result> resultsC3 = DetectLanguage.detect(textOriginalComment);

							Result result3 = resultsC3.get(0);

							//System.out.println("Language: " + result3.language);
							languageUserC = result3.language;
							//System.out.println("Is reliable: " + result3.isReliable);
							//System.out.println("Confidence: " + result3.confidence);
							 * 
							 */
							
							textWithoutEmojisC = EmojiUtils.removeAllEmojis(textOriginalComment);
							languageUserC = detectLanguage(textWithoutEmojisC);
							hashTagC = obtainHashtag (textOriginalComment);
							//minuteVideoC = obtainMinuteVideo (textOriginalComment);
							
								textOriginalCommentOneString=textOriginalComment.trim().replaceAll("\\t+", "");

							// Binding to CSV file
							sb.append(totalComments);
							sb.append(';');
							sb.append(mainUser);
							sb.append(';');
							//sb.append(channelUser);
							//sb.append(';');
							sb.append(publishedDateMainUser);
							sb.append(';');
							sb.append("Comment");
							sb.append(';');
							sb.append(likesMainUser);
							sb.append(';');
							sb.append(textOriginalCommentOneString);
							sb.append(';');
							//sb.append(textWithoutEmojisC);
							//sb.append(';');
							sb.append(hashTagC);
							sb.append(';');
							//sb.append(minuteVideoC);
							//sb.append(';');
							sb.append(languageUserC);
							sb.append('\n');
							
							//ENGLISH
							if(languageUserC.equalsIgnoreCase("en")) {
								++totalCommentsEN; // Count comments English
								
								sbEN.append(totalCommentsEN);
								sbEN.append(';');
								sbEN.append(mainUser);
								sbEN.append(';');
								//sbEN.append(channelUser);
								//sbEN.append(';');
								sbEN.append(publishedDateMainUser);
								sbEN.append(';');
								sbEN.append("Comment");
								sbEN.append(';');
								sbEN.append(likesMainUser);
								sbEN.append(';');
								sbEN.append(textOriginalCommentOneString);
								sbEN.append(';');
								//sbEN.append(textWithoutEmojisC);
								//sbEN.append(';');
								sbEN.append(hashTagC);
								sbEN.append(';');
								//sbEN.append(minuteVideoC);
								//sbEN.append(';');
								sbEN.append(languageUserC);
								sbEN.append('\n');
							}
							
							//SPANISH
							if(languageUserC.equalsIgnoreCase("es") || languageUserC.equalsIgnoreCase("ca") || 
									languageUserC.equalsIgnoreCase("gl")) {
								++totalCommentsES; // Count comments Spanish
								
								sbES.append(totalCommentsES);
								sbES.append(';');
								sbES.append(mainUser);
								sbES.append(';');
								//sbES.append(channelUser);
								//sbES.append(';');
								sbES.append(publishedDateMainUser);
								sbES.append(';');
								sbES.append("Comment");
								sbES.append(';');
								sbES.append(likesMainUser);
								sbES.append(';');
								sbES.append(textOriginalCommentOneString);
								sbES.append(';');
								//sbES.append(textWithoutEmojisC);
								//sbES.append(';');
								sbES.append(hashTagC);
								sbES.append(';');
								//sbES.append(minuteVideoC);
								//sbES.append(';');
								sbES.append(languageUserC);
								sbES.append('\n');
							}
							

						}
					}
					// New JSON File with new Page Token
					json = readJsonFromUrl("https://www.googleapis.com/youtube"
							+ "/v3/commentThreads?key="+ newAPIKey + "&textFormat=plainText&part=snippet%2Creplies" 
							+ "&videoId=" + v.getVideoId()
							+ "&maxResults=100&pageToken=" + pageToken);
					pageToken = json.getString("nextPageToken");
					++pagesCounter;
					System.out.println("---- NEXT PAGE OF COMMENTS: " + pagesCounter + " ----");
				}
				// If there is no nextPageToken
			} catch (Exception e) {
				// Save and close CSV file
				pw.write(sb.toString());
				pw.close();
				//ENGLISH
				pwEN.write(sbEN.toString());
				pwEN.close();
				//SPANISH
				pwES.write(sbES.toString());
				pwES.close();
				// Display recount of pages and total comments (main comments + replies)
				System.out.println("------------------------------------------------------");
				System.out.println("---- VIDEO NAME: "+ v.getVideoName() +" ----");
				System.out.println("---- RECOUNT OF COMMENTS... ----");
				System.out.println("---- Number of comments' pages:" + pagesCounter + " ----");
				System.out.println("---- Total comments:" + totalComments + " ----");
				System.out.println("------------------------------------------------------");
			}
		}
    System.out.println("---- END OF THE PROGRAM ----");
  }
}
