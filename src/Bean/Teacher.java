package Bean;
/**
 * ��ʦ�Ļ�����Ϣ
 * @author mao
 *
 */
public class Teacher {

	private String teacherId;
	private String teacherName;
	private int teacherAge;
	private String teacherGender;
	private String teacherInfo;
	private int teacherLabel;
	public Teacher(String teacherId, String teacherName, int teacherAge, String teacherGender, String teacherInfo,
			int teacherLabel) {
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.teacherAge = teacherAge;
		this.teacherGender = teacherGender;
		this.teacherInfo = teacherInfo;
		this.teacherLabel = teacherLabel;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public int getTeacherAge() {
		return teacherAge;
	}
	public void setTeacherAge(int teacherAge) {
		this.teacherAge = teacherAge;
	}
	public String getTeacherGender() {
		return teacherGender;
	}
	public void setTeacherGender(String teacherGender) {
		this.teacherGender = teacherGender;
	}
	public String getTeacherInfo() {
		return teacherInfo;
	}
	public void setTeacherInfo(String teacherInfo) {
		this.teacherInfo = teacherInfo;
	}
	public int getTeacherLabel() {
		return teacherLabel;
	}
	public void setTeacherLabel(int teacherLabel) {
		this.teacherLabel = teacherLabel;
	}
	
}