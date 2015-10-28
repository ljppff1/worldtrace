/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easemob.chatuidemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.R.bool;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMChatRoomChangeListener;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.applib.model.HXNotifier;
import com.easemob.applib.model.HXNotifier.HXNotificationInfoProvider;
import com.easemob.applib.model.HXSDKModel;
import com.easemob.chat.CmdMessageBody;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.EMMessage.Type;
import com.easemob.chatuidemo.activity.ChatActivity;
import com.easemob.chatuidemo.domain.RobotUser;
import com.easemob.chatuidemo.domain.User;
import com.easemob.chatuidemo.utils.CommonUtils;
import com.easemob.util.EMLog;
import com.easemob.util.EasyUtils;
import com.example.worldtrade.R;

/**
 * Demo UI HX SDK helper class which subclass HXSDKHelper
 * @author easemob
 *
 */
public class DemoHXSDKHelper extends HXSDKHelper{

    private static final String TAG = "DemoHXSDKHelper";
    
    /**
     * EMEventListener
     */
    protected EMEventListener eventListener = null;

    /**
     * contact list in cache
     */
    private Map<String, User> contactList;
    
    /**
     * robot list in cache
     */
    private Map<String, RobotUser> robotList;
    
    
    /**
     * 閻€劍娼电拋鏉跨秿foreground Activity
     */
    private List<Activity> activityList = new ArrayList<Activity>();
    
    public void pushActivity(Activity activity){
        if(!activityList.contains(activity)){
            activityList.add(0,activity); 
        }
    }
    
    public void popActivity(Activity activity){
        activityList.remove(activity);
    }
    
    @Override
    public synchronized boolean onInit(Context context){
        if(super.onInit(context)){
            
            //if your app is supposed to user Google Push, please set project number
            String projectNumber = "562451699741";
            EMChatManager.getInstance().setGCMProjectNumber(projectNumber);
            return true;
        }
        
        return false;
    }
    
    @Override
    protected void initHXOptions(){
        super.initHXOptions();

        // you can also get EMChatOptions to set related SDK options
        EMChatOptions options = EMChatManager.getInstance().getChatOptions();
        options.allowChatroomOwnerLeave(getModel().isChatroomOwnerLeaveAllowed());  
    }

    @Override
    protected void initListener(){
        super.initListener();
        IntentFilter callFilter = new IntentFilter(EMChatManager.getInstance().getIncomingCallBroadcastAction());
        initEventListener();
    }
    
