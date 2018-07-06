package com.deepheart.ellecity06.deepheart.widget.dialog;

import android.os.Parcel;
import android.os.Parcelable;

public interface ViewConvertListener extends Parcelable {
    long serialVersionUID = System.currentTimeMillis();

    void convertView(ViewHolder holder, BaseDialogFragment dialog);

  @Override
  int describeContents();

  @Override
  void writeToParcel(Parcel dest, int flags);
}
