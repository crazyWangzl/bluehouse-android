package org.blackhouse.bluehouse.activity;

import java.util.ArrayList;

import org.blackhouse.bluehouse.R;
import org.blackhouse.bluehouse.adapter.PostListAdapter;
import org.blackhouse.bluehouse.entity.Post;
import org.blackhouse.bluehouse.util.Constants;
import org.blackhouse.bluehouse.util.NetUtil;
import org.blackhouse.pulltorefresh.library.PullToRefreshBase;
import org.blackhouse.pulltorefresh.library.PullToRefreshListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 帖子列表
 * 
 * @author leo
 * 
 */
public class PostListActivity extends Activity {

	private static final String TAG = PostListActivity.class.getSimpleName();

	private PostListAdapter postListAdapter;
	private PullToRefreshListView refreshListView;
	private ListView listView;
	private ArrayList<Post> posts = new ArrayList<Post>();
	private Post post;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_post_list);
		findViews();
		setListener();
		super.onCreate(savedInstanceState);
		new GetPostListTask().execute();
	}

	private void setListener() {
		refreshListView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						new GetPostListTask().execute();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						new GetPostListTask().execute();
					}
				});
		listView = refreshListView.getRefreshableView();
		listView.setAdapter(postListAdapter);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
                 if(posts.size() > 0 ){
                	 Intent intent = new Intent(PostListActivity.this,PostDetailActivity.class);
                	 intent.putExtra("id",posts.get(position-1).getId());
                	 Bundle bundle = new Bundle();
                	 bundle.putSerializable("post",posts.get(position-1));
                	 intent.putExtras(bundle);
                	 startActivity(intent);
                 }				
			}});
	}

	private void findViews() {
		refreshListView = (PullToRefreshListView) findViewById(R.id.listview);
		postListAdapter = new PostListAdapter(this, posts);
	}

	private class GetPostListTask extends AsyncTask<Void, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Void... params) {
			JSONObject jsonObject = null;
			try {
				jsonObject = NetUtil.sendGETRequest(Constants.BASEURL
						+ Constants.POST, null, "UTF-8");
				Log.d(TAG, "jsonObject" + jsonObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonObject;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			Log.d(TAG, "result" + result);
			refreshListView.onRefreshComplete();
			if (result != null) {
				posts.clear();
				try {
					JSONArray items = result.getJSONObject("data")
							.getJSONArray("items");
					for (int i = 0; i < items.length(); i++) {
						JSONObject object = items.getJSONObject(i);
						post = new Post();
						post.setId(object.getString("id"));
						post.setContent(object.getString("content"));
						post.setEnabled(object.getString("enabled"));
						post.setCreated(object.getString("created"));
						post.setLast_comment_time(object
								.getString("last_comment_time"));
						post.setModified(object.getString("modified"));
						post.setStatus(object.getString("status"));
						post.setTitle(object.getString("title"));

						posts.add(post);

					}
				} catch (JSONException e) {
					Toast.makeText(PostListActivity.this,"数据转换异常",Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			} else {
				Toast.makeText(PostListActivity.this, "获取数据失败",
						Toast.LENGTH_SHORT).show();
			}
			postListAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

	}
}
