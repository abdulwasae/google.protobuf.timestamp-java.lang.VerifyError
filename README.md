# google.protobuf.timestamp-java.lang.VerifyError
MWE of a bug where a generated code from a proto file, that utilizes 'google/protobuf/timestamp.proto', gives the 'java.lang.VerifyError' runtime error


Error stacktrace:

08-02 14:55:29.295 13267-13267/com.abdulwasae.protobug E/AndroidRuntime: FATAL EXCEPTION: main
                                                                         Process: com.abdulwasae.protobug, PID: 13267
                                                                         java.lang.VerifyError: Verifier rejected class protobug.v0.GetTimeResp: void protobug.v0.GetTimeResp.mergeTheTime(com.google.protobuf.Timestamp) failed to verify: void protobug.v0.GetTimeResp.mergeTheTime(com.google.protobuf.Timestamp): [0x26] register v5 has type Precise Reference: com.google.protobuf.Timestamp but expected Reference: com.google.protobuf.GeneratedMessageLiteVerifier rejected class protobug.v0.GetTimeResp: java.lang.Object protobug.v0.GetTimeResp.dynamicMethod(com.google.protobuf.GeneratedMessageLite$MethodToInvoke, java.lang.Object, java.lang.Object) failed to verify: java.lang.Object protobug.v0.GetTimeResp.dynamicMethod(com.google.protobuf.GeneratedMessageLite$MethodToInvoke, java.lang.Object, java.lang.Object): [0x8C] register v8 has type Precise Reference: com.google.protobuf.Timestamp but expected Reference: com.google.protobuf.GeneratedMessageLite (declaration of 'protobug.v0.GetTimeResp' appears in /data/app/com.abdulwasae.protobug-1/split_lib_slice_6_apk.apk)
                                                                             at protobug.v0.GetTimeResp.getDefaultInstance(GetTimeResp.java:0)
                                                                             at protobug.v0.TheServiceGrpc.<clinit>(TheServiceGrpc.java:38)
                                                                             at protobug.v0.TheServiceGrpc.newBlockingStub(TheServiceGrpc.java:0)
                                                                             at com.abdulwasae.protobug.Client.getBlockingStub(Client.java:72)
                                                                             at com.abdulwasae.protobug.Client.getTime(Client.java:77)
                                                                             at com.abdulwasae.protobug.MainActivity.onClick(MainActivity.java:26)
                                                                             at android.view.View.performClick(View.java:5637)
                                                                             at android.view.View$PerformClick.run(View.java:22429)
                                                                             at android.os.Handler.handleCallback(Handler.java:751)
                                                                             at android.os.Handler.dispatchMessage(Handler.java:95)
                                                                             at android.os.Looper.loop(Looper.java:154)
                                                                             at android.app.ActivityThread.main(ActivityThread.java:6121)
                                                                             at java.lang.reflect.Method.invoke(Native Method)
                                                                             at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:889)
                                                                             at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:779)
