package com.yukun.textapplication.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.rv_chatlist)
    RecyclerView mRvChatlist;
    @BindView(R.id.et_message)
    EditText mEtMessage;
    @BindView(R.id.bt_send)
    Button mBtSend;
    private AVIMClient mClientTom;
    private AVIMClient mClientJerry;
//    private String mConverId;
    //加入者的消息
    private String mConverId="58f04a6744d904006cb5ee46";
    List<String> mList=new ArrayList<>();
    private RecyclerItemAdapter mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        init();
        EventBus.getDefault().register(this);
        //两个方法,先运行第一个方法,获得roomId ,再去运行第二个方法,加入聊天室
//        createChatRoom();//创建聊天室
        // 加入到这个聊天室,运行前记得手动复制mConverId 的值
        joinRoom();

    }

    private void init() {
        mItemAdapter = new RecyclerItemAdapter(mList,getApplicationContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRvChatlist.setLayoutManager(linearLayoutManager);
        mRvChatlist.setAdapter(mItemAdapter);
    }


    public void SendClick(View view) {
        String trim = mEtMessage.getText().toString().trim();
        if(trim.length()>0){
            //创建者的消息
//            sendMessage(mConverId,trim);
            //加入者的消息
            sendMessages(mConverId,trim);
        }
    }

    public void createChatRoom() {

        mClientTom = AVIMClient.getInstance("Tom");
        mClientTom.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    //登录成功
                    //创建一个 名为 "PK" 的暂态对话
                    client.createConversation(Collections.emptyList(), "PK", null, true,
                            new AVIMConversationCreatedCallback() {

                                @Override
                                public void done(AVIMConversation conv, AVIMException e) {
                                    if (e == null) {
                                        //获得一个 Id conv.getConversationId().惟一room的凭证
                                        //记得打印出来,这个数是随机生成的,记下来,之后加入这个聊天室要用 就是roomId
                                        mConverId = conv.getConversationId();
                                        sendMessage(mConverId,"创建聊天室成功");
                                        Log.i("----conv", conv.getConversationId());
                                        TomQueryWithLimit();
                                    } else {
                                        Log.i("----Fail", e.toString());
                                    }
                                }
                            });
                }
            }
        });
    }

    public void joinRoom() {
        //这是jerry加入聊天室
        mClientJerry = AVIMClient.getInstance("Jerry");
        //建立连接
        mClientJerry.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                //获取聊天室
                AVIMConversation avimConversation = mClientJerry.getConversation(mConverId);
                //加入聊天室
                avimConversation.join(new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        if (e == null) {
                            //获取聊天室人数
                            Toast.makeText(ChatActivity.this, "joinsuccess", Toast.LENGTH_SHORT).show();

                            //发送一条默认消息，类型为MSG_NEWPEOPLE
                            sendMessages(mConverId, "jerry加入聊天室了");
                        } else {
                            Log.i("----roomjoinFail", e.toString());
                            Toast.makeText(ChatActivity.this, "roomjoinFail", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    /**
     * 进入聊天室后，发送一条消息
     *
     * @param
     */
    private void sendMessage(String sId, String msg) {
        //这是第一个人.创建聊天室的人
        AVIMConversation avimConversation = mClientTom.getConversation(sId);//由id,得到聊天室的对象,发送消息,接收消息
        AVIMTextMessage avimTextMessage = new AVIMTextMessage();
        Map<String, Object> map = new HashMap<>();
        avimTextMessage.setAttrs(map);
        avimTextMessage.setText(msg);
        avimConversation.sendMessage(avimTextMessage, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e == null) {
                    Log.i("发送成功", msg);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }


    //获到消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OnEventMessage event) {/* Do something */
        AVIMMessage avimMessage = event.avimMessage;
        String from = avimMessage.getFrom();

        Map<String, Object> attrs = ((AVIMTextMessage) avimMessage).getAttrs();
        String attsMsg = (String) attrs.get("msg");//获取到的附加消息
        String chatMessage = ((AVIMTextMessage) avimMessage).getText();//获取到的文本消息
        Log.i("------msg",chatMessage+"---"+attsMsg);
        Toast.makeText(ChatActivity.this, chatMessage + "==" + attsMsg, Toast.LENGTH_SHORT).show();
        mList.add(chatMessage);
        mItemAdapter.notifyDataSetChanged();
        mRvChatlist.smoothScrollToPosition(mList.size()-1);
    }

    private void sendMessages(String roomId, String msg) {
        //这是加入聊天室的人
        AVIMConversation avimConversation = mClientJerry.getConversation(roomId);//由id,得到聊天室的对象,发送消息,接收消息
        AVIMTextMessage avimTextMessage = new AVIMTextMessage();
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "attr message");//发送附加消息,头像啥的都可以
        avimTextMessage.setAttrs(map);
        avimTextMessage.setText(msg);
        avimConversation.sendMessage(avimTextMessage, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e == null) {
                    Log.i("发送成功", msg);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }


    //获取会话人数
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

//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mClientTom == null) {
            return;
        }
        AVIMConversation avimConversation = mClientTom.getConversation(mConverId);
        avimConversation.quit(new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e == null) {
                }
                mClientTom.close(new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient avimClient, AVIMException e) {
                        if (e == null) {
                        }
                    }
                });
            }
        });
    }
    //    public void sendMessageToJerryFromTom() {
//        // Tom 用自己的名字作为clientId，获取AVIMClient对象实例
//        AVIMClient tom = AVIMClient.getInstance(editTextName.getText().toString());
//        // 与服务器连接
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", "yuyuyu");
//        tom.open(new AVIMClientCallback() {
//            @Override
//            public void done(AVIMClient client, AVIMException e) {
//                if (e == null) {
//                    // 创建与Jerry之间的对话
//                    client.createConversation(Arrays.asList(editTextRec.getText().toString()), "Tom & Jerry", map,
//                            new AVIMConversationCreatedCallback() {
//                                @Override
//                                public void done(AVIMConversation conversation, AVIMException e) {
//                                    if (e == null) {
//                                        AVIMTextMessage msg = new AVIMTextMessage();
//                                        msg.setText(editText.getText().toString());
//                                        // 发送消息
//                                        conversation.sendMessage(msg, new AVIMConversationCallback() {
//
//                                            @Override
//                                            public void done(AVIMException e) {
//                                                if (e == null) {
//                                                    Log.d("bb", "发送成功！");
//                                                    Toast.makeText(ChatActivity.this, "success", Toast.LENGTH_SHORT).show();
//                                                } else {
//                                                    Toast.makeText(ChatActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        });
//                                    }
//
//                                }
//                            });
//                }
//            }
//        });

}
