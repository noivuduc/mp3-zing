package com.asiantech.mp3pro.parse;

import android.app.Activity;

import com.parse.Parse;

public class ParseData extends Activity{
	public ParseData(){
		Parse.enableLocalDatastore(this);
		 
		Parse.initialize(this, "XzPsjmh2W9LtyqqlM6o0RymrdRLeyHhkAfVSkEAQ", "06EnmjNeHmyvAojzWjbFsSPsi3ZM2oy1GKVp8hm9");

	}
}
