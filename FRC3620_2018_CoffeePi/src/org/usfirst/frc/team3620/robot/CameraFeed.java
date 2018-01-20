package org.usfirst.frc.team3620.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;

public class CameraFeed {
	
	Thread m_visionThread;
	
	public void cameraFeedInit() {
		AnalogInput analog = new AnalogInput(0);
		m_visionThread = new Thread(() -> {
			// Get the UsbCamera from CameraServer
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			// Set the resolution
			
			// High Resolution, only run with the high resolution below
			//int width = 640;
			//int height = 480;
			
			//Low Resolution, only run with the low resolution below
			int width = 160;
			int height = 120;
			camera.setResolution(width, height);
			camera.setFPS(30);

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo();
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream
					= CameraServer.getInstance().putVideo("Rectangle", 640, 480);

			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat = new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if (cvSink.grabFrame(mat) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink.getError());
					// skip the rest of the current iteration
					continue;
				}
				Scalar color;
				if(analog.getVoltage()< 1.5) {
					color = new Scalar(0,255,0);
				} else {
					color = new Scalar(0,0,255);
				}
					//Low resolution but not laggy
					Imgproc.rectangle(mat, new Point(width/2, 0), new Point(width/2, 500),
							color, 1);
					Imgproc.rectangle(mat, new Point(0, height/2), new Point(650, height/2),
							color, 1);
				
				
				// Give the output stream a new image to display
				outputStream.putFrame(mat);
			}
		});
		m_visionThread.setDaemon(true);
		m_visionThread.start();
	}
}
