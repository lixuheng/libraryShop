package cn.bewweb.beans;


public class Mail {

	private String recipient;
	private String subject;
	private String code;
	private String content;

	private  String from;
	private  String fromPwd;
	private  String protocol;
	private  String host;
	private  String port;
	private  String auth;

	public Mail() {

	}

	public String getFrom() {
		return from;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFromPwd() {
		return fromPwd;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getAuth() {
		return auth;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setFromPwd(String fromPwd) {
		this.fromPwd = fromPwd;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "Mail [from=" + from + ", recipient=" + recipient + ", subject=" + subject + ", code=" + code
				+ ", content=" + content + ", fromPwd=" + fromPwd + ", protocol=" + protocol + ", host=" + host
				+ ", port=" + port + ", auth=" + auth + "]";
	}

}
