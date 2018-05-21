package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

final class FragmentState implements Parcelable {
    final String mClassName;
    final int mIndex;
    final boolean mFromLayout;
    final int mFragmentId;
    final int mContainerId;
    final String mTag;
    final boolean mRetainInstance;
    final boolean mDetached;
    final Bundle mArguments;
    final boolean mHidden;

    Bundle mSavedFragmentState;

    Fragment mInstance;

    public FragmentState(Fragment frag) {
        mClassName = frag.getClass().getName();
        mIndex = frag.mIndex;
        mFromLayout = frag.mFromLayout;
        mFragmentId = frag.mFragmentId;
        mContainerId = frag.mContainerId;
        mTag = frag.mTag;
        mRetainInstance = frag.mRetainInstance;
        mDetached = frag.mDetached;
        mArguments = frag.mArguments;
        mHidden = frag.mHidden;
    }

    public FragmentState(Parcel in) {
        mClassName = in.readString();
        mIndex = in.readInt();
        mFromLayout = in.readInt() != 0;
        mFragmentId = in.readInt();
        mContainerId = in.readInt();
        mTag = in.readString();
        mRetainInstance = in.readInt() != 0;
        mDetached = in.readInt() != 0;
        mArguments = in.readBundle();
        mHidden = in.readInt() != 0;
        mSavedFragmentState = in.readBundle();
    }

    public Fragment instantiate(FragmentHostCallback host, FragmentContainer container,
                                Fragment parent, FragmentManagerNonConfig childNonConfig) {
        if (mInstance == null) {
            final Context context = host.getContext();
            if (mArguments != null) {
                mArguments.setClassLoader(context.getClassLoader());
            }

            if (container != null) {
                mInstance = container.instantiate(context, mClassName, mArguments);
            } else {
                mInstance = Fragment.instantiate(context, mClassName, mArguments);
            }

            if (mSavedFragmentState != null) {
                mSavedFragmentState.setClassLoader(context.getClassLoader());
                mInstance.mSavedFragmentState = mSavedFragmentState;
            }
            mInstance.setIndex(mIndex, parent);
            mInstance.mFromLayout = mFromLayout;
            mInstance.mRestored = true;
            mInstance.mFragmentId = mFragmentId;
            mInstance.mContainerId = mContainerId;
            mInstance.mTag = mTag;
            mInstance.mRetainInstance = mRetainInstance;
            mInstance.mDetached = mDetached;
            mInstance.mHidden = mHidden;
            mInstance.mFragmentManager = host.mFragmentManager;

            if (FragmentManagerImpl.DEBUG) Log.v(FragmentManagerImpl.TAG,
                    "Instantiated fragment " + mInstance);
        }
        mInstance.mChildNonConfig = childNonConfig;
        return mInstance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mClassName);
        dest.writeInt(mIndex);
        dest.writeInt(mFromLayout ? 1 : 0);
        dest.writeInt(mFragmentId);
        dest.writeInt(mContainerId);
        dest.writeString(mTag);
        dest.writeInt(mRetainInstance ? 1 : 0);
        dest.writeInt(mDetached ? 1 : 0);
        dest.writeBundle(mArguments);
        dest.writeInt(mHidden? 1 : 0);
        dest.writeBundle(mSavedFragmentState);
    }

    public static final Creator<FragmentState> CREATOR
            = new Creator<FragmentState>() {
        @Override
        public FragmentState createFromParcel(Parcel in) {
            return new FragmentState(in);
        }

        @Override
        public FragmentState[] newArray(int size) {
            return new FragmentState[size];
        }
    };
}

