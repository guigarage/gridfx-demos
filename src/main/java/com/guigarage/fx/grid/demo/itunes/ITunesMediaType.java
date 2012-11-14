package com.guigarage.fx.grid.demo.itunes;

public enum ITunesMediaType {
	MOVIE("movie"), PODCAST("podcast"), MUSIC("music"), MUSICVIDEO("musicVideo"), AUDIOBOOK("audiobook"), SHORTFILM("shortFilm"), TVSHOW("tvShow"), SOFTWARE("software"), EBOOK("ebook"), ALL("all");
	
	private String mediaType;
	
	private ITunesMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	@Override
	public String toString() {
		return mediaType;
	}
}
