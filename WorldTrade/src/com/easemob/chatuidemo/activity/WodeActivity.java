package com.easemob.chatuidemo.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMConversation.EMConversationType;
import com.easemob.chatuidemo.Constant;
import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.example.utils.UILApplication1;
import com.example.worldtrade.BaseActivity;
import com.example.worldtrade.R;

import com.easemob.chatuidemo.adapter.ChatAllHistoryAdapter;
import com.easemob.chatuidemo.db.InviteMessgeDao;

/**
 * 鏄剧ず鎵�鏈変細璇濊褰曪紝姣旇緝绠�鍗曠殑瀹炵幇锛屾洿濂界殑鍙兘鏄妸闄岀敓浜哄瓨鍏ユ湰鍦帮紝杩欐牱鍙栧埌鐨勮亰澶╄褰曟槸鍙帶鐨�
 * 
 */
public class WodeActivity extends BaseActivity {

	private InputMethodManager inputMethodManager;
	private ListView listView;
	private ChatAllHistoryAdapter adapter;
	private EditText query;
	private ImageButton clearSearch;
	public RelativeLayout errorItem;

	public TextView errorText;
	private boolean hidden;
	private List<EMConversation> conversationList = new ArrayList<EMConversation>();
    private Handler handler =new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case 1:
                initView();
                break;

            default:
                break;
            }
        };
    };
    private LinearLayout mLLmy1;
    private RelativeLayout mRla1;
	private String number;
	private TextView message_title;
	private String CHINESE;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	       setContentView(R.layout.wode);
	       mLLmy1 =(LinearLayout)this.findViewById(R.id.mLLmy1);
	       mRla1 =(RelativeLayout)this.findViewById(R.id.mRla1);
	       message_title =(TextView)this.findViewById(R.id.message_title);
			SharedPreferences mySharedPreferences1= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
			CHINESE =mySharedPreferences1.getString("CHINESE","1");
			if(CHINESE.equals("1")){
			
			}else{
				message_title.setText("Live Chat");
			}

	       
	       if (DemoHXSDKHelper.getInstance().isLogined()) {
	           initView();
	       }else{
	           mLLmy1.setVisibility(View.GONE);
	           mRla1.setVisibility(View.VISIBLE);
	           login();
	       }
	       	    
	        
	    }
private void login() {
	SharedPreferences mySharedPreferences= getSharedPreferences("USER", Activity.MODE_PRIVATE); 
	number =mySharedPreferences.getString("number","");

    EMChatManager.getInstance().login(number, "asdf22", new EMCallBack() {

        @Override
        public void onSuccess() {
            UILApplication1.getInstance().setUserName(number);
            UILApplication1.getInstance().setPassword("asdf22");
         handler.sendEmptyMessage(1);    
        }

        @Override
        public void onProgress(int progress, String status) {
        	Log.e("what", progress+"   "+status);
        }

        @Override
        public void onError(final int code, final String message) {
        	Log.e("what", code+"   "+message);

        }
    });
    }
