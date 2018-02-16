package org.usfirst.frc.team3620.robot;


import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

import java.io.File;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

/**
 * This is a demo program showing the use of OpenCV to do vision processing. The
 * image is acquired from the USB camera, then a rectangle is put on the image
 * and sent to the dashboard. OpenCV has many methods for different types of
 * processing.
 */
public class OperatorView {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	boolean TurnCrosshairsRed = true;

	public void turnCrosshairsRed(){
		TurnCrosshairsRed = true;
	}

	public void turnCrosshairsGreen(){	
		TurnCrosshairsRed = false;
	}

	public void operatorViewInit() {
		File videoCamera = new File("/dev/video0");
		if (videoCamera.exists()) {
			logger.info ("/dev/video0 exists, starting the camera server");
			Thread m_visionThread = new Thread(() -> {
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
						logger.warn(cvSink.getError());
						outputStream.notifyError(cvSink.getError());
						// skip the rest of the current iteration
						continue;
					}
					Scalar color;
					if(TurnCrosshairsRed == false) {
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
		} else {
			logger.warn ("Camera is missing");
		}
	}
}