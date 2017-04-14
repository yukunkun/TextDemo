package com.yukun.textapplication.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationMemberCountCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.yukun.textapplication.R;
import com.yukun.textapplication.bean.OnEventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private EditText editTextName;
    private EditText editTextRec;
    private AVIMClient tom;
    private AVIMClient jerry;
    private  String roomId="58ef2f23ac502e006ae703b4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        EventBus.getDefault().register(this);
        init();

    }

    private void init() {
        textView = (TextView) findViewById(R.id.text);
        editText = (EditText) findViewById(R.id.edit);
        editTextName = (EditText) findViewById(R.id.edit_name);
        editTextRec = (EditText) findViewById(R.id.edit_name_rece);

    }
    public void SendClick(View view) {
        if(editText.getText().toString().length()>0){
//            sendMessageToJerryFromTom();
//            createChatRoom();
            joinRoom();

        }else {
            Toast.makeText(ChatActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    public void createChatRoom(){

        tom = AVIMClient.getInstance("Tom");
        tom.open(new AVIMClientCallback(){
            @Override
            public void done(AVIMClient client,AVIMException e){
                if(e==null){
                    //登录成功
                    //创建一个 名为 "PK" 的暂态对话
                    client.createConversation(Collections.emptyList(),"PK",null,true,
                            new AVIMConversationCreatedCallback(){
                                @Override
                                public void done(AVIMConversation conv,AVIMException e){
                                    if(e==null){
                                        //获得一个 Id conv.getConversationId().惟一room的凭证
                                        sendEnterMessage(conv.getConversationId());
                                        Log.i("----conv",conv.getConversationId());
                                        Toast.makeText(ChatActivity.this, "", Toast.LENGTH_SHORT).show();
                                        TomQueryWithLimit();
                                        sendMessage(conv.getConversationId(),"这是第一条消息");
                                    }
                                    else {
                                        Log.i("----roomFail",e.toString());
                                    }
                                }
                            });
                }
            }
        });
    }

    public void joinRoom(){

        jerry = AVIMClient.getInstance("Jerry");
        jerry.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                AVIMConversation avimConversation = jerry.getConversation(roomId);
                avimConversation.join(new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        if (e == null) {
                            //获取聊天室人数
                            Toast.makeText(ChatActivity.this, "joinsuccess", Toast.LENGTH_SHORT).show();

                            //发送一条默认消息，类型为MSG_NEWPEOPLE
                            sendMessages(roomId,"jerry Message");
                        } else {
                            Log.i("----roomjoinFail",e.toString());
                            Toast.makeText(ChatActivity.this, "roomjoinFail", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    /**
     * 进入聊天室后，发送一条消息
     * @param
     */
    private void sendEnterMessage(String sId) {
        AVIMConversation avimConversation=tom.getConversation(sId);//由id,得到聊天室的对象,发送消息,接收消息
        AVIMTextMessage avimTextMessage = new AVIMTextMessage();
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "first null message");
        avimTextMessage.setAttrs(map);
        avimConversation.sendMessage(avimTextMessage, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e == null) {
                    Log.i("to ChatRoom-发送成功", "null Msg");
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 进入聊天室后，发送一条消息
     * @param
     */
    private void sendMessage(String sId,String msg) {
        AVIMConversation avimConversation=tom.getConversation(sId);//由id,得到聊天室的对象,发送消息,接收消息
        AVIMTextMessage avimTextMessage = new AVIMTextMessage();
        Map<String, Object> map = new HashMap<>();
        avimTextMessage.setAttrs(map);
        avimTextMessage.setText(msg);
        avimConversation.sendMessage(avimTextMessage, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e == null) {
                    Log.i("to Cha发送成功", "messageType:MSGTYPE_NEW_PEOPLE");
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendMessages(String sId,String msg) {

        AVIMConversation avimConversation=jerry.getConversation(sId);//由id,得到聊天室的对象,发送消息,接收消息
        AVIMTextMessage avimTextMessage = new AVIMTextMessage();
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "first attr message");
        avimTextMessage.setAttrs(map);
        avimTextMessage.setText(msg);
        avimConversation.sendMessage(avimTextMessage, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e == null) {
                    Log.i("to ChatRoom", "Jerry send success");
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void TomQueryWithLimit() {
        AVIMClient tom = AVIMClient.getInstance("Tom");
        tom.open(new AVIMClientCallback() {

            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    //登录成功
                    AVIMConversationQuery query = tom.getQuery();
                    query.setLimit(1);
                    //获取第一个对话
                    query.findInBackground(new AVIMConversationQueryCallback() {
                        @Override
                        public void done(List<AVIMConversation> convs, AVIMException e) {
                            if (e == null) {
                                if (convs != null && !convs.isEmpty()) {
                                    AVIMConversation conv = convs.get(0);
                                    //获取第一个对话的
                                    conv.getMemberCount(new AVIMConversationMemberCountCallback() {

                                        @Override
                                        public void done(Integer count, AVIMException e) {
                                            if (e == null) {
                                                Log.i("-----count", "conversation got " + count + " members");
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });
    }



    public void sendMessageToJerryFromTom() {
        // Tom 用自己的名字作为clientId，获取AVIMClient对象实例
        AVIMClient tom = AVIMClient.getInstance(editTextName.getText().toString());
        // 与服务器连接
        Map<String,Object> map=new HashMap<>();
        map.put("name","yuyuyu");
        tom.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    // 创建与Jerry之间的对话
                    client.createConversation(Arrays.asList(editTextRec.getText().toString()), "Tom & Jerry", map,
                            new AVIMConversationCreatedCallback() {
                                @Override
                                public void done(AVIMConversation conversation, AVIMException e) {
                                    if (e == null) {
                                        AVIMTextMessage msg = new AVIMTextMessage();
                                        msg.setText(editText.getText().toString());
                                        // 发送消息
                                        conversation.sendMessage(msg, new AVIMConversationCallback() {

                                            @Override
                                            public void done(AVIMException e) {
                                                if (e == null) {
                                                    Log.d("bb", "发送成功！");
                                                    Toast.makeText(ChatActivity.this, "success", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(ChatActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }

                                }
                            });
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OnEventMessage event) {/* Do something */
        String message = event.Message;
        AVIMMessage avimMessage = event.avimMessage;
        String from = avimMessage.getFrom();
        if(!from.equals(editTextName.getText().toString())){
        }
        textView.setText(message);
        Map<String, Object> attrs = ((AVIMTextMessage) avimMessage).getAttrs();
        Toast.makeText(ChatActivity.this, attrs.size()+"=="+attrs.get("msg"), Toast.LENGTH_SHORT).show();
        Log.i("------aab", attrs.size()+"=="+attrs.get("msg"));
        String  attsMsg= (String)attrs.get("msg");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(tom==null){
            return;
        }
        AVIMConversation avimConversation = tom.getConversation(roomId);
        avimConversation.quit(new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e == null) {
                }
                tom.close(new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient avimClient, AVIMException e) {
                        if (e == null) {
                        }
                    }
                });
            }
        });


    }


}
