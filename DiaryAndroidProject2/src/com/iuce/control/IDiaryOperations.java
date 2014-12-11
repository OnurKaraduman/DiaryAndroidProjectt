package com.iuce.control;

import java.util.List;

import com.iuce.entity.Diary;

public interface IDiaryOperations {

	//add
	//remove
	//list
	//edit
	
	public boolean addDiary(Diary diary);
	public boolean removeDiary(int id);
	public boolean updateDiary(int id, Diary diary);
	public List<Diary> listDiary();
	public Diary getDiaryWithDate(String date);
	public Diary getDiaryWithId(int id);

}
