package Services;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.bytedeco.javacpp.opencv_core.Mat;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Engine.Face;
import com.alibaba.fastjson.JSON;

import Bean.Teacher;
import DBUtil.DBHelper;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;

public class GetTeacherInfo {
	
	//���մ�������ͼƬ
	private String imageName;
	private File mPhoto;
	
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public File getmPhoto() {
		return mPhoto;
	}

	public void setmPhoto(File mPhoto) {
		this.mPhoto = mPhoto;
	}

	public void getTeacherInfo() throws Exception{
		
		
		//��������ݿ��������ݣ�д��		
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		
		
		String dir=ServletActionContext.getServletContext().getRealPath("images");
		File file=new File(dir,imageName);
		FileUtils.copyFile(mPhoto, file);//�ϴ��ļ�����
		
		Mat testImage=imread(dir+"/"+imageName,CV_LOAD_IMAGE_GRAYSCALE);//�������ͼƬ���д���
		int label=Face.predict(testImage);//��ȡԤ��ı�ǩ	
//		System.out.println("��Ӧ..."+label);
		PrintWriter writer=response.getWriter();
		ArrayList<Teacher>teachers=new ArrayList<>();
		
		teachers=DBHelper.getTeacher(label);
		System.out.println("���ؽ��:"+JSON.toJSONString(teachers));
		writer.write(JSON.toJSONString(teachers));
	}
}