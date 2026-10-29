package additionals;

public class DataCount {
	private String docname;
	private Integer count;
	public DataCount(String docname, Integer count) {
		super();
		this.docname = docname;
		this.count = count;
	}
	public String getDocname() {
		return docname;
	}
	public void setDocname(String docname) {
		this.docname = docname;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "ReferringDoctorsCount [docname=" + docname + ", count=" + count + "]";
	}
	
}
