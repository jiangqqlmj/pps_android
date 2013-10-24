package tv.pps.bi.proto.model;

import java.io.Serializable;

/**
 * 投递数据记录 实体类
 * @author jiangqingqing
 *
 */
public class SendTime implements Serializable{
	private static final long serialVersionUID = 1L;
	private long sendtime;

	public SendTime() {
		super();
	}

	public SendTime(long sendtime) {
		super();
		this.sendtime = sendtime;
	}

	public long getSendtime() {
		return sendtime;
	}

	public void setSendtime(long sendtime) {
		this.sendtime = sendtime;
	}

	@Override
	public String toString() {
		return "SendTime [sendtime=" + sendtime + "]";
	}
    
}
