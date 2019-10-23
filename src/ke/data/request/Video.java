package ke.data.request;

public class Video {
	
	//Attributes
	private String videoName;
	private String videoId;
	private String videoAlias;
	
	//Constructor
	public Video () {
		this.videoName = null;
		this.videoId = null;
		this.videoAlias = null;
	}
	
	public Video (String name, String id, String alias) {
		this.videoName = name;
		this.videoId = id;
		this.videoAlias = alias;
	}
	
	//Gets
	public String getVideoName() {
		return this.videoName;
	}
	
	public String getVideoId() {
		return this.videoId;
	}
	
	public String getVideoAlias() {
		return this.videoAlias;
	}
	
	
	//Sets
	public void setVideoName(String name) {
		this.videoName = name;
	}
	
	public void setVideoId(String id) {
		this.videoId = id;
	}
	
	public void setVideoAlias(String alias) {
		this.videoAlias = alias;
	}
	
}
