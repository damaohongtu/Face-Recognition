package mao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.IntBuffer;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_face.createFisherFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;

import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
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
				images.put(counter,imread(path,CV_LOAD_IMAGE_GRAYSCALE));//读取图片
				labelsBuf.put(counter,Integer.parseInt(label));//添加标签
			}
			counter++;
		}
		br.close();
		reader.close();
    	FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
    	//比较下面方法获得的结果
        // FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
        // FaceRecognizer faceRecognizer = createLBPHFaceRecognizer();
    	//判断是否训练结束，如果没有这个文件进行训练，有的话直接predict
    	//System.out.println("start to train...");

    	faceRecognizer.train(images, labels);//训练
    	faceRecognizer.save("MyFaceRecognizer.xml");//已经训练好的模型，怎么获取这个文件，直接使用？	
    	
    	//CascadeClassifier cascadeClassifier=new CascadeClassifier("MyFaceRecognizer.xml");
    	//faceRecognizer.load("MyFaceRecognizer.xml");//加载已经训练好的模型
    	String testfileneme="F:/WORKPLACE/job/att_faces/s31/10.pgm";
    	Mat testImage = imread(testfileneme, CV_LOAD_IMAGE_GRAYSCALE); //获取待预测的图片	
    	System.out.println("start to predict...");
    	int predictedLabel=faceRecognizer.predict_label(testImage);//识别图片
    	System.out.println("people name:"+predictedLabel+"");
    }
}
