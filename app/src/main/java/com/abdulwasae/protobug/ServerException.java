package com.abdulwasae.protobug;

/**
 * Created by Abdul Wasae on 03/07/2017, 12:01 PM.
 */

public class ServerException extends Exception {

    public enum Type {
        UNKNOWN(-1),
        SERVICE_UNAVAILABLE(1),
        INTERNET_DISCONNECTED(2),
        INVALID_ARGUMENT(3),
        DEADLINE_EXCEEDED(4),
        PERMISSION_DENIED(5),
        UNAUTHENTICATED(6),
        NOT_FOUND(7),
        OUT_OF_RANGE(8);

        private final int mCode;

        Type(int code) {
            this.mCode = code;
        }

        private int getCode() {
            return mCode;
        }
    }

    private Type mType = Type.UNKNOWN;

    public ServerException(Type mType) {
        if (mType != null) {
            this.mType = mType;
        }
    }

    public Type getType() {
        return mType;
    }

    @Override
    public String getMessage() {
        return mType.name();
    }

    public int getCode() {
        return mType.getCode();
    }
}
