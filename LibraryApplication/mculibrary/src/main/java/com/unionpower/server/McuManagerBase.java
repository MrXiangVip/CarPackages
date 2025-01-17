package com.unionpower.server;

import com.unionpower.mculibrary.listener.IMcuBaseListener;

public class McuManagerBase  {

        private static McuServer   mcuServer;
        protected McuManagerBase(){
                mcuServer = McuServer.getInstance();
        }

        protected void  sendUnPackedBytesToMcu( int type, int subType, byte[] data){
                mcuServer.sendUnPackedBytesToMcu( type, subType, data);
        }
        protected void sendPackedBytesToMcu( byte[] data){
                mcuServer.sendPackedBytesToMcu( data);
        }

//        通过 type 和subType 获取缓存
        public  byte[] getCachedMcuData( int type, int subType ){
              return mcuServer.getCachedMcuData( type, subType);
        }

        protected   void addCallbackListener(String key, IMcuBaseListener  listener){
                mcuServer.addCallbackListener( key, listener);
        }

        protected  void removeCallbackListener( String key ){
                mcuServer.removeCallbackListener(key);
        }

}
