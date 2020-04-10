package com.galaxy.instadownloader.domain.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author gdong
 */
public  class InstagramMedia implements Parcelable, Serializable {

  private String url;

  private String description;

  private String embedHtml;
  private Object data;
  private String title;

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getEmbedHtml() {
    return embedHtml;
  }

  public void setEmbedHtml(String embedHtml) {
    this.embedHtml = embedHtml;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.url);
    dest.writeString(this.description);
    dest.writeString(this.embedHtml);
    dest.writeParcelable((Parcelable) this.data, flags);
    dest.writeString(this.title);
  }

  public InstagramMedia() {
  }

  protected InstagramMedia(Parcel in) {
    this.url = in.readString();
    this.description = in.readString();
    this.embedHtml = in.readString();
    this.data = in.readParcelable(Object.class.getClassLoader());
    this.title = in.readString();
  }

  public static final Parcelable.Creator<InstagramMedia> CREATOR = new Parcelable.Creator<InstagramMedia>() {
    @Override
    public InstagramMedia createFromParcel(Parcel source) {
      return new InstagramMedia(source);
    }

    @Override
    public InstagramMedia[] newArray(int size) {
      return new InstagramMedia[size];
    }
  };
}
