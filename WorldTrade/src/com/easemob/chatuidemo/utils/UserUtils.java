package com.easemob.chatuidemo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.example.worldtrade.R;

import com.easemob.chatuidemo.domain.User;

public class UserUtils {
    /**
     * 閺嶈宓乽sername閼惧嘲褰囬惄绋跨安user閿涘瞼鏁辨禍宸噀mo濞屸剝婀侀惇鐔风杽閻ㄥ嫮鏁ら幋閿嬫殶閹诡噯绱濇潻娆撳櫡缂佹瑧娈戝Ο鈩冨珯閻ㄥ嫭鏆熼幑顕嗙幢
     * @param username
     * @return
     */
    public static User getUserInfo(String username){
        User user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getContactList().get(username);
        if(user == null){
            user = new User(username);
        }
            
        if(user != null){
            //demo濞屸剝婀佹潻娆庣昂閺佺増宓侀敍灞煎閺冭泛锝為崗锟�
        	if(TextUtils.isEmpty(user.getNick()))
        		user.setNick(username);
        }
        return user;
    }
    
    /**
     * 鐠佸墽鐤嗛悽銊﹀煕婢舵潙鍎�
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
    	User user = getUserInfo(username);
        if(user != null && user.getAvatar() != null){
       //     Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
        }else{
        //    Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
        }
    }
    
    /**
     * 鐠佸墽鐤嗚ぐ鎾冲閻€劍鍩涙径鏉戝剼
     */
	public static void setCurrentUserAvatar(Context context, ImageView imageView) {}
    
    /**
     * 鐠佸墽鐤嗛悽銊﹀煕閺勭數袨
     */
    public static void setUserNick(String username,TextView textView){
    	User user = getUserInfo(username);
    	if(user != null){
    		textView.setText(user.getNick());
    	}else{
    		textView.setText(username);
    	}
    }
    
    /**
     * 鐠佸墽鐤嗚ぐ鎾冲閻€劍鍩涢弰鐢敌�
     */
    public static void setCurrentUserNick(TextView textView){}
    
    /**
     * 娣囨繂鐡ㄩ幋鏍ㄦ纯閺傜増鐓囨稉顏嗘暏閹达拷
     * @param user
     */
	public static void saveUserInfo(User newUser) {
		if (newUser == null || newUser.getUsername() == null) {
			return;
		}
		((DemoHXSDKHelper) HXSDKHelper.getInstance()).saveContact(newUser);
	}
    
}
