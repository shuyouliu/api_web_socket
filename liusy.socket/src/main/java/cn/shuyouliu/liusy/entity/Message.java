package cn.shuyouliu.liusy.entity;

public class Message {
	private int alonght;// 总长度
	private int imagenamelongth;// 图片名字长度
	private long imagelongth;// 图片长度
	private String imagename;// 图片名字
	private byte[] image;// 图片

	public int getAlonght() {
		return alonght;
	}

	public void setAlonght(int alonght) {
		this.alonght = alonght;
	}

	public int getImagenamelongth() {
		return imagenamelongth;
	}

	public void setImagenamelongth(int imagenamelongth) {
		this.imagenamelongth = imagenamelongth;
	}

	public long getImagelongth() {
		return imagelongth;
	}

	public void setImagelongth(long imagelongth) {
		this.imagelongth = imagelongth;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