    /**
     * 閸忋劌鐪禍瀣╂閻╂垵鎯�
     * 閸ョ姳璐熼崣顖濆厴娴兼碍婀乁I妞ょ敻娼伴崗鍫濐槱閻炲棗鍩屾潻娆庨嚋濞戝牊浼呴敍灞惧娴犮儰绔撮懜顒�顩ч弸娣疘妞ょ敻娼板鑼病婢跺嫮鎮婇敍宀冪箹闁插苯姘ㄦ稉宥夋付鐟曚礁鍟�濞嗏�愁槱閻烇拷
     * activityList.size() <= 0 閹板繐鎳楅惈锟介幍锟介張澶愩�夐棃銏ゅ厴瀹歌尙绮￠崷銊ユ倵閸欐媽绻嶇悰宀嬬礉閹存牞锟藉懎鍑＄紒蹇曨瀲瀵拷Activity Stack
     */
    protected void initEventListener() {
        eventListener = new EMEventListener() {
            private BroadcastReceiver broadCastReceiver = null;
            
            @Override
            public void onEvent(EMNotifierEvent event) {
                EMMessage message = null;
                if(event.getData() instanceof EMMessage){
                    message = (EMMessage)event.getData();
                    EMLog.d(TAG, "receive the event : " + event.getEvent() + ",id : " + message.getMsgId());
                }
                
                switch (event.getEvent()) {
                case EventNewMessage:
                    //鎼存梻鏁ら崷銊ユ倵閸欏府绱濇稉宥夋付鐟曚礁鍩涢弬鐧營,闁氨鐓￠弽蹇斿絹缁�鐑樻煀濞戝牊浼�
                    if(activityList.size() <= 0){
                        HXSDKHelper.getInstance().getNotifier().onNewMsg(message);
                    }
                    break;
                case EventOfflineMessage:
                    if(activityList.size() <= 0){
                        EMLog.d(TAG, "received offline messages");
                        List<EMMessage> messages = (List<EMMessage>) event.getData();
                        HXSDKHelper.getInstance().getNotifier().onNewMesg(messages);
                    }
                    break;
                // below is just giving a example to show a cmd toast, the app should not follow this
                // so be careful of this
                case EventNewCMDMessage:
                {
                    
                    EMLog.d(TAG, "閺�璺哄煂闁繋绱跺☉鍫熶紖");
                    //閼惧嘲褰囧☉鍫熶紖body
                    CmdMessageBody cmdMsgBody = (CmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action;//閼惧嘲褰囬懛顏勭暰娑斿¨ction
                    
                    //閼惧嘲褰囬幍鈺佺潔鐏炵偞锟斤拷 濮濄倕顦╅惇浣烘殣
                    //message.getStringAttribute("");
                    EMLog.d(TAG, String.format("闁繋绱跺☉鍫熶紖閿涙瓫ction:%s,message:%s", action,message.toString()));
                    final String str = appContext.getString(R.string.receive_the_passthrough);
                    
                    final String CMD_TOAST_BROADCAST = "easemob.demo.cmd.toast";
                    IntentFilter cmdFilter = new IntentFilter(CMD_TOAST_BROADCAST);
                    
                    if(broadCastReceiver == null){
                        broadCastReceiver = new BroadcastReceiver(){

                            @Override
                            public void onReceive(Context context, Intent intent) {
                                // TODO Auto-generated method stub
                                Toast.makeText(appContext, intent.getStringExtra("cmd_value"), Toast.LENGTH_SHORT).show();
                            }
                        };
                        
                      //濞夈劌鍞介獮鎸庢尡閹恒儲鏁归懓锟�
                        appContext.registerReceiver(broadCastReceiver,cmdFilter);
                    }

                    Intent broadcastIntent = new Intent(CMD_TOAST_BROADCAST);
                    broadcastIntent.putExtra("cmd_value", str+action);
                    appContext.sendBroadcast(broadcastIntent, null);
                    
                    break;
                }
                case EventDeliveryAck:
                    message.setDelivered(true);
                    break;
                case EventReadAck:
                    message.setAcked(true);
                    break;
                // add other events in case you are interested in
                default:
                    break;
                }
                
            }
        };
        
        EMChatManager.getInstance().registerEventListener(eventListener);
        
        EMChatManager.getInstance().addChatRoomChangeListener(new EMChatRoomChangeListener(){
            private final static String ROOM_CHANGE_BROADCAST = "easemob.demo.chatroom.changeevent.toast";
            private final IntentFilter filter = new IntentFilter(ROOM_CHANGE_BROADCAST);
            private boolean registered = false;
            
            private void showToast(String value){
                if(!registered){
                  //濞夈劌鍞介獮鎸庢尡閹恒儲鏁归懓锟�
                    appContext.registerReceiver(new BroadcastReceiver(){

                        @Override
                        public void onReceive(Context context, Intent intent) {
                            Toast.makeText(appContext, intent.getStringExtra("value"), Toast.LENGTH_SHORT).show();
                        }
                        
                    }, filter);
                    
                    registered = true;
                }
                
                Intent broadcastIntent = new Intent(ROOM_CHANGE_BROADCAST);
                broadcastIntent.putExtra("value", value);
                appContext.sendBroadcast(broadcastIntent, null);
            }
            
            @Override
            public void onChatRoomDestroyed(String roomId, String roomName) {
                showToast(" room : " + roomId + " with room name : " + roomName + " was destroyed");
                Log.i("info","onChatRoomDestroyed="+roomName);
            }

            @Override
            public void onMemberJoined(String roomId, String participant) {
                showToast("member : " + participant + " join the room : " + roomId);
                Log.i("info", "onmemberjoined="+participant);
                
            }

            @Override
            public void onMemberExited(String roomId, String roomName,
                    String participant) {
                showToast("member : " + participant + " leave the room : " + roomId + " room name : " + roomName);
                Log.i("info", "onMemberExited="+participant);
                
            }

            @Override
            public void onMemberKicked(String roomId, String roomName,
                    String participant) {
                showToast("member : " + participant + " was kicked from the room : " + roomId + " room name : " + roomName);
                Log.i("info", "onMemberKicked="+participant);
                
            }

        });
    }

    /**
     * 閼奉亜鐣炬稊澶愶拷姘辩叀閺嶅繑褰佺粈鍝勫敶鐎癸拷
     * @return
     */
    @Override
    protected HXNotificationInfoProvider getNotificationListener() {
        //閸欘垯浜掔憰鍡欐磰姒涙顓婚惃鍕啎缂冿拷
        return new HXNotificationInfoProvider() {
            
            @Override
            public String getTitle(EMMessage message) {
              //娣囶喗鏁奸弽鍥暯,鏉╂瑩鍣锋担璺ㄦ暏姒涙顓�
                return null;
            }
            
            @Override
            public int getSmallIcon(EMMessage message) {
              //鐠佸墽鐤嗙亸蹇撴禈閺嶅浄绱濇潻娆撳櫡娑撴椽绮拋锟�
                return 0;
            }
            
            @Override
            public String getDisplayedText(EMMessage message) {
                // 鐠佸墽鐤嗛悩鑸碉拷浣圭埉閻ㄥ嫭绉烽幁顖涘絹缁�鐚寸礉閸欘垯浜掗弽瑙勫祦message閻ㄥ嫮琚崹瀣粵閻╃绨查幓鎰仛
                String ticker = CommonUtils.getMessageDigest(message, appContext);
                if(message.getType() == Type.TXT){
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[鐞涖劍鍎廬");
                }
                Map<String,RobotUser> robotMap=((DemoHXSDKHelper)HXSDKHelper.getInstance()).getRobotList();
    			if(robotMap!=null&&robotMap.containsKey(message.getFrom())){
    				String nick = robotMap.get(message.getFrom()).getNick();
    				if(!TextUtils.isEmpty(nick)){
    					return nick + ": " + ticker;
    				}else{
    					return message.getFrom() + ": " + ticker;
    				}
    			}else{
    				return message.getFrom() + ": " + ticker;
    			}
            }
            
            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                return null;
                // return fromUsersNum + "娑擃亜鐔�閸欏绱濋崣鎴炴降娴滐拷" + messageNum + "閺夆剝绉烽幁锟�";
            }
            
            @Override
            public Intent getLaunchIntent(EMMessage message) {
                //鐠佸墽鐤嗛悙鐟板毊闁氨鐓￠弽蹇氱儲鏉烆兛绨ㄦ禒锟�
                Intent intent = new Intent(appContext, ChatActivity.class);
                //閺堝鏁哥拠婵囨娴兼ê鍘涚捄瀹犳祮閸掍即锟芥俺鐦芥い鐢告桨
                    ChatType chatType = message.getChatType();
                    if (chatType == ChatType.Chat) { // 閸楁洝浜版穱鈩冧紖
                        intent.putExtra("userId", message.getFrom());
                        intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
                    } else { // 缂囥倛浜版穱鈩冧紖
                        // message.getTo()娑撹櫣鍏㈤懕濂縟
                        intent.putExtra("groupId", message.getTo());
                        if(chatType == ChatType.GroupChat){
                            intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
                        }else{
                            intent.putExtra("chatType", ChatActivity.CHATTYPE_CHATROOM);
                        }
                        
                    }
                
                return intent;
            }
        };
    }
    
    
    
    @Override
    protected void onConnectionConflict(){
    }
    
    @Override
    protected void onCurrentAccountRemoved(){}
    

    @Override
    protected HXSDKModel createModel() {
        return new DemoHXSDKModel(appContext);
    }
    
    @Override
    public HXNotifier createNotifier(){
        return new HXNotifier(){
            public synchronized void onNewMsg(final EMMessage message) {
                if(EMChatManager.getInstance().isSlientMessage(message)){
                    return;
                }
                
                String chatUsename = null;
                List<String> notNotifyIds = null;
                // 閼惧嘲褰囩拋鍓х枂閻ㄥ嫪绗夐幓鎰仛閺傜増绉烽幁顖滄畱閻€劍鍩涢幋鏍拷鍛參缂佸埇ds
                if (message.getChatType() == ChatType.Chat) {
                    chatUsename = message.getFrom();
                    notNotifyIds = ((DemoHXSDKModel) hxModel).getDisabledGroups();
                } else {
                    chatUsename = message.getTo();
                    notNotifyIds = ((DemoHXSDKModel) hxModel).getDisabledIds();
                }

                if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
                    // 閸掋倖鏌嘺pp閺勵垰鎯侀崷銊ユ倵閸欙拷
                    if (!EasyUtils.isAppRunningForeground(appContext)) {
                        EMLog.d(TAG, "app is running in backgroud");
                        sendNotification(message, false);
                    } else {
                        sendNotification(message, true);

                    }
                    
                    viberateAndPlayTone(message);
                }
            }
        };
    }
    
