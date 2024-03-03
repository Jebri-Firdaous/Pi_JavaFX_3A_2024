package tn.pidev.controllers;

import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class App {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JFrame frame;
    private JLabel imageLabel;
    private CascadeClassifier faceDetector;

    public static void main(String[] args) {
        App app = new App();
        app.initGUI();
        app.loadCascade();
        app.runMainLoop();
    }

    private void loadCascade() {
        String cascadePath = "xml/haarcascade_frontalface_alt.xml";
        faceDetector = new CascadeClassifier(cascadePath);
    }

    private void initGUI() {
        frame = new JFrame("Face detection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        imageLabel = new JLabel();
        frame.add(imageLabel);
        frame.setVisible(true);
    }

    private void runMainLoop() {
        VideoCapture capture = new VideoCapture(0);
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);

        try {
            while (capture.isOpened()) {
                Mat webcamImage = new Mat();
                if (capture.read(webcamImage)) {
                    detectAndDrawFace(webcamImage);
                    updateImage(webcamImage);
                } else {
                    JOptionPane.showMessageDialog(frame, "Error: Could not capture frames");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            capture.release(); // Release the VideoCapture resource
        }
    }

    private void detectAndDrawFace(Mat image) {
        MatOfRect faceDetection = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetection, 1.1, 7, 0, new Size(50, 20), new Size());

        for (Rect rect : faceDetection.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 2);
            Imgproc.putText(image, "Face", new Point(rect.x, rect.y - 5),
                    Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(124, 252, 0), 2);
        }
    }

    private void updateImage(Mat image) {
        Image tempImage = matToBufferedImage(image);
        ImageIcon imageIcon = new ImageIcon(tempImage, "Captured Video");
        imageLabel.setIcon(imageIcon);
        frame.pack();
    }

    // Convert OpenCV Mat to BufferedImage
    private Image matToBufferedImage(Mat matrix) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (matrix.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = matrix.channels() * matrix.cols() * matrix.rows();
        byte[] buffer = new byte[bufferSize];
        matrix.get(0, 0, buffer); // Get all the pixels
        BufferedImage image = new BufferedImage(matrix.cols(), matrix.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
        return image;
    }
}
