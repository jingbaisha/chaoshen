package org.moblietrain.cbk_text.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/22.
 */
/*2.实现Parcelable就是为了进行序列化，那么，为什么要序列化？
        1）永久性保存对象，保存对象的字节序列到本地文件中；
        2）通过序列化对象在网络中传递对象；
        3）通过序列化在进程间传递对象。*/

public class Cbkdate implements Parcelable {

    private String title;//资讯标题   ---
    private int infoclass;//分类
    private String img;//图片        ----
    private String description;//描述  ----
    private String keywords;//关键字
    private String message;//资讯内容  -----
    private int count ;//访问次数
    private int fcount;//收藏数
    private int rcount;//评论读数
    private String time;              //-----
    private long id;

    public Cbkdate() {

    }

    protected Cbkdate(Parcel in){
        img = in.readString();
        description = in.readString();
        rcount = in.readInt();
        time = in.readString();
        id = in.readLong();
    }

    public static final Creator<Cbkdate> CREATOR = new Creator<Cbkdate>() {

        @Override
        public Cbkdate createFromParcel(Parcel in) {
            return new Cbkdate(in);
        }

        @Override
        public Cbkdate[] newArray(int size) {
            return new Cbkdate[size];
        }
    };


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getInfoclass() {
        return infoclass;
    }

    public void setInfoclass(int infoclass) {
        this.infoclass = infoclass;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(description);
        dest.writeInt(rcount);
        dest.writeString(time);
        dest.writeLong(id);
    }

}
