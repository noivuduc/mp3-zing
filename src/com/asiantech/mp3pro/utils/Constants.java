package com.asiantech.mp3pro.utils;

import java.util.ArrayList;

import android.os.Handler;

import com.asiantech.mp3pro.bean.Song;

public class Constants {
	public static Song mBaiHat = new Song();
	public static int INDEX;
	public static ArrayList<Song> mBaiHats;
	public static int CURRENT_PLAY_ID;
	public static ArrayList<String> Albums;
	
	public static boolean SONG_PAUSE=false;
	public static boolean STATUS_PLAYING=true;
	public static boolean RANDOM_PLAYING=true;
	public static boolean SERVICE_RUNNING=false;
	
	public static int LOOP=0;
	
	public static String PLAY_SONG="play";
	public static String NEXT_SONG="next";
	public static String PREV_SONG="prev";
	public static String PAUSE_SONG="pause";
	public static String PLAY_MSG = "play";
	public static String PAUSE_MSG = "pause";
	public static String CHANGE_TITLE = "change";
	
	public static Handler SONG_CHANGE_HANDLER;
	public static Handler SONG_PAUSE_HANDLER;
	public static Handler PROGRESS_HANDLER;
	public static Handler PROGRESS_CHANGE;
	public static Handler CHANGE_TITlE_BAR;
}