void initView(){
    mLLmy1.setVisibility(View.VISIBLE);
    mRla1.setVisibility(View.GONE);

    inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
    errorItem = (RelativeLayout) this.findViewById(R.id.rl_error_item);
    errorText = (TextView) errorItem.findViewById(R.id.tv_connect_errormsg);        
    
    conversationList.addAll(loadConversationsWithRecentChat());
    listView = (ListView)this.findViewById(R.id.list);
    adapter = new ChatAllHistoryAdapter(this, 1, conversationList);
    // 璁剧疆adapter
    listView.setAdapter(adapter);
            
    
     final String st2 = getResources().getString(R.string.Cant_chat_with_yourself);
    listView.setOnItemClickListener(new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            EMConversation conversation = adapter.getItem(position);
            String username = conversation.getUserName();
           
            Intent intent = new Intent(WodeActivity.this, ChatActivity.class);
            intent.putExtra("userId", username);
            startActivity(intent);
         /*   if (username.equals(DemoApplication.getInstance().getUserName()))
               Toast.makeText(getApplicationContext(), st2, 0).show();
            else {
                // 杩涘叆鑱婂ぉ椤甸潰
                Intent intent = new Intent(WodeActivity.this, ChatActivity.class);
                if(conversation.isGroup()){
                    if(conversation.getType() == EMConversationType.ChatRoom){
                     // it is group chat
                        intent.putExtra("chatType", ChatActivity.CHATTYPE_CHATROOM);
                        intent.putExtra("groupId", username);
                    }else{
                     // it is group chat
                        intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
                        intent.putExtra("groupId", username);
                    }
                    
                }else{
                    // it is single chat
                    intent.putExtra("userId", username);
                }
                startActivity(intent);
            }*/
        }
    });
    // 娉ㄥ唽涓婁笅鏂囪彍鍗�
    registerForContextMenu(listView);

    listView.setOnTouchListener(new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // 闅愯棌杞敭鐩�
            hideSoftKeyboard();
            return false;
        }

    });
    // 鎼滅储妗�
    query = (EditText) this.findViewById(R.id.query);
    String strSearch = getResources().getString(R.string.search);
    query.setHint(strSearch);
    // 鎼滅储妗嗕腑娓呴櫎button
    clearSearch = (ImageButton)this.findViewById(R.id.search_clear);
    query.addTextChangedListener(new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            adapter.getFilter().filter(s);
            if (s.length() > 0) {
                clearSearch.setVisibility(View.VISIBLE);
            } else {
                clearSearch.setVisibility(View.INVISIBLE);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    });
    clearSearch.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            query.getText().clear();
            hideSoftKeyboard();
        }
    });
}
	    void hideSoftKeyboard() {
	        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
	            if (getCurrentFocus() != null)
	                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
	                        InputMethodManager.HIDE_NOT_ALWAYS);
	        }
	    }

	

    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
    }
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
        for(EMConversation conversation:EMChatManager.getInstance().getAllConversations().values()){
            if(conversation.getType() == EMConversationType.ChatRoom)
            chatroomUnreadMsgCount=chatroomUnreadMsgCount+conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal-chatroomUnreadMsgCount;
    }


	/**
	 * 鍒锋柊椤甸潰
	 */
	public void refresh() {
		conversationList.clear();
		conversationList.addAll(loadConversationsWithRecentChat());
		if(adapter != null)
		    adapter.notifyDataSetChanged();
	}

	/**
	 * 
	 * @param context
	 * @return
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        +	 */
	private List<EMConversation> loadConversationsWithRecentChat() {
		// 鑾峰彇鎵�鏈変細璇濓紝鍖呮嫭闄岀敓浜�
		Hashtable<String, EMConversation> conversations = EMChatManager.getInstance().getAllConversations();
		// 杩囨护鎺塵essages size涓�0鐨刢onversation
		/**
		 * 濡傛灉鍦ㄦ帓搴忚繃绋嬩腑鏈夋柊娑堟伅鏀跺埌锛宭astMsgTime浼氬彂鐢熷彉鍖�
		 * 褰卞搷鎺掑簭杩囩▼锛孋ollection.sort浼氫骇鐢熷紓甯�
		 * 淇濊瘉Conversation鍦⊿ort杩囩▼涓渶鍚庝竴鏉℃秷鎭殑鏃堕棿涓嶅彉 
		 * 閬垮厤骞跺彂闂
		 */
		List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
		synchronized (conversations) {
			for (EMConversation conversation : conversations.values()) {
				if (conversation.getAllMessages().size() != 0) {
					//if(conversation.getType() != EMConversationType.ChatRoom){
						sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
					//}
				}
			}
		}
		try {
			// Internal is TimSort algorithm, has bug
			sortConversationByLastChatTime(sortList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<EMConversation> list = new ArrayList<EMConversation>();
		for (Pair<Long, EMConversation> sortItem : sortList) {
			list.add(sortItem.second);
		}
		return list;
	}

	/**
	 * 鏍规嵁鏈�鍚庝竴鏉℃秷鎭殑鏃堕棿鎺掑簭
	 * 
	 * @param usernames
	 */
	private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
		Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
			@Override
			public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

				if (con1.first == con2.first) {
					return 0;
				} else if (con2.first > con1.first) {
					return 1;
				} else {
					return -1;
				}
			}

		});
	}


}
