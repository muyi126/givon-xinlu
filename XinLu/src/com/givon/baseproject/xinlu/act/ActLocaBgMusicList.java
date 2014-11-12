///* 
// * Copyright 2014 ShangDao.Ltd  All rights reserved.
// * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// * 
// * @ActLocaBgMusicList.java  2014年11月6日 下午8:25:04 - Guzhu
// * @author Guzhu
// * @email:muyi126@163.com
// * @version 1.0
// */
//
//package com.givon.baseproject.xinlu.act;
//
//import android.database.Cursor;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.Menu;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.givon.baseproject.xinlu.BaseListActivity;
//import com.givon.baseproject.xinlu.entity.BackgroundMusicModel;
//
//public class ActLocaBgMusicList extends BaseListActivity<BackgroundMusicModel> {
//	private int _ids[];// 存放音乐文件的id数组
//	private String _titles[];// 存放音乐文件的标题数组
//	private String _artists[]; // 存放音乐艺术家的标题数组
//	private String[] _path;// 存放音乐路过的标题数组
//	private String[] _times;// 存放总时间的标题数组
//	private String[] _album;// 存放专辑的标题数组
//	private int _sizes[];// 存放文件大小的标题数组
//	private String[] _displayname;//存放名称的标题数组
//	private Menu xmenu;//自定义菜单
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//	}
//
//	@Override
//	protected View getItemView(int position, View convertView, ViewGroup parent) {
//		return null;
//	}
//
//	/**
//	 * 显示MP3信息,其中_ids保存了所有音乐文件的_ID，用来确定到底要播放哪一首歌曲，_titles存放音乐名，用来显示在播放界面， 而_path存放音乐文件的路径（删除文件时会用到）。
//	 */
//	private void ShowMp3List() {
//		// 用游标查找媒体详细信息
//		Cursor cursor = this.getContentResolver().query(
//				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//				new String[] { MediaStore.Audio.Media.TITLE,// 标题，游标从0读取
//						MediaStore.Audio.Media.DURATION,// 持续时间,1
//						MediaStore.Audio.Media.ARTIST,// 艺术家,2
//						MediaStore.Audio.Media._ID,// id,3
//						MediaStore.Audio.Media.DISPLAY_NAME,// 显示名称,4
//						MediaStore.Audio.Media.DATA,// 数据，5
//						MediaStore.Audio.Media.ALBUM_ID,// 专辑名称ID,6
//						MediaStore.Audio.Media.ALBUM,// 专辑,7
//						MediaStore.Audio.Media.SIZE }, null, null, MediaStore.Audio.Media.ARTIST);// 大小,8
//		/** 判断游标是否为空，有些地方即使没有音乐也会报异常。而且游标不稳定。稍有不慎就出错了,其次，如果用户没有音乐的话，不妨可以告知用户没有音乐让用户添加进去 */
//		if (cursor != null && cursor.getCount() == 0) {
////			final MDialog xfdialog = new MDialog.Builder(MusicListActivity.this).setTitle("提示:")
////					.setMessage("你的手机未找到音乐,请添加音乐").setPositiveButton("确定", null).create();
////			xfdialog.show();
//			return;
//
//		}
//		/** 将游标移到第一位 **/
//		cursor.moveToFirst();
//		/** 分别将各个标题数组实例化 **/
//		_ids = new int[cursor.getCount()];//
//		_titles = new String[cursor.getCount()];
//		_artists = new String[cursor.getCount()];
//		_path = new String[cursor.getCount()];
//		_album = new String[cursor.getCount()];
//		_times = new String[cursor.getCount()];
//		_displayname = new String[cursor.getCount()];
//		_sizes = new int[cursor.getCount()];
//		/**
//		 * 这里获取路径的格式是_path[i]=c.geString,为什么这么写？是因为MediaStore.Audio.Media.DATA的关系
//		 * 得到的内容格式为/mnt/sdcard/[子文件夹名/]音乐文件名，而我们想要得到的是/sdcard/[子文件夹名]音乐文件名
//		 */
//		for (int i = 0; i < cursor.getCount(); i++) {
//			_ids[i] = cursor.getInt(3);
//			_titles[i] = cursor.getString(0);
//			_artists[i] = cursor.getString(2);
//			_path[i] = cursor.getString(5).substring(4);
//			/**************** 以下是为提供音乐详细信息而生成的 ***************************/
//			_album[i] = cursor.getString(7);
//			_times[i] = toTime(cursor.getInt(1));
//			_sizes[i] = cursor.getInt(8);
//			_displayname[i] = cursor.getString(4);
//			cursor.moveToNext();
//			/*** 一直将游标往下走 **/
//		}
//		listview.setAdapter(new MusicListAdapter(this, cursor));
//
//	}
//
//}
