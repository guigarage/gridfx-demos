package com.guigarage.fx.grid.demo.itunes;

import org.javafxdata.datasources.provider.ObjectDataSource;


public class ITunesDataSource extends ObjectDataSource<ITunesMedia> {

	public ITunesDataSource(ITunesDataSourceReader reader) {
		super(reader, "results", ITunesMedia.class);
	}

}
