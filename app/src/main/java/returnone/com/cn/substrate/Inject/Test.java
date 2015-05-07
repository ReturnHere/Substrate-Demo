package returnone.com.cn.substrate.Inject;

import android.app.PendingIntent;
import android.util.Log;

import com.saurik.substrate.MS;

import java.lang.reflect.Method;

/**
 * Created by gaokun on 15/5/7.
 */
public class Test {
    static void  initialize(){
        MS.hookClassLoad("android.telephony.SmsManager" , new MS.ClassLoadHook() {
            @Override
            public void classLoaded(Class<?> aClass) {
                Method sendTextMessage;
                try {
                    sendTextMessage = aClass.getMethod("sendTextMessage",
                            new Class[] {
                                    String.class,
                                    String.class,
                                    String.class,
                                    PendingIntent.class,
                                    PendingIntent.class
                            });
                } catch (NoSuchMethodException e) {
                    sendTextMessage = null;
                }

                if (sendTextMessage != null) {
                    final MS.MethodPointer old = new MS.MethodPointer();
                    MS.hookMethod(aClass,sendTextMessage,new MS.MethodHook(){
                        @Override
                        public Object invoked(Object o, Object... objects) throws Throwable {
                            Log.e("returnone",(String)objects[2]);
                            return old.invoke(o, objects);
                        }
                    },old);
                }
            }
        });
    }
}
