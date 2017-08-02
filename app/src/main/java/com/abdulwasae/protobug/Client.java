package com.abdulwasae.protobug;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelProvider;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.okhttp.OkHttpChannelBuilder;
import io.grpc.stub.AbstractStub;
import io.grpc.stub.MetadataUtils;
import protobug.v0.GetTimeReq;
import protobug.v0.GetTimeResp;
import protobug.v0.TheServiceGrpc;

/**
 * Created by Abdul Wasae on 8/5/17.
 */

public class Client {
    private static final String TAG = "DBG_" + "Client2";

    private static final String SERVER_V1_IP = "127.0.0.1";
    private static final int SERVER_V1_PORT = 21100;
    private static final int DEFAULT_TIMEOUT_SEC = 10;
    private final Object mBlocker = new Object();
    private final int mTimeout;
    private ManagedChannel mChannel;

    public Client() {
        this(DEFAULT_TIMEOUT_SEC);
    }

    private Client(int timeout) {
        mTimeout = timeout;
    }

    private <T extends AbstractStub<T>> T attachHeaders(T stub, String keyName, String metadata) {
        Metadata.Key<String> metadataKey = Metadata.Key.of(keyName, Metadata.ASCII_STRING_MARSHALLER);
        Metadata headers = new Metadata();
        headers.put(metadataKey, metadata);
        return MetadataUtils.attachHeaders(stub, headers);
    }

    public Channel getChannel() {
        if (mChannel == null) {
            mChannel = OkHttpChannelBuilder.forAddress(SERVER_V1_IP, SERVER_V1_PORT)
                    .usePlaintext(true).enableKeepAlive(true).build();
        }
        return mChannel;
    }

    private void disconnectChannel() {
        synchronized (mBlocker) {
            if (mChannel == null) return;

            try {
                mChannel.shutdownNow();
            } catch (Exception ex) {
                Log.e(TAG, "Couldn't shutdown channel", ex);
            }

            mChannel = null;
        }
    }

    private TheServiceGrpc.TheServiceBlockingStub getBlockingStub() {
        return TheServiceGrpc.newBlockingStub(this.getChannel()).withDeadlineAfter(mTimeout, TimeUnit.SECONDS);
    }

    public GetTimeResp getTime(GetTimeReq request) throws ServerException {
        try {
            return this.getBlockingStub().getTime(request);
        } catch (ManagedChannelProvider.ProviderNotFoundException ex) {
            this.disconnectChannel();
            Log.d(TAG, "getTime failed.", ex);
            throw new ServerException(ServerException.Type.INTERNET_DISCONNECTED);
        } catch (StatusRuntimeException ex) {
            Log.d(TAG, "getTime failed: " + ex.getStatus().getDescription());
            throw serverExceptionFromStatusCode(ex.getStatus().getCode());
        }
    }

    private static ServerException serverExceptionFromStatusCode(Status.Code code) {
        switch (code) {
            case UNAVAILABLE:
                return new ServerException(ServerException.Type.SERVICE_UNAVAILABLE);
            case INVALID_ARGUMENT:
                return new ServerException(ServerException.Type.INVALID_ARGUMENT);
            case DEADLINE_EXCEEDED:
                return new ServerException(ServerException.Type.DEADLINE_EXCEEDED);
            case PERMISSION_DENIED:
                return new ServerException(ServerException.Type.PERMISSION_DENIED);
            case UNAUTHENTICATED:
                return new ServerException(ServerException.Type.UNAUTHENTICATED);
            case NOT_FOUND:
                return new ServerException(ServerException.Type.NOT_FOUND);
            case OUT_OF_RANGE:
                return new ServerException(ServerException.Type.OUT_OF_RANGE);
            default:
                return new ServerException(ServerException.Type.UNKNOWN);
        }
    }
}
