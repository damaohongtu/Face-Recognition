package Engine;

import static org.bytedeco.javacpp.opencv_face.createFisherFaceRecognizer;

import org.apache.struts2.ServletActionContext;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_core.Mat;
public class Face {

	public static int predict(Mat testImage) throws Exception {
    	
    	FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
        // FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
        // FaceRecognizer faceRecognizer = createLBPHFaceRecognizer();
    	String dir=ServletActionContext.getServletContext().getRealPath("images");
    	faceRecognizer.load(dir+"/MyFaceRecognizer.xml");//�����Ѿ�ѵ���õ�ģ��
    	int predictedLabel=faceRecognizer.predict_label(testImage);//ʶ��ͼƬ
    	return predictedLabel;
	}
	
}
