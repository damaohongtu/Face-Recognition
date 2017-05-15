package mao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_face.createFisherFaceRecognizer;
import static org.bytedeco.javacpp.opencv_face.createEigenFaceRecognizer;
import static org.bytedeco.javacpp.opencv_face.createLBPHFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
public class FaceTrainer {
	
    public static void main(String[] args) throws Exception {
    	
    	Mat labels=new Mat(400, 1, CV_32SC1);
    	MatVector images=new MatVector(400);
    	
    	String filename="F:\\WORKPLACE\\job\\at.txt";
    	StringBuffer sb=new StringBuffer();
		FileReader reader=new FileReader(filename);
		BufferedReader br=new BufferedReader(reader);
		String str=null;
		String []liness;
		String path;
		String label;
		int counter=0;
		IntBuffer labelsBuf = labels.createBuffer();
		while((str=br.readLine())!=null){
			liness=str.split(";");
			path=liness[0];
			label=liness[1];
//			System.out.println(path);
//			System.out.println(label);
			if(!path.isEmpty()&&!label.isEmpty()){
				images.put(counter,imread(path,CV_LOAD_IMAGE_GRAYSCALE));//��ȡͼƬ
				labelsBuf.put(counter,Integer.parseInt(label));//��ӱ�ǩ
			}
			counter++;
		}
		br.close();
		reader.close();
    	FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
    	//�Ƚ����淽����õĽ��
        // FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
        // FaceRecognizer faceRecognizer = createLBPHFaceRecognizer();
    	//�ж��Ƿ�ѵ�����������û������ļ�����ѵ�����еĻ�ֱ��predict
    	System.out.println("start to train...");
    	faceRecognizer.train(images, labels);//ѵ��
    	faceRecognizer.save("MyFaceRecognizer.xml");//�Ѿ�ѵ���õ�ģ�ͣ���ô��ȡ����ļ���ֱ��ʹ�ã�	
    	String testfileneme="F:/WORKPLACE/job/att_faces/s13/3.pgm";
    	Mat testImage = imread(testfileneme, CV_LOAD_IMAGE_GRAYSCALE); //��ȡ��Ԥ���ͼƬ	
    	int predictedLabel=faceRecognizer.predict_label(testImage);//ʶ��ͼƬ
    	System.out.println("people name:"+predictedLabel+"");
    }
}
