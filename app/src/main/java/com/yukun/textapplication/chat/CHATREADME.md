 ### leancloud聊天室使用
 #### 最近由于需求需要建聊天室,后来使用了leancloud来做,这个以前免费的,后来企业级要收费了,具体的可以看官网
 #### 这里主要是建立聊天室,消息存储那些都是一次性的,所以这里主要讲几个方法,从建立聊天室,发送一条默认的消息,到别人加入聊天室,发送消息
 [github代码链接](https://github.com/yukunkun/TextDemo/blob/master/app/src/main/java/com/yukun/textapplication/chat/ChatActivity.java)
 #### 首先是建立聊天室:
 #### 接入leancloud,只需要看管方的流程就可以完成了(这里是接入成功以后的使用)
            
          public void createChatRoom() {
                tom = AVIMClient.getInstance("Tom");
                tom.open(new AVIMClientCallback() {
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
#### 以上代码建立了聊天室,并且获取到了getConversationId 这个id,这是一个聊天室惟一的凭证,
#### sendMessage(mConverId,"创建聊天室成功")和TomQueryWithLimit(),分别为发送第一条消息,和获取人数的方法
#### 代码如下:
        /**
         * 进入聊天室后，发送一条消息
         *
         * @param
         */
        private void sendMessage(String sId, String msg) {
            //这是第一个人.创建聊天室的人
            AVIMConversation avimConversation = tom.getConversation(sId);//由id,得到聊天室的对象,发送消息,接收消息
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
#### 获取人数:
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
#### 加入聊天室的方法如下:        
      public void joinRoom() {
              //这是jerry加入聊天室
              jerry = AVIMClient.getInstance("Jerry");
              //建立连接
              jerry.open(new AVIMClientCallback() {
                  @Override
                  public void done(AVIMClient avimClient, AVIMException e) {
                      //获取聊天室
                      AVIMConversation avimConversation = jerry.getConversation(mConverId);
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
#### 加入了聊天室,并且向聊天室发送了一条消息
#### 发送消息都是一样的,这里需要获取convenientId ,由这个Id获取到聊天室的对象   
#### sendMessages(mConverId, "jerry加入聊天室了")
        private void sendMessages(String roomId, String msg) {
            //这是加入聊天室的人
            AVIMConversation avimConversation = jerry.getConversation(roomId);//由id,得到聊天室的对象,发送消息,接收消息
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
### 其中聊天室主要由 mConverId = conv.getConversationId();这里获取到的id将所有人联系起来,这样才能加入到一起
### 消息获取
#### 消息获取和官网提供的方法一样在APPLICATION里面,全局的获取
#### 可以查看我的MyApp类 (MyApp extends Application)
    public static class CustomMessageHandler extends AVIMMessageHandler {
            //接收到消息后的处理逻辑
            @Override
            public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
                if(message instanceof AVIMTextMessage){
                    String from = ((AVIMTextMessage) message).getFrom();
                    String name = (String)conversation.getAttribute("name");
    //                Log.d("aa & bb",((AVIMTextMessage)message).getText());
                    EventBus.getDefault().post(new OnEventMessage(((AVIMTextMessage)message).getText(),message));
                }
            }
    
            public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){
    
            }
        }
#### 消息的获取用了EventBus来传对象,如下:
        /获到消息
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
#### 以上就能获得消息了,具体的需要实际操作才能理解,只是做了一些简单的操作,可以建立聊天室,并且加入,实现双方的聊天功能
#### 这里是销毁聊天室:    
        @Override
        protected void onDestroy() {
            super.onDestroy();
            EventBus.getDefault().unregister(this);
            if (tom == null) {
                return;
            }
            AVIMConversation avimConversation = tom.getConversation(mConverId);
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