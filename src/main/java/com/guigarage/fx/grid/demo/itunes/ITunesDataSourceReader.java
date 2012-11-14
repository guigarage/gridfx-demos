package com.guigarage.fx.grid.demo.itunes;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.javafxdata.datasources.Format;
import org.javafxdata.datasources.reader.DataSourceReader;

public class ITunesDataSourceReader implements DataSourceReader {

	private String term;

	private String country;

	private int limit = 50;

	private ITunesMediaType mediaType;

	public ITunesDataSourceReader(String term) {
		this.term = term;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		if (limit > 200) {
			throw new IllegalArgumentException(
					"limit must be beetween 0 and 200");
		}
		this.limit = limit;
	}

	public ITunesMediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(ITunesMediaType mediaType) {
		this.mediaType = mediaType;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		
		StringBuilder urlBuilder = new StringBuilder("https://itunes.apple.com/search?term=" + getTerm());
		if(getCountry() != null) {
			urlBuilder.append("&country=" + getCountry());
		}
		if(getLimit() > 0) {
			urlBuilder.append("&limit=" + getLimit());
		}
		if(getMediaType() != null) {
			urlBuilder.append("&media=" + getMediaType());
		}
		return new URL(urlBuilder.toString()).openStream();
	}

	@Override
	public Format getInputFormat() {
		return Format.JSON;
	}

}
