package parse.xml;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//定义命名空间，如果不写的话，xml会以类名开头: <TextMessage>...</TextMessage>，写了就会以xml开头: <xml>...</xml>
@XmlRootElement(name = "xml")
public class TextMessage {
    private String fromUserName;
    private String toUserName;
    private String msgType;
    private int funcFlag = 0;
    private String content;
    private String event;
    private long createTime;

    public TextMessage() {
    }

    public TextMessage(String fromUserName, String toUserName, String msgType, String content, long createTime) {
        this.fromUserName = fromUserName;
        this.toUserName = toUserName;
        this.msgType = msgType;
        this.content = content;
        this.createTime = createTime;
    }

    public String getToUserName() {
        return toUserName;
    }

    //定义xml子项的名称，不写这个annotation的话，转换后的xml是: <toUserName>xxx</toUserName>，首字母变小写了，会导致消息传输错误
    @XmlElement(name = "ToUserName")
    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

	public String getFromUserName() {
		return fromUserName;
	}

	@XmlElement(name = "FromUserName")
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getMsgType() {
		return msgType;
	}

	@XmlElement(name = "MsgType")
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public int getFuncFlag() {
		return funcFlag;
	}

	@XmlElement(name = "FuncFlag")
	public void setFuncFlag(int funcFlag) {
		this.funcFlag = funcFlag;
	}

	public String getContent() {
		return content;
	}

	@XmlElement(name = "Content")
	public void setContent(String content) {
		this.content = content;
	}

	public String getEvent() {
		return event;
	}

	@XmlElement(name = "Event")
	public void setEvent(String event) {
		this.event = event;
	}

	public long getCreateTime() {
		return createTime;
	}

	@XmlElement(name = "CreateTime")
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

    
    
    
    //other setter and getter

}