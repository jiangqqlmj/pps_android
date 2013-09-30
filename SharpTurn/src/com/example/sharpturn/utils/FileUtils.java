package com.example.sharpturn.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.example.sharpturn.R;
import com.example.sharpturn.model.SharpModel;

public class FileUtils {
    public static List<SharpModel> getSharpModels(Context pContext)
    {
    	InputStream iStream= pContext.getResources().openRawResource(R.raw.sharp_question);
    	String line="";
    	List<SharpModel> mLists=new ArrayList<SharpModel>();
    	SharpModel model;
    	try {
    		BufferedReader br=new BufferedReader(new InputStreamReader(iStream, "utf-8"));
		    while((line=(br.readLine()))!=null)
			{
		    	model=new SharpModel();
		    	int index = line.indexOf("答案");
		    	String name=line.substring(0, index-1);
		    	String answer=line.substring(index, line.length()-1);
		    	model.setName(name);
		    	model.setAnswer(answer);
		    	//Log.d("jiangqq", "name = "+name+",answer = "+answer);
		    	mLists.add(model);
			}
		    return mLists;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
}
