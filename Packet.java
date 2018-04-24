
public class Packet {
	private String ttl;
	private String ip;
	private String id;
	private double start;
	private double end;
	
	public Packet(String ttl, String id, double start) {
		super();
		this.ttl = ttl;
		this.id = id;
		this.start = start;
	}

	public String getTtl() {
		return ttl;
	}

	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double getEnd() {
		return end;
	}

	public void setEnd(double end) {
		this.end = end;
	}
		
}
