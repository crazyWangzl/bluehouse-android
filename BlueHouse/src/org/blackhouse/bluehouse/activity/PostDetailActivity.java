package org.blackhouse.bluehouse.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.blackhouse.bluehouse.R;
import org.blackhouse.bluehouse.entity.Comment;
import org.blackhouse.bluehouse.entity.Post;
import org.blackhouse.bluehouse.util.BaseUtil;
import org.blackhouse.bluehouse.util.Constants;
import org.blackhouse.bluehouse.util.NetUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;


/**
 * ��������
 * @author leo
 *
 */
public class PostDetailActivity extends Activity {
	
	private static final String TAG = PostDetailActivity.class.getSimpleName();

	
	private String id;
	private Post post;
	private TextView tv_post_title;
	private TextView tv_post_content;
	private TextView tv_post_created;
	private TextView tv_post_modified;
	private TextView tv_comment;
	
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	private Comment comment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		id = this.getIntent().getStringExtra("id");
		post = (Post) this.getIntent().getSerializableExtra("post");
		new GetPostDetailTask().execute();
		setContentView(R.layout.activity_post_detail);
		findView();
		setListener();
		setText();
		super.onCreate(savedInstanceState);
	}
	
	
	private void findView(){
		tv_post_title = (TextView) findViewById(R.id.tv_post_title);
		tv_post_content = (TextView) findViewById(R.id.tv_post_content);
		tv_post_created = (TextView) findViewById(R.id.tv_post_created);
		tv_post_modified = (TextView) findViewById(R.id.tv_post_modified);
	    tv_comment = (TextView) findViewById(R.id.tv_comment);
	}
	
	
	private void setText(){
		tv_post_title.setText("���⣺"+post.getTitle());
		tv_post_content.setText("���ݣ�"+ post.getContent());
		tv_post_created.setText("����ʱ�䣺"+ BaseUtil.convertTime(post.getCreated(),"yyyy-MM-dd HH:mm"));
		tv_post_modified.setText("�޸�ʱ�䣺"+BaseUtil.convertTime(post.getModified(),"yyyy-MM-dd HH:mm"));
	}
	private void setListener(){
		tv_comment.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			}});
	}
	
	private class GetPostDetailTask extends AsyncTask<Void,Void,JSONObject>{

		@Override
		protected JSONObject doInBackground(Void... params) {
			JSONObject jsonObject = null;
			HashMap<String,String> data = new HashMap<String,String>();
			data.put("id",id);
			try {
				jsonObject = NetUtil.sendGETRequest(Constants.BASEURL+Constants.POST+"/",data,"UTF-8");
	            Log.d(TAG, "jsonObject"+jsonObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
            return jsonObject;			
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			Log.d(TAG, "result"+result);
			if(result != null){
				comments.clear();
				try {
					JSONArray items = result.getJSONObject("comments").getJSONArray("items");
	                for(int i = 0; i < items.length(); i++){
	                	JSONObject object = items.getJSONObject(i);
	                	comment = new Comment();
	                	comment.setId(object.getString("id"));
	                	comment.setContent(object.getString("content"));
	                	
	                	comments.add(comment);
	                }
	                tv_comment.setText("������:"+comments.size());
				} catch (JSONException e1) {
					e1.printStackTrace();
					Toast.makeText(PostDetailActivity.this,"����ת��һ��",Toast.LENGTH_SHORT).show();
					
				}
			}else{
				Toast.makeText(PostDetailActivity.this,"��ȡ����ʧ��",Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}
         		
	}
	
}
