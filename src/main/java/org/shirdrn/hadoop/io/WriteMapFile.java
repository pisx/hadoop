package org.shirdrn.hadoop.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.Text;

public class WriteMapFile {

public static void main(String[] args) {
		
		String localFile = args[0];
		String targetFile = args[1];
		
		Configuration conf = new Configuration();
		FileSystem fs = null; 
		
		Text domain = new Text();
		IntWritable len = new IntWritable();
		BufferedReader reader = null;
		MapFile.Writer writer = null;
		
		try {
			reader = new BufferedReader(new FileReader(localFile));
			fs = FileSystem.get(URI.create(targetFile), conf);
			writer = new MapFile.Writer(conf, fs, targetFile, Text.class, IntWritable.class);
			String line = null;
			while((line = reader.readLine()) != null) {
				line = line.trim();
				if(!line.isEmpty()) {
					domain.set(line);
					len.set(line.length());
					writer.append(domain, len);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(writer);
			IOUtils.closeStream(reader);
		}
		
	}

}