    /**
     * get demo HX SDK Model
     */
    public DemoHXSDKModel getModel(){
        return (DemoHXSDKModel) hxModel;
    }
    
    /**
     * 閼惧嘲褰囬崘鍛摠娑擃厼銈介崣濯er list
     *
     * @return
     */
    public Map<String, User> getContactList() {
        if (getHXId() != null && contactList == null) {
            contactList = ((DemoHXSDKModel) getModel()).getContactList();
        }
        
        return contactList;
    }
    
	public Map<String, RobotUser> getRobotList() {
		if (getHXId() != null && robotList == null) {
			robotList = ((DemoHXSDKModel) getModel()).getRobotList();
		}
		return robotList;
	}
	
	
	public boolean isRobotMenuMessage(EMMessage message) {

		try {
			JSONObject jsonObj = message.getJSONObjectAttribute(Constant.MESSAGE_ATTR_ROBOT_MSGTYPE);
			if (jsonObj.has("choice")) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	public String getRobotMenuMessageDigest(EMMessage message) {
		String title = "";
		try {
			JSONObject jsonObj = message.getJSONObjectAttribute(Constant.MESSAGE_ATTR_ROBOT_MSGTYPE);
			if (jsonObj.has("choice")) {
				JSONObject jsonChoice = jsonObj.getJSONObject("choice");
				title = jsonChoice.getString("title");
			}
		} catch (Exception e) {
		}
		return title;
	}
	
	
	

    public void setRobotList(Map<String, RobotUser> robotList){
    	this.robotList = robotList;
    }
    
    /**
     * 鐠佸墽鐤嗘總钘夊几user list閸掓澘鍞寸�涙ü鑵�
     *
     * @param contactList
     */
    public void setContactList(Map<String, User> contactList) {
        this.contactList = contactList;
    }
    
    /**
     * 娣囨繂鐡ㄩ崡鏇氶嚋user 
     */
    public void saveContact(User user){
    	contactList.put(user.getUsername(), user);
    	((DemoHXSDKModel) getModel()).saveContact(user);
    }
    
    @Override
    public void logout(final boolean unbindDeviceToken,final EMCallBack callback){
        endCall();
        super.logout(unbindDeviceToken,new EMCallBack(){

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                setContactList(null);
                setRobotList(null);
                getModel().closeDB();
                if(callback != null){
                    callback.onSuccess();
                }
            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub
            	if(callback != null){
                    callback.onError(code, message);
                }
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub
                if(callback != null){
                    callback.onProgress(progress, status);
                }
            }
            
        });
    }   
    
    void endCall(){
        try {
            EMChatManager.getInstance().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * update User cach And db
     *
     * @param contactList
     */
    public void updateContactList(List<User> contactInfoList) {
         for (User u : contactInfoList) {
			contactList.put(u.getUsername(), u);
         }
         ArrayList<User> mList = new ArrayList<User>();
         mList.addAll(contactList.values());
        ((DemoHXSDKModel)getModel()).saveContactList(mList);
    }

    
}
